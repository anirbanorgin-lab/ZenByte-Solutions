# GitHub Actions Automated Deployment Setup

## Overview
This guide sets up automated deployments using GitHub Actions for:
- **Frontend** → Vercel (free static hosting)
- **Backend** → Railway (free tier with $5 credit)
- **Docker images** → Docker Hub (optional)

---

## Step 1: Push to GitHub

If not already on GitHub, initialize and push your repo:

```bash
git init
git add .
git commit -m "Initial commit with GitHub Actions"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/zenbyte-solutions.git
git push -u origin main
```

---

## Step 2: Set Up Vercel Deployment

### 2.1 Create Vercel Project
1. Go to [vercel.com](https://vercel.com) and sign in with GitHub
2. Click **Add New** → **Project**
3. Import your repository
4. Select framework: **Other** (since it's static HTML)
5. Set root directory to `frontend/`
6. Deploy!

### 2.2 Add Vercel Secrets to GitHub
1. In Vercel dashboard → **Settings** → **Tokens**
2. Create a token, copy it
3. Go to GitHub repo → **Settings** → **Secrets and variables** → **Actions**
4. Add these secrets:
   - `VERCEL_TOKEN` = Your Vercel token
   - `VERCEL_ORG_ID` = From Vercel account settings
   - `VERCEL_PROJECT_ID` = From your project settings

---

## Step 3: Set Up Railway Deployment

### 3.1 Create Railway Project
1. Go to [railway.app](https://railway.app) and sign in
2. Click **New Project** → **Deploy from GitHub repo**
3. Select your repository
4. Select the `main` branch

### 3.2 Configure Railway
1. In Railway project:
   - Add **MySQL** service
   - Add **Java** service
2. Set environment variables in Railway:
   ```
   SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/zenbyte
   SPRING_DATASOURCE_USERNAME=root
   SPRING_DATASOURCE_PASSWORD=your_password
   SPRING_JPA_HIBERNATE_DDL_AUTO=validate
   ```

### 3.3 Add Railway Token to GitHub
1. In Railway → **Account** → **API Tokens**
2. Create token, copy it
3. Go to GitHub repo → **Settings** → **Secrets and variables** → **Actions**
4. Add secret: `RAILWAY_TOKEN` = Your Railway token

---

## Step 4: Set Up Docker Hub (Optional)

If you want to also build Docker images:

### 4.1 Create Docker Hub Account
1. Go to [hub.docker.com](https://hub.docker.com)
2. Sign up for free account

### 4.2 Add Docker Secrets to GitHub
In GitHub repo → **Settings** → **Secrets and variables** → **Actions**, add:
- `DOCKER_USERNAME` = Your Docker Hub username
- `DOCKER_PASSWORD` = Your Docker Hub password

---

## Workflow Files Explained

### `deploy-frontend.yml`
- Triggers when files in `frontend/` change
- Automatically deploys to Vercel
- Runs on every push to `main`

### `deploy-backend.yml`
- Triggers when files in `backend/` change
- Automatically deploys to Railway
- Runs on every push to `main`

### `backend-tests.yml`
- Runs Maven tests on pull requests
- Compiles and builds JAR file
- Prevents broken code from being merged

### `frontend-tests.yml`
- Runs npm install and build
- Detects missing dependencies or syntax errors
- Runs on pull requests

### `docker-build.yml`
- Builds Docker images for both backend and Node.js service
- Pushes to Docker Hub
- Optional - only needed if using Docker

---

## How to Use

### Automatic Deployments
Just push to `main` branch:
```bash
git add .
git commit -m "Your changes"
git push origin main
```

Changes will auto-deploy within 2-3 minutes!

### Manual Deployments
Go to GitHub repo → **Actions** → Choose workflow → **Run workflow**

### Check Deployment Status
1. Go to GitHub repo → **Actions** tab
2. Click on the latest workflow run
3. See logs and status in real-time

---

## Troubleshooting

### Deployment fails
- Check GitHub Actions logs (Actions tab)
- Verify all secrets are set correctly
- For backend: Check Railway logs in Railway dashboard

### Frontend not updating
- Verify `VERCEL_PROJECT_ID` is correct
- Clear Vercel cache: Settings → Deployments → Clear cache

### Backend deployment slow
- Railway free tier has limited resources
- Builds may take 5-10 minutes
- Check Railway dashboard for resource usage

---

## Cost Breakdown (Monthly)

| Service | Free Tier | Cost |
|---------|-----------|------|
| Vercel Frontend | Unlimited | **FREE** |
| Railway Backend | $5 credit | **FREE** (~$5/mo equivalent) |
| GitHub Actions | 2000 min/month | **FREE** |
| **Total** | | **FREE** ✅ |

---

## Next Steps

1. ✅ Push code to GitHub
2. ✅ Set up Vercel and add tokens
3. ✅ Set up Railway and add token
4. ✅ Push changes to trigger workflows
5. Monitor deployments in GitHub Actions

**Your app should be live within 5 minutes!** 🚀
