import { useState, useEffect } from 'react'
import './App.css'
import TodoList from './components/TodoList'
import TodoForm from './components/TodoForm'
import { getTodos, createTodo, updateTodo, deleteTodo, toggleTodo } from './services/api'

function App() {
  const [todos, setTodos] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [filter, setFilter] = useState('all') // 'all', 'active', 'completed'

  // Load todos on mount
  useEffect(() => {
    loadTodos()
  }, [])

  const loadTodos = async () => {
    try {
      setLoading(true)
      setError(null)
      const data = await getTodos()
      setTodos(data)
    } catch (err) {
      setError('Failed to load todos. Please make sure the backend is running.')
      console.error('Error loading todos:', err)
    } finally {
      setLoading(false)
    }
  }

  const handleCreateTodo = async (title, description) => {
    try {
      const newTodo = await createTodo(title, description)
      setTodos([...todos, newTodo])
    } catch (err) {
      setError('Failed to create todo')
      console.error('Error creating todo:', err)
    }
  }

  const handleToggleTodo = async (id) => {
    try {
      const updatedTodo = await toggleTodo(id)
      setTodos(todos.map(todo =>
        todo.id === id ? updatedTodo : todo
      ))
    } catch (err) {
      setError('Failed to update todo')
      console.error('Error toggling todo:', err)
    }
  }

  const handleDeleteTodo = async (id) => {
    try {
      await deleteTodo(id)
      setTodos(todos.filter(todo => todo.id !== id))
    } catch (err) {
      setError('Failed to delete todo')
      console.error('Error deleting todo:', err)
    }
  }

  const handleUpdateTodo = async (id, title, description) => {
    try {
      const todo = todos.find(t => t.id === id)
      const updatedTodo = await updateTodo(id, {
        title,
        description,
        completed: todo.completed
      })
      setTodos(todos.map(t => t.id === id ? updatedTodo : t))
    } catch (err) {
      setError('Failed to update todo')
      console.error('Error updating todo:', err)
    }
  }

  // Filter todos based on selected filter
  const filteredTodos = todos.filter(todo => {
    if (filter === 'active') return !todo.completed
    if (filter === 'completed') return todo.completed
    return true
  })

  const activeCount = todos.filter(todo => !todo.completed).length
  const completedCount = todos.filter(todo => todo.completed).length

  return (
    <div className="app">
      <header className="app-header">
        <h1>📝 TODO App</h1>
        <p className="subtitle">Fullstack Workshop - React + Spring Boot</p>
      </header>

      <main className="app-main">
        <div className="container">
          {error && (
            <div className="error-banner">
              {error}
              <button onClick={() => setError(null)} className="close-btn">×</button>
            </div>
          )}

          <TodoForm onSubmit={handleCreateTodo} />

          <div className="stats">
            <span className="stat">
              Total: <strong>{todos.length}</strong>
            </span>
            <span className="stat">
              Active: <strong>{activeCount}</strong>
            </span>
            <span className="stat">
              Completed: <strong>{completedCount}</strong>
            </span>
          </div>

          <div className="filters">
            <button
              className={filter === 'all' ? 'active' : ''}
              onClick={() => setFilter('all')}
            >
              All
            </button>
            <button
              className={filter === 'active' ? 'active' : ''}
              onClick={() => setFilter('active')}
            >
              Active
            </button>
            <button
              className={filter === 'completed' ? 'active' : ''}
              onClick={() => setFilter('completed')}
            >
              Completed
            </button>
          </div>

          {loading ? (
            <div className="loading">Loading todos...</div>
          ) : (
            <TodoList
              todos={filteredTodos}
              onToggle={handleToggleTodo}
              onDelete={handleDeleteTodo}
              onUpdate={handleUpdateTodo}
            />
          )}

          {!loading && filteredTodos.length === 0 && (
            <div className="empty-state">
              <p>🎉 No todos found!</p>
              <p className="empty-hint">
                {filter !== 'all'
                  ? `You don't have any ${filter} todos.`
                  : 'Create your first todo above.'}
              </p>
            </div>
          )}
        </div>
      </main>

      <footer className="app-footer">
        <p>Built with React + Vite | Backend: Spring Boot</p>
      </footer>
    </div>
  )
}

export default App

