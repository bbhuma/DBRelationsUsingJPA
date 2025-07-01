# Role-Based Authorization in Spring Data JPA Complex Object Project

## Overview

This document describes the role-based access control (RBAC) implemented in the project, including roles, permissions, and how they are enforced in the codebase.

## Roles

- **ADMIN**: Full access to all student, teacher, course, and enrollment data. Can add, update, and delete any record.
- **TEACHER**: Can view and update grades for their own students. Can only access data for students they teach.
- **STUDENT**: Can view only their own data (profile, enrollments, marks). Cannot access or modify other students' data.
- **PARENT**: Can view their own child's data (profile, marks, attendance, etc.).
- **REGISTRAR**: Can manage enrollments, view all students, and perform administrative academic tasks (but not delete students).
- **GUEST**: Can view public analytics or general information, but cannot access personal or sensitive data.

## Enforcement

Role-based access is enforced using Spring Security's `@PreAuthorize` annotation and custom logic in `SecurityService`.

### Example Annotations

- `@PreAuthorize("hasRole('ADMIN')")`: Only ADMIN can access.
- `@PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and @securityService.isTeacherOfStudent(authentication, #id))")`: ADMIN or the student's TEACHER can access.
- `@PreAuthorize("hasRole('STUDENT') and @securityService.isCurrentStudent(authentication, #id)")`: Only the student themselves can access.
- `@PreAuthorize("hasRole('PARENT') and @securityService.isParentOfStudent(authentication, #studentId)")`: Only the parent of the student can access.
- `@PreAuthorize("hasRole('REGISTRAR')")`: Only REGISTRAR can access.
- `@PreAuthorize("hasRole('GUEST')")`: Only GUEST can access public endpoints.

### SecurityService

Custom methods in `SecurityService`:

- `isTeacherOfStudent(Authentication authentication, Long studentId)`: Checks if the authenticated teacher teaches the student.
- `isCurrentStudent(Authentication authentication, Long studentId)`: Checks if the authenticated student is accessing their own data.
- `isParentOfStudent(Authentication authentication, Long studentId)`: Checks if the authenticated parent is the parent of the student.

### Example Usage

```java
@PreAuthorize("hasRole('ADMIN') or (hasRole('TEACHER') and @securityService.isTeacherOfStudent(authentication, #id)) or (hasRole('STUDENT') and @securityService.isCurrentStudent(authentication, #id)) or (hasRole('PARENT') and @securityService.isParentOfStudent(authentication, #id)) or hasRole('REGISTRAR')")
@GetMapping("/{id}/full-info")
public StudentFullInfoDTO getStudentFullInfo(@PathVariable Long id) { ... }
```

## Permissions Matrix

| Endpoint/Action       | ADMIN | TEACHER (own students) | STUDENT (self) | PARENT (own child) | REGISTRAR | GUEST |
| --------------------- | :---: | :--------------------: | :------------: | :----------------: | :-------: | :---: |
| Add student           |   ✔   |           ✖            |       ✖        |         ✖          |     ✔     |   ✖   |
| Delete student        |   ✔   |           ✖            |       ✖        |         ✖          |     ✖     |   ✖   |
| Update grades         |   ✔   |           ✔            |       ✖        |         ✖          |     ✖     |   ✖   |
| View all students     |   ✔   |           ✖            |       ✖        |         ✖          |     ✔     |   ✖   |
| View own students     |   ✔   |           ✔            |       ✖        |         ✖          |     ✔     |   ✖   |
| View own data         |   ✔   |           ✖            |       ✔        |         ✖          |     ✖     |   ✖   |
| View own child's data |   ✔   |           ✖            |       ✖        |         ✔          |     ✖     |   ✖   |
| Manage enrollments    |   ✔   |           ✖            |       ✖        |         ✖          |     ✔     |   ✖   |
| View public analytics |   ✔   |           ✔            |       ✔        |         ✔          |     ✔     |   ✔   |

## How to Extend

- Add new roles (e.g., COUNSELOR, LIBRARIAN) by updating the roles table and controller annotations.
- Implement additional checks in `SecurityService` as needed.

## References

- See `src/main/java/com/example/spring_data_jpa_complex_object/service/SecurityService.java` for custom logic.
- See controller classes for `@PreAuthorize` usage.

---

For more details, see the code and comments in the relevant files.
