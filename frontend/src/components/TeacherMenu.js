import React from 'react';

export default function TeacherMenu({ onSelect }) {
  return (
    <nav style={{ marginBottom: 24 }}>
      <button onClick={() => onSelect('dashboard')}>Dashboard</button>{' '}
      <button onClick={() => onSelect('classes')}>My Classes</button>{' '}
      <button onClick={() => onSelect('students')}>My Students</button>{' '}
      <button onClick={() => onSelect('marks')}>Enter/Update Marks</button>{' '}
      <button onClick={() => onSelect('assignments')}>Assignments</button>{' '}
      <button onClick={() => onSelect('profile')}>Profile</button>{' '}
      <button onClick={() => onSelect('logout')}>Logout</button>
    </nav>
  );
}
