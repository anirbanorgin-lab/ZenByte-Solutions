#!/bin/bash
# One-click deploy for zenbytesolutions.in on Hostinger VPS
# Run in Hostinger hPanel → VPS → Browser terminal (as root):
#   cd /var/www/zenbyte && chmod +x deploy/hostinger/one-click-deploy.sh && ./deploy/hostinger/one-click-deploy.sh

set -euo pipefail

DOMAIN="zenbytesolutions.in"
APP_DIR="/var/www/zenbyte"

echo "============================================"
echo " ZenByte Solutions — Deploy to $DOMAIN"
echo "============================================"

if [ "$(id -u)" -ne 0 ]; then
    echo "Please run as root: sudo $0"
    exit 1
fi

cd "$APP_DIR" || { echo "ERROR: $APP_DIR not found. Upload project files first."; exit 1; }

# ── Install Docker if missing ──────────────────────────────────────────────
if ! command -v docker &> /dev/null; then
    echo "==> Installing Docker..."
    curl -fsSL https://get.docker.com | sh
    systemctl enable docker && systemctl start docker
fi

if ! docker compose version &> /dev/null; then
    echo "==> Installing Docker Compose..."
    apt-get update -y && apt-get install -y docker-compose-plugin ufw curl
fi

# ── Firewall ───────────────────────────────────────────────────────────────
ufw allow OpenSSH 2>/dev/null || true
ufw allow 80/tcp 2>/dev/null || true
ufw allow 443/tcp 2>/dev/null || true
ufw --force enable 2>/dev/null || true

# ── Generate .env with secure random passwords ─────────────────────────────
if [ ! -f .env ]; then
    echo "==> Creating .env with secure passwords..."
    ROOT_PW=$(openssl rand -base64 24 | tr -d '/+=' | head -c 24)
    DB_PW=$(openssl rand -base64 24 | tr -d '/+=' | head -c 24)
    JWT_SEC=$(openssl rand -base64 48 | tr -d '/+=' | head -c 48)

    cat > .env <<EOF
DOMAIN=$DOMAIN
VPS_IP=2.57.91.91
MYSQL_ROOT_PASSWORD=$ROOT_PW
MYSQL_DATABASE=zenbyte_db
MYSQL_USER=zenbyte
MYSQL_PASSWORD=$DB_PW
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/zenbyte_db
SPRING_DATASOURCE_USERNAME=zenbyte
SPRING_DATASOURCE_PASSWORD=$DB_PW
JWT_SECRET=$JWT_SEC
SPRING_PROFILES_ACTIVE=prod
JAVA_OPTS=-Xmx512m -Xms256m
EOF
    chmod 600 .env
    echo "    .env created. Passwords saved in /var/www/zenbyte/.env"
else
    echo "==> Using existing .env"
fi

# ── Build backend JAR ──────────────────────────────────────────────────────
echo "==> Building backend..."
cd "$APP_DIR/backend"
docker run --rm -v "$PWD":/app -w /app maven:3.9-eclipse-temurin-17 mvn clean package -DskipTests -q
cd "$APP_DIR"

# ── Start production stack ─────────────────────────────────────────────────
echo "==> Starting Docker containers..."
docker compose -f docker-compose.prod.yml --env-file .env up -d --build

echo "==> Waiting for services to start (60s)..."
sleep 60

docker compose -f docker-compose.prod.yml ps

# ── Health check ───────────────────────────────────────────────────────────
echo ""
echo "==> Testing API..."
if curl -sf http://localhost/api/health > /dev/null 2>&1; then
    echo "    Node.js + API gateway is responding."
elif curl -sf http://localhost/api/contacts > /dev/null 2>&1; then
    echo "    API is responding."
else
    echo "    Services not ready yet — check: docker logs zenbyte-nodejs && docker logs zenbyte-backend"
fi

echo ""
echo "============================================"
echo " DEPLOYMENT COMPLETE"
echo "============================================"
echo "  Site:  http://$DOMAIN"
echo "  Site:  http://2.57.91.91"
echo "  Health: http://$DOMAIN/api/health"
echo ""
echo " DNS (set in Hostinger hPanel if not done):"
echo "    A  @   → 2.57.91.91"
echo "    A  www → 2.57.91.91"
echo ""
echo " SSL (after DNS works):"
echo "    docker compose -f docker-compose.prod.yml stop frontend"
echo "    certbot certonly --standalone -d $DOMAIN -d www.$DOMAIN"
echo "    # then uncomment HTTPS in deploy/hostinger/nginx.conf"
echo "    docker compose -f docker-compose.prod.yml up -d frontend"
echo "============================================"
