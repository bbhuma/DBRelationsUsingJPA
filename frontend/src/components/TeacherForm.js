import React, { useState } from 'react';
// import { registerTeacher } from '../api';

export default function TeacherForm({ onSuccess }) {
  const [form, setForm] = useState({ name: '', subject: '' });
  const [loading, setLoading] = useState(false);

  const handleChange = e =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    setLoading(true);
    // await registerTeacher(form);
    setLoading(false);
    setForm({ name: '', subject: '' });
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
        name='subject'
        value={form.subject}
        onChange={handleChange}
        placeholder='Subject'
        required
      />
      <button type='submit' disabled={loading}>
        {loading ? 'Saving...' : 'Register Teacher'}
      </button>
    </form>
  );
}
