import { useState } from 'react'
import './TodoForm.css'

function TodoForm({ onSubmit }) {
  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [showDescription, setShowDescription] = useState(false)

  const handleSubmit = (e) => {
    e.preventDefault()
    if (!title.trim()) return

    onSubmit(title, description)
    setTitle('')
    setDescription('')
    setShowDescription(false)
  }

  return (
    <form className="todo-form" onSubmit={handleSubmit}>
      <div className="form-group">
        <input
          type="text"
          className="form-input"
          placeholder="What needs to be done?"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          maxLength={200}
          required
        />
      </div>

      {showDescription && (
        <div className="form-group">
          <textarea
            className="form-textarea"
            placeholder="Add a description (optional)"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            maxLength={1000}
            rows={3}
          />
        </div>
      )}

      <div className="form-actions">
        <button
          type="button"
          className="btn-secondary"
          onClick={() => setShowDescription(!showDescription)}
        >
          {showDescription ? '− Less' : '+ Add Description'}
        </button>
        <button type="submit" className="btn-primary">
          Add TODO
        </button>
      </div>
    </form>
  )
}

export default TodoForm

