import React, { useState } from 'react';
import TeacherMenu from './TeacherMenu';

export default function TeacherDashboard({ user }) {
  const [view, setView] = useState('dashboard');

  return (
    <div>
      <TeacherMenu onSelect={setView} />
      {view === 'dashboard' && (
        <div>
          <h2>Welcome, {user?.username}!</h2>
          <p>View and manage your classes, students, and grades.</p>
          {/* Add summary cards/charts here */}
        </div>
      )}
      {view === 'classes' && (
        <div>
          <h3>My Classes</h3>
          {/* List classes */}
        </div>
      )}
      {view === 'students' && (
        <div>
          <h3>My Students</h3>
          {/* List students */}
        </div>
      )}
      {view === 'marks' && (
        <div>
          <h3>Enter/Update Marks</h3>
          {/* Marks entry UI */}
        </div>
      )}
      {view === 'assignments' && (
        <div>
          <h3>Assignments</h3>
          {/* Assignment UI */}
        </div>
      )}
      {view === 'profile' && (
        <div>
          <h3>Profile</h3>
          {/* Show/edit profile */}
        </div>
      )}
      {view === 'logout' && window.location.reload()}
    </div>
  );
}
