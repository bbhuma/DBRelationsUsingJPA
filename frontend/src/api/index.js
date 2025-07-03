const API_BASE = '/api';

export async function getStudents() {
  const res = await fetch(`${API_BASE}/students`);
  return res.json();
}

export async function getStudentDetails(id) {
  const res = await fetch(`${API_BASE}/students/${id}`);
  return res.json();
}

export async function registerStudent(data) {
  const res = await fetch(`${API_BASE}/students`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });
  return res.json();
}

export async function getTeachers() {
  const res = await fetch(`${API_BASE}/teachers`);
  return res.json();
}

export async function getClasses() {
  const res = await fetch(`${API_BASE}/classes`);
  return res.json();
}

// Add more API functions as needed for enrollments, assignments, etc.
