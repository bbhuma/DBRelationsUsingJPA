import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Dashboard from './components/Dashboard';
import Students from './components/Students';
import Teachers from './components/Teachers';
import Classes from './components/Classes';
import Login from './components/Login';
import AdminDashboard from './components/AdminDashboard';
import TeacherDashboard from './components/TeacherDashboard';
import StudentDashboard from './components/StudentDashboard';
import ParentDashboard from './components/ParentDashboard';

function App() {
  const [user, setUser] = useState(null);

  if (!user) {
    return <Login onLogin={setUser} />;
  }

  let dashboard;
  if (user.role === 'admin') dashboard = <AdminDashboard user={user} />;
  else if (user.role === 'teacher')
    dashboard = <TeacherDashboard user={user} />;
  else if (user.role === 'student')
    dashboard = <StudentDashboard user={user} />;
  else if (user.role === 'parent') dashboard = <ParentDashboard user={user} />;

  return (
    <Router>
      <div style={{ padding: 32 }}>
        <h1>School Management System</h1>
        <nav style={{ marginBottom: 24 }}>
          <Link to='/'>Dashboard</Link> | <Link to='/students'>Students</Link> |{' '}
          <Link to='/teachers'>Teachers</Link> |{' '}
          <Link to='/classes'>Classes</Link> |{' '}
          <button onClick={() => setUser(null)}>Logout</button>
        </nav>
        <Routes>
          <Route path='/' element={dashboard} />
          <Route path='/students' element={<Students />} />
          <Route path='/teachers' element={<Teachers />} />
          <Route path='/classes' element={<Classes />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
