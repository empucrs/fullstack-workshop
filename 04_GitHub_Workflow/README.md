# Module 04 - GitHub Collaboration Workflow

## Overview

Learn Git branching strategies, pull requests, code reviews, and team collaboration best practices.

## Branch Strategy

### Branch Types

| Type | Naming | Purpose |
|------|--------|---------|
| **main** | `main` | Production-ready code (protected) |
| **feature** | `feature/description` | New features |
| **bugfix** | `bugfix/description` | Bug fixes |
| **hotfix** | `hotfix/critical-issue` | Urgent production fixes |

### Examples

```bash
feature/add-user-authentication
feature/123-todo-filtering
bugfix/fix-cors-error
hotfix/security-patch
```

## Workflow

### 1. Create Feature Branch

```bash
# Update main
git checkout main
git pull origin main

# Create feature branch
git checkout -b feature/add-categories

# Make changes and commit
git add .
git commit -m "feat: add category support"

# Push to GitHub
git push origin feature/add-categories
```

### 2. Create Pull Request

1. Go to GitHub repository
2. Click "Pull requests" > "New pull request"
3. Select your branch
4. Fill in title and description
5. Request reviewers
6. Create PR

### 3. Code Review

**For Reviewers:**
- Check functionality and edge cases
- Verify code quality and readability
- Ensure tests are included
- Look for security issues

**For Authors:**
- Address feedback promptly
- Make requested changes
- Resolve all conversations

### 4. Merge PR

After approval:
1. Ensure all checks pass
2. Squash and merge (recommended)
3. Delete feature branch
4. Pull updated main locally

```bash
git checkout main
git pull origin main
git branch -d feature/add-categories
```

## PR Best Practices

### Good PR Titles

✅ `feat: add todo categories feature`
✅ `fix: resolve CORS error on API calls`
✅ `refactor: improve TodoList performance`

❌ `Update` (too vague)
❌ `Fixed stuff` (not descriptive)

### PR Description Template

```markdown
## Description
Brief description of changes

## Type
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation

## Related Issue
Fixes #123

## Testing
- [ ] Tested locally
- [ ] Tests pass
- [ ] Builds successfully

## Screenshots
(if applicable)
```

### PR Size

- **Small:** < 200 lines (ideal)
- **Medium:** 200-500 lines (acceptable)
- **Large:** > 500 lines (split into smaller PRs)

## Branch Protection Rules

Recommended settings for `main` branch:

1. **Settings** > **Branches** > **Add rule**
2. Branch pattern: `main`
3. Enable:
   - ☑ Require pull request before merging
   - ☑ Require 1 approval
   - ☑ Require status checks to pass
   - ☑ Require conversation resolution
   - ☑ Include administrators

## Commit Message Convention

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:** `feat`, `fix`, `docs`, `style`, `refactor`, `test`, `chore`

**Examples:**
```bash
feat(backend): add categories endpoint
fix(frontend): resolve memory leak in TodoList
docs(readme): update deployment instructions
```

## Quick Reference

### Common Commands

```bash
# Create branch
git checkout -b feature/name

# Stage and commit
git add .
git commit -m "feat: description"

# Push branch
git push origin feature/name

# Update from main
git fetch origin
git rebase origin/main

# Squash last 3 commits
git rebase -i HEAD~3
```

### Merge Conflicts

```bash
# Rebase on main
git checkout feature/name
git fetch origin
git rebase origin/main

# Resolve conflicts in editor
git add resolved-files
git rebase --continue

# Force push
git push --force-with-lease origin feature/name
```

## Troubleshooting

**Accidental commit to main:** Create branch, reset main to origin
**Wrong commit:** `git reset --soft HEAD~1` to undo
**Merge conflicts:** Pull latest main, rebase, resolve conflicts

---

**Next:** [Module 05 - CI/CD Pipeline](../05_CI_CD_Pipeline/)
