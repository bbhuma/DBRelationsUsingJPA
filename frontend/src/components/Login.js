import React, { useState } from 'react';

export default function Login({ onLogin }) {
  const [role, setRole] = useState('student');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = e => {
    e.preventDefault();
    // In a real app, call backend for authentication
    onLogin({ role, username });
  };

  return (
    <form
      onSubmit={handleSubmit}
      style={{
        maxWidth: 320,
        margin: '40px auto',
        padding: 24,
        border: '1px solid #ccc',
        borderRadius: 8,
      }}>
      <h2>Login</h2>
      <label>
        Role:
        <select value={role} onChange={e => setRole(e.target.value)}>
          <option value='admin'>Admin</option>
          <option value='teacher'>Teacher</option>
          <option value='student'>Student</option>
          <option value='parent'>Parent</option>
        </select>
      </label>
      <br />
      <br />
      <input
        placeholder='Username'
        value={username}
        onChange={e => setUsername(e.target.value)}
        required
      />
      <br />
      <br />
      <input
        type='password'
        placeholder='Password'
        value={password}
        onChange={e => setPassword(e.target.value)}
        required
      />
      <br />
      <br />
      <button type='submit'>Login</button>
    </form>
  );
}
