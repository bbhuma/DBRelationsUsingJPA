# React Frontend Integration: Project Changes

This project now includes a React app for the UI, integrated for single-unit deployment with Spring Boot.

## What Was Changed

1. **Created a React app in `/frontend`**

   - All React source code is under `frontend/`.
   - Main entry: `frontend/src/App.js` (simple placeholder UI).
   - React app is ready to call your Spring Boot APIs.

2. **Configured React for API Proxy**

   - `frontend/package.json` includes:
     ```json
     "proxy": "http://localhost:8080"
     ```
   - This allows the React app to call backend APIs during development without CORS issues.

3. **How to Build and Deploy**

   - Develop UI in `frontend/` as usual.
   - When ready for production:
     1. Run `npm run build` in `frontend/`.
     2. Copy the contents of `frontend/build/` to `src/main/resources/static/`.
     3. Build and run your Spring Boot JAR as usual. Both UI and APIs are now served from the same server.

4. **How to Develop Locally**
   - Run Spring Boot backend (`mvn spring-boot:run` or similar).
   - In another terminal, run `npm start` in `frontend/`.
   - UI runs on port 3000, backend on 8080. API calls are proxied.

## Next Steps

- Build out your React UI in `frontend/`.
- Use fetch/axios to call your backend APIs (e.g., `/api/customers`).
- When ready, follow the build/deploy steps above.

---

For a detailed integration guide, see `INTEGRATE-REACT-WITH-SPRINGBOOT.md` in the project root.
