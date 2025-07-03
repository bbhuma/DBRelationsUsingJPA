import React from 'react';
import { Bar } from 'react-chartjs-2';

export default function ClassPerformanceChart({ data }) {
  // Example data: [{ className: 'A', pass: 20, fail: 5 }, ...]
  const chartData = {
    labels: data.map(d => d.className),
    datasets: [
      {
        label: 'Pass',
        backgroundColor: 'green',
        data: data.map(d => d.pass),
      },
      {
        label: 'Fail',
        backgroundColor: 'red',
        data: data.map(d => d.fail),
      },
    ],
  };
  return (
    <div style={{ maxWidth: 600 }}>
      <Bar data={chartData} />
    </div>
  );
}
