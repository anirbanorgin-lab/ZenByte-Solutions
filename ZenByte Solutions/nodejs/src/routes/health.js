import express from 'express';
import config from '../config.js';

const router = express.Router();

router.get('/health', async (_req, res) => {
    const nodeStatus = {
        service: 'nodejs-gateway',
        status: 'UP',
        timestamp: new Date().toISOString(),
        uptime: process.uptime(),
        environment: config.nodeEnv
    };

    let javaStatus = { service: 'java-api', status: 'UNKNOWN' };

    try {
        const response = await fetch(`${config.javaApiUrl}/api/contacts`, {
            method: 'GET',
            signal: AbortSignal.timeout(5000)
        });
        javaStatus = {
            service: 'java-api',
            status: response.ok ? 'UP' : 'DEGRADED',
            httpStatus: response.status
        };
    } catch (err) {
        javaStatus = {
            service: 'java-api',
            status: 'DOWN',
            error: err.message
        };
    }

    const overall = javaStatus.status === 'UP' && nodeStatus.status === 'UP' ? 'UP' : 'DEGRADED';

    res.status(overall === 'UP' ? 200 : 503).json({
        status: overall,
        application: config.appName,
        domain: config.domain,
        services: [nodeStatus, javaStatus]
    });
});

router.get('/info', (_req, res) => {
    res.json({
        name: config.appName,
        stack: ['Node.js Express', 'Java Spring Boot', 'MySQL'],
        tagline: 'Innovate • Integrate • Elevate',
        version: '1.0.0',
        domain: config.domain
    });
});

export default router;
