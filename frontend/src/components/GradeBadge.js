import React from 'react';
export default function GradeBadge({ grade }) {
  const color = grade === 'A' ? 'green' : grade === 'F' ? 'red' : 'orange';
  return (
    <span
      style={{
        background: color,
        color: 'white',
        padding: '2px 8px',
        borderRadius: 4,
      }}>
      {grade}
    </span>
  );
}
