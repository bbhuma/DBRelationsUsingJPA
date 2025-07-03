import React from 'react';

export default function AdminMenu({ onSelect }) {
  return (
    <nav style={{ marginBottom: 24 }}>
      <button onClick={() => onSelect('dashboard')}>Dashboard</button>{' '}
      <div style={{ display: 'inline-block', position: 'relative' }}>
        <button>Students ▼</button>
        <div
          style={{
            position: 'absolute',
            background: '#fff',
            border: '1px solid #ccc',
            zIndex: 1,
          }}>
          <div onClick={() => onSelect('students-all')}>All Students</div>
          <div onClick={() => onSelect('students-by-class')}>By Class</div>
          <div onClick={() => onSelect('students-by-teacher')}>By Teacher</div>
          <div onClick={() => onSelect('students-by-teacher-class')}>
            By Teacher & Class
          </div>
        </div>
      </div>{' '}
      <button onClick={() => onSelect('teachers')}>Teachers</button>{' '}
      <button onClick={() => onSelect('classes')}>Classes</button>{' '}
      <button onClick={() => onSelect('assignments')}>Assignments</button>{' '}
      <button onClick={() => onSelect('reports')}>Reports</button>{' '}
      <button onClick={() => onSelect('logout')}>Logout</button>
    </nav>
  );
}
