# 08. Authentication: Real Backend Login & Session Management

## What Was Added

- Login form now calls backend authentication API
- Session/token management for logged-in users
- Role-based access enforced via backend

## How to Use

- Enter username and password on login page
- On success, session is established and user is redirected to their dashboard
- On failure, error is shown

## Next Steps

- Implement backend authentication endpoints (Spring Security/JWT)
- Store session/token securely (cookie/localStorage)
- Add logout and session expiration handling

---

**See `frontend/src/components/Login.js` for authentication logic.**
