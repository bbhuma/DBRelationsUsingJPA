# Database Sample Data and API User Creation

This folder contains:

- `sample-users-roles.sql`: SQL script to insert demo users, roles, and relationships for a school system.
- `README.md`: This documentation file.

## How to Use the SQL Script

1. Ensure your database schema matches the entity structure (see `User`, `Role`, and join tables).
2. Run `sample-users-roles.sql` in your Oracle database to insert demo data:
   - 1 admin, 1 teacher, 1 student, 1 parent, 1 registrar, 1 guest
   - Parent is linked to student
   - Passwords are bcrypt hashes for the string `password`
3. You can log in with these users or use them for API testing.

## Creating Users via API

- Use your registration or admin endpoints to create users.
- Assign roles by adding the appropriate role(s) to the `roles` field.
- For parent-student relationships, add student users to the parent's `children` set (or use the parent-student API if available).
- See `USER-CREATION-ROLES.md` in the project root for detailed API and code examples.

## Customization

- Edit the SQL file to add more users or relationships as needed.
- Always use a secure password encoder for new users.

---

For more details, see the main project documentation.
