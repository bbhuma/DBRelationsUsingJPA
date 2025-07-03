import React, { useEffect, useState } from 'react';
import { getStudents, getTeachers, getClasses } from '../api';
import MetricsCard from './MetricsCard';

export default function Dashboard() {
  const [students, setStudents] = useState([]);
  const [teachers, setTeachers] = useState([]);
  const [classes, setClasses] = useState([]);

  useEffect(() => {
    getStudents().then(setStudents);
    getTeachers().then(setTeachers);
    getClasses().then(setClasses);
  }, []);

  const passCount = students.filter(s => s.grade !== 'F').length;
  const failCount = students.filter(s => s.grade === 'F').length;
  const passPercent = students.length
    ? ((passCount / students.length) * 100).toFixed(1)
    : 0;
  const failPercent = students.length
    ? ((failCount / students.length) * 100).toFixed(1)
    : 0;

  return (
    <div>
      <h2>School Dashboard</h2>
      <div style={{ display: 'flex', gap: 16 }}>
        <MetricsCard label='Total Students' value={students.length} />
        <MetricsCard label='Total Teachers' value={teachers.length} />
        <MetricsCard label='Total Classes' value={classes.length} />
        <MetricsCard label='Pass %' value={passPercent + '%'} />
        <MetricsCard label='Fail %' value={failPercent + '%'} />
      </div>
    </div>
  );
}
