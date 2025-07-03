import React, { useState } from 'react';
// import { registerClass } from '../api';

export default function ClassForm({ onSuccess }) {
  const [form, setForm] = useState({ name: '', grade: '' });
  const [loading, setLoading] = useState(false);

  const handleChange = e =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async e => {
    e.preventDefault();
    setLoading(true);
    // await registerClass(form);
    setLoading(false);
    setForm({ name: '', grade: '' });
    if (onSuccess) onSuccess();
  };

  return (
    <form onSubmit={handleSubmit} style={{ margin: '16px 0' }}>
      <input
        name='name'
        value={form.name}
        onChange={handleChange}
        placeholder='Class Name'
        required
      />
      <input
        name='grade'
        value={form.grade}
        onChange={handleChange}
        placeholder='Grade'
        required
      />
      <button type='submit' disabled={loading}>
        {loading ? 'Saving...' : 'Register Class'}
      </button>
    </form>
  );
}
