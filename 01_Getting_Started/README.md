# Module 01 - Getting Started

## Overview

Set up your development environment and verify all required tools are installed.

## Required Software

1. **Git** - Version control (2.30+)
2. **Java JDK** - For Spring Boot (17+)
3. **Maven** - Java build tool (3.8+) or use included wrapper
4. **Node.js** - For React (18+, includes npm)
5. **Code Editor** - VS Code, IntelliJ IDEA, or Eclipse

### Recommended VS Code Extensions

- Extension Pack for Java
- Spring Boot Extension Pack
- ES7+ React/Redux/React-Native snippets
- ESLint
- Prettier

## Setup Steps

### 1. Verify Installations

Run the environment check script:

```bash
cd 01_Getting_Started
./environment-check.sh
```

Or manually verify:

```bash
git --version      # Should be 2.30+
java -version      # Should be 17+
mvn -version       # Should be 3.8+ (or use ./mvnw)
node --version     # Should be 18+
npm --version      # Should be 9+
```

### 2. Configure Git

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### 3. Get the Repository

**Fork (Recommended):**
1. Click "Fork" on GitHub
2. Clone your fork:
```bash
git clone https://github.com/YOUR_USERNAME/fullstack-workshop.git
cd fullstack-workshop
```

**Or Clone Directly:**
```bash
git clone https://github.com/ORIGINAL_OWNER/fullstack-workshop.git
cd fullstack-workshop
```

### 4. Test Backend Build

```bash
cd 02_Backend_SpringBoot
mvn clean compile
# Or: ./mvnw clean compile
```

Expected: `BUILD SUCCESS`

### 5. Test Frontend Setup

```bash
cd ../03_Frontend_React
npm install
```

## Troubleshooting

**Git not found:** Install Git and add to PATH
**Wrong Java version:** Install Java 17+ and set JAVA_HOME
**Maven not found:** Use Maven wrapper `./mvnw`
**Node.js too old:** Install Node.js 18+ from nodejs.org
**Port in use:** Kill process with `lsof -i :PORT` then `kill -9 PID`

## Verification Checklist

- [ ] Git installed (2.30+)
- [ ] Java installed (17+)
- [ ] Maven accessible (3.8+)
- [ ] Node.js installed (18+)
- [ ] Repository cloned
- [ ] Backend compiles
- [ ] Frontend dependencies installed

---

**Next:** [Module 02 - Backend Application](../02_Backend_SpringBoot/)
