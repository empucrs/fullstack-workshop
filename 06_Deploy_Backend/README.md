# Module 06 - Backend Deployment to Render

## Overview

Deploy Spring Boot backend to Render cloud platform using Docker.

## Why Render?

- Free tier (750 hours/month)
- Git-based deployment
- Auto-deploy from GitHub
- Free HTTPS/SSL
- Docker support for Java applications

## Prerequisites

- Backend from Module 02
- GitHub repository
- Render account (free at [render.com](https://render.com))

## Step 1: Create Production Config

The production properties file is already created at `02_Backend_SpringBoot/src/main/resources/application-prod.properties`:

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

## Step 2: Docker Configuration

Render.com does not natively support Java, so we use Docker. The Dockerfile is already created at `02_Backend_SpringBoot/Dockerfile`:

```dockerfile
# Build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 10000
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-jar", "app.jar"]
```

This multi-stage build:
1. Uses Maven + Java 17 to build the application
2. Uses a lightweight Alpine JRE image for runtime
3. Includes memory configuration optimized for free tier

Commit and push:
```bash
git add .
git commit -m "feat: add Docker configuration for Render deployment"
git push origin main
```

## Step 3: Create Render Service

1. Log into [Render Dashboard](https://dashboard.render.com)
2. Click **New +** > **Web Service**
3. Connect GitHub repository
4. Configure:

| Setting | Value |
|---------|-------|
| **Name** | `fullstack-workshop` |
| **Language** | `Docker` |
| **Branch** | `master` |
| **Region** | Virginia (US East) |
| **Root Directory** | `./02_Backend_SpringBoot` |
| **Dockerfile Path** | `./Dockerfile` |
| **Plan** | Free |

5. **Advanced** > Add Environment Variables:

| Key | Value |
|-----|-------|
| `SERVER_PORT` | `10000` |
| `SPRING_PROFILES_ACTIVE` | `prod` |

6. Set **Health Check Path:** `/api/todos/health`

7. Click **Create Web Service**

## Step 4: Monitor Deployment

Watch deployment logs in Render dashboard. Success when you see:
```
Started TodoApplication in X.XXX seconds
Your service is live 🎉
```

Your backend URL: `https://todo-backend-XXXX.onrender.com`

## Step 5: Test Deployed API

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

## Step 6: Set Up Auto-Deploy

1. In Render: **Settings** > **Deploy Hook** > **Create**
2. Copy webhook URL
3. In GitHub: **Settings** > **Secrets** > **New secret**
   - Name: `RENDER_DEPLOY_HOOK_BACKEND`
   - Value: Paste webhook URL

Now pushes to main auto-deploy via GitHub Actions!

## Alternative: Using render.yaml

The `render.yaml` file is already available in `06_Deploy_Backend/render.yaml`:

```yaml
services:
  - type: web
    name: fullstack-workshop
    runtime: docker
    branch: master
    region: virginia
    plan: free
    rootDir: ./02_Backend_SpringBoot
    dockerfilePath: ./Dockerfile
    healthCheckPath: /api/todos/health
    envVars:
      - key: SERVER_PORT
        value: 10000
      - key: SPRING_PROFILES_ACTIVE
        value: prod
```

To use Blueprint deployment:
1. Copy `render.yaml` to repository root
2. Go to Render Dashboard: **New** > **Blueprint** > Select repo
3. Render will automatically detect and deploy the service

## Free Tier Limits

- **750 hours/month** (good for demos/testing)
- **512 MB RAM**
- **Spin down** after 15 min inactivity
- **Spin up** takes ~30 seconds (cold start)

**Tip:** Keep warm with cron job pinging health endpoint

## Troubleshooting

**Docker build fails:** Check Dockerfile path is correct, verify `pom.xml` exists in context
**App won't start:** Check port is `10000`, verify `SPRING_PROFILES_ACTIVE=prod`
**Health check fails:** Verify path `/api/todos/health` exists and returns 200
**Out of memory:** Update Dockerfile ENTRYPOINT to use `-Xmx256m -Xms128m`
**CORS errors:** Add frontend URL to `WebConfig.java` allowed origins
**Slow cold start:** Docker images take ~30-60s to start on free tier

## Monitoring

**Render Dashboard tabs:**
- **Logs:** Real-time application logs
- **Metrics:** CPU, memory, requests
- **Events:** Deployment history

---

**Next:** [Module 07 - Deploy Frontend](../07_Deploy_Frontend/)
