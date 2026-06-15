# DNS Records for Hostinger — zenbytesolutions.in

Point your domain to VPS **2.57.91.91** in hPanel → Domains → zenbytesolutions.in → DNS / DNS Zone.

| Type | Name | Points to | TTL |
|------|------|-----------|-----|
| A | `@` | `2.57.91.91` | 3600 |
| A | `www` | `2.57.91.91` | 3600 |

Remove or update any old A records that point elsewhere.

After saving, wait 5–30 minutes, then verify:

```bash
ping zenbytesolutions.in
nslookup zenbytesolutions.in
```

Expected result: `2.57.91.91`

---

## Deploy commands (run on VPS via SSH)

```bash
ssh root@2.57.91.91
```

```bash
cd /var/www/zenbyte
cp deploy/hostinger/.env.production .env
nano .env                    # set passwords + JWT secret
chmod +x deploy/hostinger/*.sh
./deploy/hostinger/setup-vps.sh   # first time only
./deploy/hostinger/deploy.sh
```

## Upload from Windows (if not on server yet)

```powershell
cd "c:\Users\ANIRBAN\Music\ZenByte Solutions"
.\deploy\hostinger\upload.ps1 -ServerIp "2.57.91.91"
```

## URLs after deploy

| Service | URL |
|---------|-----|
| Website | http://zenbytesolutions.in |
| Website (IP) | http://2.57.91.91 |
| API test | http://zenbytesolutions.in/api/contacts |

## SSL (after DNS propagates)

```bash
docker compose -f docker-compose.prod.yml stop frontend
certbot certonly --standalone -d zenbytesolutions.in -d www.zenbytesolutions.in --email info@zenbytesolutions.in --agree-tos
# Uncomment HTTPS block in deploy/hostinger/nginx.conf
docker compose -f docker-compose.prod.yml up -d frontend
```

Live site: **https://zenbytesolutions.in**
