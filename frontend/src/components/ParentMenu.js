import React from 'react';

export default function ParentMenu({ onSelect }) {
  return (
    <nav style={{ marginBottom: 24 }}>
      <button onClick={() => onSelect('dashboard')}>My Dashboard</button>{' '}
      <button onClick={() => onSelect('classes')}>My Child's Classes</button>{' '}
      <button onClick={() => onSelect('teachers')}>My Child's Teachers</button>{' '}
      <button onClick={() => onSelect('marks')}>My Child's Marks</button>{' '}
      <button onClick={() => onSelect('profile')}>Profile</button>{' '}
      <button onClick={() => onSelect('logout')}>Logout</button>
    </nav>
  );
}
