# JWT Authentication and Role-Based Access in Spring Data JPA Complex Object API

## Overview

This project uses JWT (JSON Web Token) authentication for all API endpoints. All users—admin, user, student, and teacher—authenticate using JWT tokens. There is no Basic Auth. The backend uses the JWT token to identify the user and their role(s) for access control.

---

## How JWT Tokens Work

- **Single Token Format:**

  - All users receive a JWT token after successful authentication (login or OAuth2).
  - The token structure is the same for all roles.

- **Role Identification:**

  - The JWT token contains a claim (usually `role` or `roles`) that specifies the user's role(s): `ADMIN`, `USER`, `STUDENT`, or `TEACHER`.

- **Backend Authorization:**

  - The backend decodes the JWT and checks the `role` claim to determine access rights.
  - Example:
    - `role: ADMIN` → full access
    - `role: USER` → limited access (e.g., only GET endpoints)
    - `role: STUDENT` or `TEACHER` → access to their own data or specific endpoints

- **No Separate Tokens per Role:**

  - There is not a different token type for each role. The difference is in the `role` claim inside the token.

- **How to Get a Token:**
  1. User logs in (with username/password or via OAuth2 provider).
  2. Backend authenticates and issues a JWT with the correct `role` claim.
  3. Client uses this token as a Bearer token in the `Authorization` header for all requests.

---

## Example JWT Payload

```json
{
  "sub": "user123",
  "name": "John Doe",
  "role": "ADMIN", // or "USER", "STUDENT", "TEACHER"
  "exp": 1712345678
}
```

---

## Role-Based Access Table

| Role    | Token Structure | How Backend Knows | Access Control         |
| ------- | --------------- | ----------------- | ---------------------- |
| Admin   | Same JWT        | `role: ADMIN`     | All endpoints          |
| User    | Same JWT        | `role: USER`      | Limited endpoints      |
| Student | Same JWT        | `role: STUDENT`   | Student endpoints only |
| Teacher | Same JWT        | `role: TEACHER`   | Teacher endpoints only |

---

## Postman Usage

- Set your JWT token in the `authToken` variable (or `adminToken`, `userToken` for role-based testing).
- All requests use the `Authorization: Bearer <token>` header.
- The backend will check the token and allow/deny access based on the `role` claim.

---

## Summary

- **Only JWT tokens are used for authentication.**
- **All roles use the same JWT format; the `role` claim determines access.**
- **Backend checks the `role` claim to enforce permissions.**

For more details, see the main project README or the Postman collection documentation.
