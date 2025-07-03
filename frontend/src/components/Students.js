import React, { useEffect, useState } from 'react';
import { getStudents } from '../api';
import StudentDetails from './StudentDetails';

export default function Students() {
  const [students, setStudents] = useState([]);
  const [selected, setSelected] = useState(null);

  useEffect(() => {
    getStudents().then(setStudents);
  }, []);

  return (
    <div>
      <h2>Students</h2>
      <button onClick={() => setSelected({})}>Register New Student</button>
      <ul>
        {students.map(s => (
          <li
            key={s.id}
            onClick={() => setSelected(s)}
            style={{ cursor: 'pointer' }}>
            {s.name} ({s.grade})
          </li>
        ))}
      </ul>
      {selected && (
        <StudentDetails student={selected} onClose={() => setSelected(null)} />
      )}
    </div>
  );
}
