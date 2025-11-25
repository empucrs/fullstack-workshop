import axios from 'axios'

// API base URL - change this for production deployment
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

/**
 * Get all todos
 * @returns {Promise<Array>} Array of todo objects
 */
export const getTodos = async () => {
  const response = await api.get('/todos')
  return response.data
}

/**
 * Get todos by completion status
 * @param {boolean} completed - Filter by completion status
 * @returns {Promise<Array>} Array of filtered todo objects
 */
export const getTodosByStatus = async (completed) => {
  const response = await api.get('/todos', {
    params: { completed }
  })
  return response.data
}

/**
 * Get a single todo by ID
 * @param {number} id - Todo ID
 * @returns {Promise<Object>} Todo object
 */
export const getTodoById = async (id) => {
  const response = await api.get(`/todos/${id}`)
  return response.data
}

/**
 * Create a new todo
 * @param {string} title - Todo title
 * @param {string} description - Todo description (optional)
 * @returns {Promise<Object>} Created todo object
 */
export const createTodo = async (title, description = '') => {
  const response = await api.post('/todos', {
    title,
    description,
    completed: false,
  })
  return response.data
}

/**
 * Update an existing todo
 * @param {number} id - Todo ID
 * @param {Object} data - Updated todo data
 * @returns {Promise<Object>} Updated todo object
 */
export const updateTodo = async (id, data) => {
  const response = await api.put(`/todos/${id}`, data)
  return response.data
}

/**
 * Toggle todo completion status
 * @param {number} id - Todo ID
 * @returns {Promise<Object>} Updated todo object
 */
export const toggleTodo = async (id) => {
  const response = await api.patch(`/todos/${id}/toggle`)
  return response.data
}

/**
 * Delete a todo
 * @param {number} id - Todo ID
 * @returns {Promise<void>}
 */
export const deleteTodo = async (id) => {
  await api.delete(`/todos/${id}`)
}

/**
 * Delete all todos
 * @returns {Promise<void>}
 */
export const deleteAllTodos = async () => {
  await api.delete('/todos')
}

/**
 * Health check
 * @returns {Promise<Object>} Health status
 */
export const healthCheck = async () => {
  const response = await api.get('/todos/health')
  return response.data
}

export default api

