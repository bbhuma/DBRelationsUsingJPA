# How to Create Users with Roles (Student, Teacher, Admin, Parent, Registrar, Guest)

This document explains how to create new users for each role in the system, how relationships (such as parent-student) are established, and what fields are required.

## User Entity Overview

- All users (students, teachers, admins, parents, registrar, guests) are stored in the `users` table.
- Each user can have one or more roles, assigned via the `roles` field (many-to-many with the `roles` table).
- Parent-child relationships are managed via the `parent_students` join table (linking parent users to their children, who are also users).

## Required Fields for User Creation

- `username` (unique)
- `password` (hashed, e.g., BCrypt)
- `fullName`
- `email`
- `roles` (set of roles, e.g., STUDENT, TEACHER, etc.)

## Creating Users by Role

### 1. Student

- Create a `User` with the STUDENT role.
- Optionally, link to parent(s) by adding parent users to the `parents` set.
- Example:

```java
User student = new User();
student.setUsername("student1");
student.setPassword(bcrypt("password"));
student.setFullName("Student One");
student.setEmail("student1@school.edu");
student.setRoles(Set.of(roleRepository.findByName(Role.STUDENT)));
// Optionally: student.setParents(Set.of(parentUser));
userRepository.save(student);
```

### 2. Teacher

- Create a `User` with the TEACHER role.
- Example:

```java
User teacher = new User();
teacher.setUsername("teacher1");
teacher.setPassword(bcrypt("password"));
teacher.setFullName("Teacher One");
teacher.setEmail("teacher1@school.edu");
teacher.setRoles(Set.of(roleRepository.findByName(Role.TEACHER)));
userRepository.save(teacher);
```

### 3. Admin

- Create a `User` with the ADMIN role.
- Example:

```java
User admin = new User();
admin.setUsername("admin1");
admin.setPassword(bcrypt("password"));
admin.setFullName("Admin User");
admin.setEmail("admin1@school.edu");
admin.setRoles(Set.of(roleRepository.findByName(Role.ADMIN)));
userRepository.save(admin);
```

### 4. Parent

- Create a `User` with the PARENT role.
- Link to children (students) by adding student users to the `children` set.
- Example:

```java
User parent = new User();
parent.setUsername("parent1");
parent.setPassword(bcrypt("password"));
parent.setFullName("Parent One");
parent.setEmail("parent1@school.edu");
parent.setRoles(Set.of(roleRepository.findByName(Role.PARENT)));
parent.setChildren(Set.of(studentUser1, studentUser2));
userRepository.save(parent);
```

### 5. Registrar

- Create a `User` with the REGISTRAR role.
- Example:

```java
User registrar = new User();
registrar.setUsername("registrar1");
registrar.setPassword(bcrypt("password"));
registrar.setFullName("Registrar User");
registrar.setEmail("registrar1@school.edu");
registrar.setRoles(Set.of(roleRepository.findByName(Role.REGISTRAR)));
userRepository.save(registrar);
```

### 6. Guest

- Create a `User` with the GUEST role (optional, for public/limited access).
- Example:

```java
User guest = new User();
guest.setUsername("guest1");
guest.setPassword(bcrypt("password"));
guest.setFullName("Guest User");
guest.setEmail("guest1@school.edu");
guest.setRoles(Set.of(roleRepository.findByName(Role.GUEST)));
userRepository.save(guest);
```

## Notes

- Use a password encoder (e.g., BCrypt) for storing passwords.
- Roles must exist in the `roles` table before assignment.
- Parent-child and student-parent relationships are managed via the `children` and `parents` sets in the `User` entity.
- A user can have multiple roles (e.g., a parent who is also a teacher).

## Example SQL for Roles

```sql
INSERT INTO roles (name) VALUES ('ADMIN'), ('TEACHER'), ('STUDENT'), ('PARENT'), ('REGISTRAR'), ('GUEST');
```

## References

- See `User` and `Role` entities for field details.
- See `ROLE-BASED-AUTH.md` for permissions and access control.
