import React from 'react';
export default function MetricsCard({ label, value }) {
  return (
    <div
      style={{
        border: '1px solid #ccc',
        borderRadius: 8,
        padding: 16,
        minWidth: 120,
        textAlign: 'center',
      }}>
      <div style={{ fontSize: 24, fontWeight: 'bold' }}>{value}</div>
      <div>{label}</div>
    </div>
  );
}
