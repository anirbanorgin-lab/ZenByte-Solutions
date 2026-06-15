import express from 'express';
import path from 'path';
import cors from 'cors';
import helmet from 'helmet';
import morgan from 'morgan';
import { createProxyMiddleware } from 'http-proxy-middleware';
import config from './config.js';
import healthRoutes from './routes/health.js';

const app = express();

app.use(helmet({
    contentSecurityPolicy: false,
    crossOriginEmbedderPolicy: false
}));
app.use(cors({ origin: config.corsOrigins, credentials: true }));
app.use(morgan(config.nodeEnv === 'production' ? 'combined' : 'dev'));
app.use(express.json({ limit: '10mb' }));
app.use(express.urlencoded({ extended: true }));

// Node.js API routes (handled before Java proxy)
app.use('/api', healthRoutes);

// Proxy remaining /api/* requests to Java Spring Boot
app.use('/api', createProxyMiddleware({
    target: config.javaApiUrl,
    changeOrigin: true,
    pathRewrite: (path) => `/api${path}`,
    on: {
        error: (err, _req, res) => {
            console.error('[Java Proxy Error]', err.message);
            if (!res.headersSent) {
                res.status(502).json({
                    error: 'Java API unavailable',
                    message: 'The backend service is temporarily unreachable.'
                });
            }
        }
    }
}));

// Static frontend
app.use(express.static(config.frontendPath, {
    maxAge: config.nodeEnv === 'production' ? '1d' : 0,
    etag: true
}));

// SPA-style fallback for HTML pages
app.get('*', (req, res, next) => {
    if (req.path.startsWith('/api')) return next();
    const filePath = path.join(config.frontendPath, req.path.endsWith('.html') ? req.path : `${req.path}.html`);
    res.sendFile(filePath, (err) => {
        if (err) {
            res.sendFile(path.join(config.frontendPath, 'index.html'));
        }
    });
});

app.use((err, _req, res, _next) => {
    console.error('[Server Error]', err);
    res.status(500).json({ error: 'Internal server error' });
});

app.listen(config.port, () => {
    console.log('');
    console.log('  ╔══════════════════════════════════════════╗');
    console.log('  ║       ZenByte Solutions — Node.js        ║');
    console.log('  ╠══════════════════════════════════════════╣');
    console.log(`  ║  Frontend : http://localhost:${config.port}`.padEnd(43) + '║');
    console.log(`  ║  Java API : ${config.javaApiUrl}/api`.padEnd(43) + '║');
    console.log(`  ║  Health   : http://localhost:${config.port}/api/health`.padEnd(43) + '║');
    console.log('  ╚══════════════════════════════════════════╝');
    console.log('');
});

export default app;
