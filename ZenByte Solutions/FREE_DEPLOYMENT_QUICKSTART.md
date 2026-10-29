# Free Deployment Quick Start (5 Steps)

## 🚀 Deploy ZenByte Solutions in 5 Minutes

### Step 1: Push to GitHub (2 min)
```bash
git init
git add .
git commit -m "ZenByte Solutions ready for deployment"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/zenbyte-solutions.git
git push -u origin main
```

---

### Step 2: Set Up Netlify for Frontend (1 min)
1. Go to [netlify.com](https://netlify.com) → Sign in with GitHub
2. Click **Add new site** → **Import from Git** → Select your repo
3. Set **Base directory** to `/` (root)
4. Set **Build command** to blank or `echo "No build needed"`
5. Set **Publish directory** to `frontend`
6. Deploy the site and add the `_redirects` file in `frontend/`

**Result**: Your frontend is now live on Netlify ✅

---

### Step 3: Set Up Railway for Backend (1 min)
1. Go to [railway.app](https://railway.app) → Sign in with GitHub
2. Click **New Project** → **Deploy from GitHub repo** → Select your repo
3. Railway auto-detects Java project ✅
4. In Railway dashboard:
   - Add **MySQL** service
   - Add Java service for `backend/`
5. Set environment variables (see [deploy/railway.md](deploy/railway.md))
6. Get your **API Token** from Account → API Tokens
7. In GitHub repo → Settings → Secrets:
   - `RAILWAY_TOKEN` = Your token

**Result**: Backend deployed to Railway with auto-scaling ✅

---

### Step 4: Configure Backend Environment (1 min)
In Railway dashboard → Java service → Variables, set:
```
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/zenbyte
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=[your_password]
CORS_ALLOWED_ORIGINS=https://your-vercel-domain.vercel.app
```

Run database migration:
```bash
mysql -h railway-host -u root -p < database/schema.sql
```

---

### Step 5: Enable Auto-Deployment (instant)
Already done! ✅ 

Every time you push to `main`:
- Frontend automatically deploys to Vercel
- Backend automatically deploys to Railway
- Tests run automatically on pull requests

---

## ✅ You're Done!

**Your app is now live:**
- 🌐 **Frontend**: https://zenbyte-solutions.vercel.app
- 🔌 **Backend API**: https://railway-backend-url.up.railway.app/api

**GitHub Actions automatically:**
- Tests code on pull requests
- Deploys frontend on every push
- Deploys backend on every push
- Builds Docker images (optional)

---

## Cost Comparison

| Before | After (Free) |
|--------|--------------|
| Hostinger VPS: $24-48/month | **$0** ✅ |
| Maintenance overhead | **$0** ✅ |
| Manual deployments | **Automatic** ✅ |

---

## Troubleshooting

**Frontend not updating?**
```
Check: GitHub Actions → deploy-frontend.yml logs
```

**Backend not deploying?**
```
Check: Railway dashboard → Logs
Verify: All environment variables are set
```

**API not responding?**
```
Check: CORS_ALLOWED_ORIGINS matches your frontend URL
Check: Database connection string is correct
```

---

## Next: Custom Domain (Optional)

To use `zenbytesolutions.in`:
- **Vercel**: Settings → Domains → Add custom domain
- **Railway**: No custom domain for free tier (use Vercel + Nginx as reverse proxy)

See [HOSTINGER_DEPLOY.md](HOSTINGER_DEPLOY.md) if you need domain setup help.
