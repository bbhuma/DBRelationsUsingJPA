import React from 'react';
import GradeBadge from './GradeBadge';

export default function StudentDetails({ student, onClose }) {
  return (
    <div>
      <button onClick={onClose}>Close</button>
      <h3>{student.name}</h3>
      <p>Class: {student.className}</p>
      <p>Marks: {student.marks}</p>
      <GradeBadge grade={student.grade} />
      <p>Status: {student.grade === 'F' ? 'Fail' : 'Pass'}</p>
      {/* Add more details as needed */}
    </div>
  );
}
