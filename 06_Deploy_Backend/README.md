# Module 06 - Backend Deployment to Render

## Overview

Deploy Spring Boot backend to Render cloud platform.

## Why Render?

- Free tier (750 hours/month)
- Git-based deployment
- Auto-deploy from GitHub
- Free HTTPS/SSL
- Easy configuration

## Prerequisites

- Backend from Module 02
- GitHub repository
- Render account (free at [render.com](https://render.com))

## Step 1: Create Production Config

Create `02_Backend_SpringBoot/src/main/resources/application-prod.properties`:

```properties
spring.application.name=todo-backend
server.port=${SERVER_PORT:10000}

# Database
spring.datasource.url=jdbc:h2:mem:tododb
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false

# Security
spring.h2.console.enabled=false

# Logging
logging.level.root=INFO

# API
server.servlet.context-path=/api
```

Commit and push:
```bash
git add .
git commit -m "feat: add production config"
git push origin main
```

## Step 2: Create Render Service

1. Log into [Render Dashboard](https://dashboard.render.com)
2. Click **New +** > **Web Service**
3. Connect GitHub repository
4. Configure:

| Setting | Value |
|---------|-------|
| **Name** | `todo-backend` |
| **Region** | Oregon (or nearest) |
| **Branch** | `main` |
| **Build Command** | `cd 02_Backend_SpringBoot && ./mvnw clean package -DskipTests` |
| **Start Command** | `cd 02_Backend_SpringBoot && java -jar target/*.jar` |
| **Plan** | Free |

5. **Advanced** > Add Environment Variables:

| Key | Value |
|-----|-------|
| `JAVA_OPTS` | `-Xmx512m -Xms256m` |
| `SERVER_PORT` | `10000` |
| `SPRING_PROFILES_ACTIVE` | `prod` |

6. Set **Health Check Path:** `/api/todos/health`

7. Click **Create Web Service**

## Step 3: Monitor Deployment

Watch deployment logs in Render dashboard. Success when you see:
```
Started TodoApplication in X.XXX seconds
Your service is live 🎉
```

Your backend URL: `https://todo-backend-XXXX.onrender.com`

## Step 4: Test Deployed API

```bash
# Health check
curl https://YOUR-SERVICE.onrender.com/api/todos/health

# Get todos
curl https://YOUR-SERVICE.onrender.com/api/todos

# Create todo
curl -X POST https://YOUR-SERVICE.onrender.com/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","completed":false}'
```

Or visit in browser: `https://YOUR-SERVICE.onrender.com/api/todos`

## Step 5: Set Up Auto-Deploy

1. In Render: **Settings** > **Deploy Hook** > **Create**
2. Copy webhook URL
3. In GitHub: **Settings** > **Secrets** > **New secret**
   - Name: `RENDER_DEPLOY_HOOK_BACKEND`
   - Value: Paste webhook URL

Now pushes to main auto-deploy via GitHub Actions!

## Alternative: Using render.yaml

Create `render.yaml` in repository root:

```yaml
services:
  - type: web
    name: todo-backend
    env: java
    plan: free
    buildCommand: cd 02_Backend_SpringBoot && ./mvnw clean package -DskipTests
    startCommand: cd 02_Backend_SpringBoot && java -jar target/*.jar
    healthCheckPath: /api/todos/health
    envVars:
      - key: JAVA_OPTS
        value: -Xmx512m -Xms256m
      - key: SERVER_PORT
        value: 10000
      - key: SPRING_PROFILES_ACTIVE
        value: prod
```

Then: **New** > **Blueprint** > Select repo

## Free Tier Limits

- **750 hours/month** (good for demos/testing)
- **512 MB RAM**
- **Spin down** after 15 min inactivity
- **Spin up** takes ~30 seconds (cold start)

**Tip:** Keep warm with cron job pinging health endpoint

## Troubleshooting

**Build fails:** Check Java 17+ in build logs, verify `mvnw` is executable
**App won't start:** Check port is `10000`, verify start command
**Health check fails:** Verify path `/api/todos/health` exists
**Out of memory:** Reduce `JAVA_OPTS` to `-Xmx256m -Xms128m`
**CORS errors:** Add frontend URL to `WebConfig.java` allowed origins

## Monitoring

**Render Dashboard tabs:**
- **Logs:** Real-time application logs
- **Metrics:** CPU, memory, requests
- **Events:** Deployment history

---

**Next:** [Module 07 - Deploy Frontend](../07_Deploy_Frontend/)
