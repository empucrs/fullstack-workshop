# Module 03 - Frontend Application (React)

## Overview

Modern React application with Vite for managing TODO items.

## Tech Stack

- React 18, Vite, Axios
- Component-based architecture
- Modern CSS with responsive design

## Project Structure

```
03_Frontend_React/
├── src/
│   ├── components/      # React components
│   │   ├── TodoForm.jsx
│   │   ├── TodoList.jsx
│   │   └── TodoItem.jsx
│   ├── services/
│   │   └── api.js       # API client
│   ├── App.jsx          # Main app
│   └── main.jsx         # Entry point
├── index.html
├── package.json
└── vite.config.js
```

## Features

- ➕ Create todos with descriptions
- ✅ Toggle completion status
- ✏️ Edit inline
- 🗑️ Delete todos
- 🔍 Filter (All, Active, Completed)
- 📊 Real-time statistics
- 📱 Mobile responsive

## Quick Start

### 1. Install Dependencies

```bash
cd 03_Frontend_React
npm install
```

### 2. Configure API

Create `.env` file:
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

### 3. Run Development Server

```bash
npm run dev
```

Opens at: http://localhost:3000

**Note:** Backend must be running on port 8080!

### 4. Build for Production

```bash
npm run build
npm run preview
```

## Key Components

**App.jsx** - Main component with state management
**TodoForm.jsx** - Create new todos
**TodoList.jsx** - List container
**TodoItem.jsx** - Individual todo with edit/delete
**api.js** - Centralized API calls

## API Service

```javascript
// src/services/api.js
import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

export const getTodos = async () => {
  const response = await api.get('/todos')
  return response.data
}

export const createTodo = async (title, description) => {
  const response = await api.post('/todos', { title, description, completed: false })
  return response.data
}

// ... more API methods
```

## Available Scripts

```bash
npm run dev      # Start dev server with HMR
npm run build    # Production build
npm run preview  # Preview production build
npm run lint     # Run ESLint
```

## Troubleshooting

**Backend connection error:** Ensure backend is running on port 8080
**Port 3000 in use:** Change port in `vite.config.js`
**Dependencies fail:** Clear cache with `npm cache clean --force`
**Blank page:** Check browser console for errors

---

**Next:** [Module 04 - GitHub Workflow](../04_GitHub_Workflow/)
