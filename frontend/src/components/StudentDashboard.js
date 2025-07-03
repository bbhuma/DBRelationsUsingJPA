import React, { useState } from 'react';
import StudentMenu from './StudentMenu';

export default function StudentDashboard({ user }) {
  const [view, setView] = useState('dashboard');

  return (
    <div>
      <StudentMenu onSelect={setView} />
      {view === 'dashboard' && (
        <div>
          <h2>Welcome, {user?.username}!</h2>
          <p>Here is your progress, grades, and summary.</p>
          {/* Add summary cards/charts here */}
        </div>
      )}
      {view === 'classes' && (
        <div>
          <h3>My Classes</h3>
          {/* List classes */}
        </div>
      )}
      {view === 'teachers' && (
        <div>
          <h3>My Teachers</h3>
          {/* List teachers */}
        </div>
      )}
      {view === 'marks' && (
        <div>
          <h3>My Marks</h3>
          {/* List marks/grades */}
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
