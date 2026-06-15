# Hostinger Deployment Guide — ZenByte Solutions

**Domain:** [zenbytesolutions.in](https://zenbytesolutions.in)  
**VPS IP:** `2.57.91.91`

This project needs **Hostinger VPS** (KVM) because it runs a Java Spring Boot API and MySQL. Shared hosting only supports static/PHP sites and cannot run this backend.

## What you need

| Item | Recommendation |
|------|----------------|
| Hostinger plan | **VPS KVM 1** (4 GB RAM) or higher |
| OS | Ubuntu 22.04 or 24.04 |
| Domain | Pointed to your VPS IP in Hostinger hPanel |

---

## Step 1 — Buy Hostinger VPS

1. Log in to [Hostinger hPanel](https://hpanel.hostinger.com)
2. Go to **VPS** → choose **KVM 1** (or higher)
3. Select **Ubuntu 24.04**
4. Note your **VPS IP address** and **root password**

---

## Step 2 — Point your domain

In hPanel → **Domains** → your domain → **DNS**:

| Type | Name | Points to |
|------|------|-----------|
| A | `@` | `2.57.91.91` |
| A | `www` | `2.57.91.91` |

Wait 5–30 minutes for DNS to propagate.

---

## Step 3 — Connect to VPS via SSH

**Windows (PowerShell):**
```powershell
ssh root@2.57.91.91
```

**First-time VPS setup:**
```bash
curl -fsSL https://raw.githubusercontent.com/YOUR_REPO/main/deploy/hostinger/setup-vps.sh | bash
```

Or upload the project and run:
```bash
chmod +x deploy/hostinger/setup-vps.sh
sudo ./deploy/hostinger/setup-vps.sh
```

---

## Step 4 — Upload the project

**Option A — Git (recommended)**
```bash
cd /var/www
git clone https://github.com/YOUR_USERNAME/zenbyte-solutions.git zenbyte
cd zenbyte
```

**Option B — SCP from your PC (Windows PowerShell)**
```powershell
scp -r "c:\Users\ANIRBAN\Music\ZenByte Solutions" root@2.57.91.91:/var/www/zenbyte
```

**Option C — Hostinger File Manager / FileZilla**  
Upload the whole project folder to `/var/www/zenbyte`.

---

## Step 5 — Configure environment

```bash
cd /var/www/zenbyte
cp deploy/hostinger/.env.production .env
nano .env
```

Set these values:

```env
DOMAIN=zenbytesolutions.in
MYSQL_ROOT_PASSWORD=strong_root_password_here
MYSQL_PASSWORD=strong_db_password_here
SPRING_DATASOURCE_PASSWORD=strong_db_password_here
JWT_SECRET=at_least_32_random_characters_here
```

Save and exit (`Ctrl+O`, `Enter`, `Ctrl+X`).

---

## Step 6 — Deploy

```bash
chmod +x deploy/hostinger/deploy.sh
./deploy/hostinger/deploy.sh
```

This will:
- Build the Spring Boot JAR
- Start MySQL, backend, and Nginx via Docker
- Serve the frontend on port **80**

Visit: `http://2.57.91.91` or `http://zenbytesolutions.in`

Test the API:
```bash
curl http://localhost/api/contacts
```

---

## Step 7 — Enable HTTPS (SSL)

After DNS points to your VPS:

```bash
# Install certbot on the VPS host
apt install -y certbot

# Stop nginx container briefly if port 80 is busy
docker compose -f docker-compose.prod.yml stop frontend

# Get certificate
certbot certonly --standalone -d zenbytesolutions.in -d www.zenbytesolutions.in --email you@email.com --agree-tos

# Copy certs into Docker volume (or mount host path in compose)
```

Edit `deploy/hostinger/nginx.conf`:
1. Uncomment the **HTTPS server** block
2. Replace `zenbytesolutions.in` with your real domain
3. Restart:

```bash
docker compose -f docker-compose.prod.yml up -d frontend
```

**Easier alternative:** In Hostinger hPanel, use **SSL** on the domain if you use Hostinger's managed reverse proxy — for VPS you typically use Let's Encrypt as above.

---

## Step 8 — Hostinger hPanel checklist

- [ ] VPS running (green status in hPanel)
- [ ] Domain A record → VPS IP
- [ ] Firewall allows ports 80, 443, 22
- [ ] `.env` secrets changed (not default passwords)
- [ ] Site loads in browser
- [ ] Contact form submits successfully
- [ ] SSL enabled

---

## Updating the site later

```bash
cd /var/www/zenbyte
git pull          # if using git
./deploy/hostinger/deploy.sh
```

---

## Alternative: Frontend only on Hostinger Shared Hosting

If you only have **shared hosting** (no VPS):

1. Upload contents of `frontend/` to `public_html/` via File Manager or FTP
2. Forms **will not work** unless you host the API elsewhere (Railway, Render, another VPS)

This full-stack app is designed for **VPS + Docker**.

---

## Troubleshooting

| Problem | Fix |
|---------|-----|
| Site not loading | `docker compose -f docker-compose.prod.yml ps` — all containers should be `Up` |
| 502 on `/api` | Backend still starting; wait 60s or check `docker logs zenbyte-backend` |
| Database error | Verify `.env` passwords match; `docker logs zenbyte-mysql` |
| Forms fail | Open browser DevTools → Network; API should hit `/api/contacts` on same domain |
| Port 80 in use | `ss -tlnp \| grep :80` — stop conflicting service |

**View logs:**
```bash
docker logs zenbyte-backend -f
docker logs zenbyte-frontend -f
docker logs zenbyte-mysql -f
```

---

## Support

- Hostinger VPS docs: [support.hostinger.com](https://support.hostinger.com)
- Project issues: check `docker compose` logs first

**ZenByte Solutions** — production-ready on Hostinger VPS
