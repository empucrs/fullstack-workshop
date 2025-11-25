# Module 05 - CI/CD Pipeline Configuration

## Overview

Set up automated testing and deployment using GitHub Actions.

## CI/CD Concepts

**Continuous Integration (CI):** Automatically build and test code changes
**Continuous Deployment (CD):** Automatically deploy to production

## Workflows

We'll create 4 workflows:

1. **backend-pr.yml** - Test backend on PRs
2. **frontend-pr.yml** - Lint and build frontend on PRs
3. **backend-deploy.yml** - Deploy backend to Render
4. **frontend-deploy.yml** - Deploy frontend to GitHub Pages

## Setup

### 1. Create Workflow Directory

```bash
mkdir -p .github/workflows
```

### 2. Backend PR Workflow

Copy `05_CI_CD_Pipeline/workflows/backend-pr.yml` to `.github/workflows/backend-pr.yml`:

Example content:

```yaml
name: Backend PR Check

on:
  pull_request:
    branches: [main]
    paths: ['02_Backend_SpringBoot/**']

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build and Test
        working-directory: ./02_Backend_SpringBoot
        run: |
          mvn clean package
          mvn test
```

### 3. Frontend PR Workflow

Copy `05_CI_CD_Pipeline/workflows/frontend-pr.yml` to `.github/workflows/frontend-pr.yml`:

Example content:

```yaml
name: Frontend PR Check

on:
  pull_request:
    branches: [main]
    paths: ['03_Frontend_React/**']

jobs:
  build-and-lint:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: '18'
      - name: Install and Build
        working-directory: ./03_Frontend_React
        run: |
          npm ci
          npm run lint
          npm run build
```

### 4. Backend Deployment

Copy `05_CI_CD_Pipeline/workflows/backend-deploy.yml` to `.github/workflows/backend-deploy.yml`:

Example content:

```yaml
name: Deploy Backend to Render

on:
  push:
    branches: [main]
    paths: ['02_Backend_SpringBoot/**']
  workflow_dispatch:

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '17'
      - name: Build
        working-directory: ./02_Backend_SpringBoot
        run: mvn clean package -DskipTests
      - name: Deploy to Render
        env:
          RENDER_DEPLOY_HOOK: ${{ secrets.RENDER_DEPLOY_HOOK_BACKEND }}
        run: curl "$RENDER_DEPLOY_HOOK"
```

### 5. Frontend Deployment

Copy `05_CI_CD_Pipeline/workflows/frontend-deploy.yml` to `.github/workflows/frontend-deploy.yml`:

Example content:

```yaml
name: Deploy Frontend to GitHub Pages

on:
  push:
    branches: [main]
    paths: ['03_Frontend_React/**']
  workflow_dispatch:

permissions:
  contents: read
  pages: write
  id-token: write

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: '18'
      - name: Build
        working-directory: ./03_Frontend_React
        env:
          VITE_API_BASE_URL: ${{ secrets.VITE_API_BASE_URL }}
        run: |
          npm ci
          npm run build
      - uses: actions/upload-pages-artifact@v3
        with:
          path: ./03_Frontend_React/dist

  deploy:
    needs: build
    runs-on: ubuntu-latest
    steps:
      - uses: actions/deploy-pages@v4
```

## Configure Secrets

### 1. Add Secrets

Go to **Settings** > **Secrets and variables** > **Actions** > **New repository secret**

| Secret Name | Value |
|-------------|-------|
| `RENDER_DEPLOY_HOOK_BACKEND` | Render deploy hook URL |
| `VITE_API_BASE_URL` | Backend URL (e.g., `https://your-backend.onrender.com/api`) |

### 2. Get Render Deploy Hook

1. Log into Render
2. Go to your service
3. **Settings** > **Deploy Hook** > **Create**
4. Copy URL and add as GitHub secret

## Enable GitHub Pages

1. **Settings** > **Pages**
2. Source: **GitHub Actions**
3. Save

## Test Workflows

### Test PR Workflow

```bash
git checkout -b feature/test-ci
# Make a change
git add .
git commit -m "test: trigger CI"
git push origin feature/test-ci
# Create PR on GitHub
```

### Test Deployment

```bash
# Merge PR to main
# Watch Actions tab for deployment workflows
```

## Workflow Syntax Quick Reference

```yaml
# Triggers
on:
  push:
    branches: [main]
  pull_request:
  workflow_dispatch:  # Manual trigger

# Jobs
jobs:
  job-name:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - run: echo "Hello"

# Environment variables
env:
  KEY: ${{ secrets.SECRET_NAME }}

# Path filters
paths:
  - '02_Backend_SpringBoot/**'
```

## Monitoring

**View workflows:** **Actions** tab in GitHub
**View logs:** Click workflow run > Click step
**Re-run:** Click "Re-run jobs"

## Troubleshooting

**Workflow not triggering:** Check path filters match changed files
**Secret not found:** Verify secret name matches exactly (case-sensitive)
**Build fails:** Check logs, test locally first
**Deploy fails:** Check Render logs and deploy hook URL

---

**Next:** [Module 06 - Deploy Backend](../06_Deploy_Backend/)
