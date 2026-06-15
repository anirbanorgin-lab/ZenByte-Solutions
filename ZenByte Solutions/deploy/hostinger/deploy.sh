#!/bin/bash
# Deploy ZenByte Solutions on Hostinger VPS
# Run from project root: ./deploy/hostinger/deploy.sh

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/../.." && pwd)"
cd "$ROOT_DIR"

if [ ! -f .env ]; then
    echo "ERROR: .env file not found."
    echo "Copy deploy/hostinger/.env.example to .env and fill in your secrets."
    exit 1
fi

echo "==> Building backend JAR..."
cd backend
if command -v mvn &> /dev/null; then
    mvn clean package -DskipTests -q
else
    echo "Maven not found. Building inside Docker..."
    docker run --rm -v "$PWD":/app -w /app maven:3.9-eclipse-temurin-17 mvn clean package -DskipTests -q
fi
cd "$ROOT_DIR"

echo "==> Starting production stack..."
docker compose -f docker-compose.prod.yml --env-file .env up -d --build

echo "==> Waiting for services..."
sleep 15
docker compose -f docker-compose.prod.yml ps

echo ""
echo "Deployment complete!"
echo "  Site:  http://$(grep DOMAIN .env | cut -d= -f2 || echo 'YOUR_VPS_IP')"
echo "  API:   http://$(grep DOMAIN .env | cut -d= -f2 || echo 'YOUR_VPS_IP')/api/contacts"
echo ""
echo "Point your domain A record to this server's IP, then enable SSL (see HOSTINGER_DEPLOY.md)."
