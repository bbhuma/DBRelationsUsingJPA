import React, { useState } from 'react';
import AdminMenu from './AdminMenu';
import StudentForm from './StudentForm';
import TeacherForm from './TeacherForm';
import ClassForm from './ClassForm';
import { getStudents, getTeachers, getClasses } from '../api';

export default function AdminDashboard() {
  const [view, setView] = useState('dashboard');
  const [students, setStudents] = useState([]);
  const [teachers, setTeachers] = useState([]);
  const [classes, setClasses] = useState([]);

  // Fetch data as needed for each view
  const fetchStudents = async () => setStudents(await getStudents());
  const fetchTeachers = async () => setTeachers(await getTeachers());
  const fetchClasses = async () => setClasses(await getClasses());

  // Menu handler
  const handleMenu = v => {
    setView(v);
    if (v.startsWith('students')) fetchStudents();
    if (v === 'teachers') fetchTeachers();
    if (v === 'classes') fetchClasses();
    if (v === 'logout') window.location.reload();
  };

  return (
    <div>
      <AdminMenu onSelect={handleMenu} />
      {view === 'dashboard' && <h2>Admin Dashboard: Metrics & Charts</h2>}
      {view === 'students-all' && (
        <div>
          <h3>All Students</h3>
          <ul>
            {students.map(s => (
              <li key={s.id}>{s.name}</li>
            ))}
          </ul>
        </div>
      )}
      {view === 'students-by-class' && (
        <div>
          <h3>Get Students by Class</h3>
          {/* Add class dropdown and student list here */}
        </div>
      )}
      {view === 'students-by-teacher' && (
        <div>
          <h3>Get Students by Teacher</h3>
          {/* Add teacher dropdown and student list here */}
        </div>
      )}
      {view === 'students-by-teacher-class' && (
        <div>
          <h3>Get Students by Teacher & Class</h3>
          {/* Add teacher/class dropdowns and student list here */}
        </div>
      )}
      {view === 'teachers' && (
        <div>
          <h3>All Teachers</h3>
          <ul>
            {teachers.map(t => (
              <li key={t.id}>{t.name}</li>
            ))}
          </ul>
        </div>
      )}
      {view === 'classes' && (
        <div>
          <h3>All Classes</h3>
          <ul>
            {classes.map(c => (
              <li key={c.id}>{c.name}</li>
            ))}
          </ul>
        </div>
      )}
      {view === 'assignments' && (
        <div>
          <h3>Assignments</h3>
        </div>
      )}
      {view === 'reports' && (
        <div>
          <h3>Reports</h3>
        </div>
      )}
      {/* Forms for creating new entities */}
      <div style={{ marginTop: 32 }}>
        <h3>Create Student</h3>
        <StudentForm onSuccess={fetchStudents} />
        <h3>Create Teacher</h3>
        <TeacherForm onSuccess={fetchTeachers} />
        <h3>Create Class</h3>
        <ClassForm onSuccess={fetchClasses} />
      </div>
    </div>
  );
}
