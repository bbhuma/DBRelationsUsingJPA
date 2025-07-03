import React from 'react';

export default function StudentMenu({ onSelect }) {
  return (
    <nav style={{ marginBottom: 24 }}>
      <button onClick={() => onSelect('dashboard')}>My Dashboard</button>{' '}
      <button onClick={() => onSelect('classes')}>My Classes</button>{' '}
      <button onClick={() => onSelect('teachers')}>My Teachers</button>{' '}
      <button onClick={() => onSelect('marks')}>My Marks</button>{' '}
      <button onClick={() => onSelect('profile')}>Profile</button>{' '}
      <button onClick={() => onSelect('logout')}>Logout</button>
    </nav>
  );
}
