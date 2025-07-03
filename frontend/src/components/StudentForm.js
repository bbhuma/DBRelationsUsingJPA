import React, { useState } from 'react';
import { registerStudent } from '../api';

export default function StudentForm({ onSuccess }) {
  const [form, setForm] = useState({ name: '', className: '', marks: '' });
  const [loading, setLoading] = useState(false);

  const handleChange = e =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    setLoading(true);
    await registerStudent(form);
    setLoading(false);
    setForm({ name: '', className: '', marks: '' });
    if (onSuccess) onSuccess();
  };

  return (
    <form onSubmit={handleSubmit} style={{ margin: '16px 0' }}>
      <input
        name='name'
        value={form.name}
        onChange={handleChange}
        placeholder='Name'
        required
      />
      <input
        name='className'
        value={form.className}
        onChange={handleChange}
        placeholder='Class'
        required
      />
      <input
        name='marks'
        value={form.marks}
        onChange={handleChange}
        placeholder='Marks'
        type='number'
        required
      />
      <button type='submit' disabled={loading}>
        {loading ? 'Saving...' : 'Register Student'}
      </button>
    </form>
  );
}
