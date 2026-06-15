#!/bin/bash
# One-time VPS setup for Hostinger (Ubuntu 22.04/24.04)
# Run as root on your Hostinger VPS:
#   curl -fsSL ... | bash
# Or: chmod +x setup-vps.sh && sudo ./setup-vps.sh

set -euo pipefail

echo "==> Updating system..."
apt-get update -y && apt-get upgrade -y

echo "==> Installing Docker..."
if ! command -v docker &> /dev/null; then
    curl -fsSL https://get.docker.com | sh
    systemctl enable docker
    systemctl start docker
fi

echo "==> Installing Docker Compose plugin..."
apt-get install -y docker-compose-plugin git ufw

echo "==> Configuring firewall..."
ufw allow OpenSSH
ufw allow 80/tcp
ufw allow 443/tcp
ufw --force enable

echo "==> Creating app directory..."
mkdir -p /var/www/zenbyte
chown -R "$SUDO_USER:$SUDO_USER" /var/www/zenbyte 2>/dev/null || true

echo ""
echo "VPS setup complete."
echo "Next steps:"
echo "  1. Upload project to /var/www/zenbyte"
echo "  2. cp deploy/hostinger/.env.example .env && nano .env"
echo "  3. ./deploy/hostinger/deploy.sh"
