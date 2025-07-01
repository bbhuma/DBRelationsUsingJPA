# Postman Collection Enterprise Enhancement Steps

This document describes the steps taken to enhance and professionalize the Postman collection for the Spring Boot Student-Course-Class-Teacher Management System, making it enterprise-ready and suitable for team/CI/CD use.

## 1. Collection Organization and Grouping

- **Grouped all endpoints** into logical folders by resource: Students, Teachers, Courses, Classrooms, Enrollments, Customers.
- **Added clear descriptions** to each folder and endpoint for self-documentation and onboarding.

## 2. Parameterization

- **Created collection variables** for `baseUrl`, `studentId`, `classroomId`, `courseId`, `teacherId`, `enrollmentId`, and `authToken`.
- **Replaced all hardcoded IDs and URLs** in requests with variables (e.g., `{{studentId}}`, `{{baseUrl}}`).

## 3. Authentication

- **Added Authorization header** (`Bearer {{authToken}}`) to every request.
- **Pre-request script** sets the `authToken` variable dynamically from the environment if present.

## 4. Realistic Payloads and Responses

- **Used real data from `data.sql`** to create realistic sample request payloads and example responses for all POST/PUT/GET endpoints.
- **Ensured all sample responses** are representative and match the actual data model.

## 5. Test Scripts

- **Added a test script** to every request (at the collection level) to:
  - Ensure the response is JSON.
  - Ensure the status code is 2xx.

## 6. Advanced Postman Features

- **Enabled protocol profile behavior** (`disableBodyPruning: true`) for advanced request/response handling.

## 7. Environment Files

- **Created three environment files** for easy switching:
  - `environment.dev.json` (for local/dev)
  - `environment.stage.json` (for staging)
  - `environment.prod.json` (for production)
- **Populated each environment** with appropriate values for `baseUrl`, `authToken`, and other variables.

## 8. Documentation and Scalability

- **Added clear descriptions** to all endpoints, folders, and variables.
- **Ensured the collection is self-documenting** and ready for team sharing, onboarding, and automation.

## 9. CI/CD and Team Readiness

- **All variables and scripts** are parameterized for easy integration with CI/CD pipelines and team environments.
- **Collection and environments** can be imported into Postman or Newman for automated testing.

---

## How to Use

1. **Import the collection** (`SpringDataJpaComplexObjectAPI.postman_collection.json`) into Postman.
2. **Import the appropriate environment file** (`environment.dev.json`, `environment.stage.json`, or `environment.prod.json`).
3. **Set the `authToken`** in your environment (or use a pre-request script to fetch it dynamically).
4. **Run requests or folders** as needed. All requests are ready for team, automation, and CI/CD use.

---

## OAuth2 Authentication with Postman

### How OAuth2 Login Works in This Project

- OAuth2 login is handled by Spring Security at standard endpoints (e.g., `/oauth2/authorization/google`, `/login/oauth2/code/google`).
- You do not POST credentials to your backend; instead, you authenticate with the provider (Google, Microsoft, etc.), which redirects back to your app.

### How to Test OAuth2 in Postman

1. **Go to any secured endpoint in the collection.**
2. In the Authorization tab, select **OAuth 2.0**.
3. Click **Get New Access Token** and fill in:
   - **Token Name:** Google (or your provider)
   - **Grant Type:** Authorization Code
   - **Callback URL:** `http://localhost:8080/login/oauth2/code/google` (must match your provider config)
   - **Auth URL:** `https://accounts.google.com/o/oauth2/v2/auth`
   - **Access Token URL:** `https://oauth2.googleapis.com/token`
   - **Client ID/Secret:** (from your provider)
   - **Scope:** `openid profile email`
   - **State:** (optional)
   - **Client Authentication:** Send as Basic Auth header
4. Click **Get New Access Token**. Complete the login in the browser popup.
5. Postman will store the token and use it as a Bearer token for requests.

### Example

- See the `authToken` variable in the environment. You can set it manually or use Postman's OAuth2 flow to fetch and inject it.
- All secured endpoints will work with a valid OAuth2 access token.

### Notes

- You must have valid client credentials and redirect URIs set up in your provider (see `OAUTH2-CLIENT-ID-SECRET.md`).
- For JWT endpoints, use the JWT token as before.
- For OAuth2, use the access token from the provider.

---

## File List

- `SpringDataJpaComplexObjectAPI.postman_collection.json` (main collection)
- `environment.dev.json` (dev environment)
- `environment.stage.json` (stage environment)
- `environment.prod.json` (prod environment)
- `README-Postman.md` (this document)

---

For further enhancements (e.g., schema validation, dynamic data generation, or CI/CD integration), see the comments in the collection or contact the project maintainer.
