import dotenv from 'dotenv';
import path from 'path';
import { fileURLToPath } from 'url';

dotenv.config();

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const rootDir = path.resolve(__dirname, '../..');

export default {
    port: parseInt(process.env.PORT || '3000', 10),
    nodeEnv: process.env.NODE_ENV || 'development',
    javaApiUrl: process.env.JAVA_API_URL || 'http://localhost:8080',
    frontendPath: process.env.FRONTEND_PATH || path.join(rootDir, 'frontend'),
    corsOrigins: (process.env.CORS_ORIGINS || 'http://localhost:3000,http://localhost:8080')
        .split(',')
        .map((o) => o.trim()),
    appName: 'ZenByte Solutions',
    domain: process.env.DOMAIN || 'zenbytesolutions.in'
};
