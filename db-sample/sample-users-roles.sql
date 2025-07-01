-- Sample roles
INSERT INTO roles (id, name) VALUES
  (1, 'ADMIN'),
  (2, 'TEACHER'),
  (3, 'STUDENT'),
  (4, 'PARENT'),
  (5, 'REGISTRAR'),
  (6, 'GUEST');

-- Sample users (passwords are bcrypt hashes for 'password')
INSERT INTO users (id, username, password, full_name, email) VALUES
  (1, 'admin1', '$2a$10$7QJ8Qw1Qw1Qw1Qw1Qw1QwOQw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Q', 'Admin User', 'admin1@school.edu'),
  (2, 'teacher1', '$2a$10$7QJ8Qw1Qw1Qw1Qw1Qw1QwOQw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Q', 'Teacher One', 'teacher1@school.edu'),
  (3, 'student1', '$2a$10$7QJ8Qw1Qw1Qw1Qw1Qw1QwOQw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Q', 'Student One', 'student1@school.edu'),
  (4, 'parent1', '$2a$10$7QJ8Qw1Qw1Qw1Qw1Qw1QwOQw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Q', 'Parent One', 'parent1@school.edu'),
  (5, 'registrar1', '$2a$10$7QJ8Qw1Qw1Qw1Qw1Qw1QwOQw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Q', 'Registrar User', 'registrar1@school.edu'),
  (6, 'guest1', '$2a$10$7QJ8Qw1Qw1Qw1Qw1Qw1QwOQw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Qw1Q', 'Guest User', 'guest1@school.edu');

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 1), -- admin1 is ADMIN
  (2, 2), -- teacher1 is TEACHER
  (3, 3), -- student1 is STUDENT
  (4, 4), -- parent1 is PARENT
  (5, 5), -- registrar1 is REGISTRAR
  (6, 6); -- guest1 is GUEST

-- Parent-student relationship
INSERT INTO parent_students (parent_id, student_id) VALUES
  (4, 3); -- parent1 is parent of student1
