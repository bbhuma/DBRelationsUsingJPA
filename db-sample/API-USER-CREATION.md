# How to Use the API for User Creation and Role Assignment

This guide explains how to create users (student, teacher, admin, parent, registrar, guest) and assign roles using the API.

## 1. Register a New User

### Endpoint Example

```
POST /api/auth/register
Content-Type: application/json

{
  "username": "student2",
  "password": "password",
  "fullName": "Student Two",
  "email": "student2@school.edu",
  "roles": ["STUDENT"]
}
```

- The `roles` array can include any valid role: `ADMIN`, `TEACHER`, `STUDENT`, `PARENT`, `REGISTRAR`, `GUEST`.
- Passwords should be sent in plain text and will be hashed by the backend.

## 2. Assign Roles to an Existing User

### Endpoint Example

```
POST /api/users/{userId}/roles
Content-Type: application/json

{
  "roles": ["TEACHER", "ADMIN"]
}
```

- This endpoint should be protected (ADMIN only).
- The backend will update the user's roles accordingly.

## 3. Link Parent and Student

### Endpoint Example

```
POST /api/users/{parentId}/children
Content-Type: application/json

{
  "childrenIds": [3, 5] // IDs of student users
}
```

- This links the parent user to one or more student users.
- The backend will update the parent-child relationship.

## 4. Example: Create a Parent and Link to a Student

1. Register the parent user with the PARENT role.
2. Register the student user with the STUDENT role.
3. Use the parent-child linking endpoint to associate them.

## 5. Notes

- All user creation and role assignment endpoints should be protected (e.g., only ADMIN or REGISTRAR can create users except for self-registration).
- Always use HTTPS for API calls.
- The actual endpoint paths may vary; check your controller mappings.

## 6. References

- See `USER-CREATION-ROLES.md` for code examples and more details.
- See your API documentation (Swagger/OpenAPI) for exact endpoint details.
