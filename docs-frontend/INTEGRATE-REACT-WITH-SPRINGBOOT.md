# Integrating React Frontend with Spring Boot Backend (Single Deployable Unit)

This guide explains how to set up a React app inside your Spring Boot project so both frontend and backend are served from the same JAR/WAR. This is a common enterprise pattern for simple deployments.

---

## 1. Create the React App Inside Your Spring Boot Project

From your project root, run:

```sh
npx create-react-app frontend
```

- This creates a `frontend/` folder for your React app.
- All frontend code will live here.

---

## 2. Develop Your React App

- Build your UI in the `frontend` folder.
- Use `fetch` or `axios` to call your Spring Boot APIs (e.g., `/api/students`).
- During development, run the React app with `npm start` (port 3000 by default).
- Make sure to handle CORS in your Spring Boot app for local development.

---

## 3. Build the React App for Production

When ready to deploy:

```sh
cd frontend
npm run build
```

- This creates a `frontend/build/` folder with static files (HTML, JS, CSS, etc.).

---

## 4. Serve React Build with Spring Boot

- Copy the contents of `frontend/build/` to `src/main/resources/static/` in your Spring Boot project:

```sh
cp -r frontend/build/* src/main/resources/static/
```

- Spring Boot will now serve your React app at the root URL (`/`).
- Your backend APIs remain available under `/api/...` or whatever path you use.

---

## 5. Build and Run as a Single Deployable Unit

- Build your Spring Boot JAR/WAR as usual:

```sh
./mvnw clean package
```

- Run the JAR:

```sh
java -jar target/your-app-name.jar
```

- Both the React frontend and backend APIs are now served from the same server and port.

---

## 6. Development Tips

- During development, keep React and Spring Boot running separately for fast feedback.
- Use a proxy in `frontend/package.json` to forward API requests to Spring Boot:

```json
"proxy": "http://localhost:8080"
```

- For production, only the built static files are needed in `src/main/resources/static`.

---

## 7. Example Project Structure

```
project-root/
├── frontend/           # React app source
│   └── build/          # Production build output
├── src/
│   └── main/
│       └── resources/
│           └── static/ # Where React build files go
├── pom.xml
└── ...
```

---

## 8. Summary

- React app lives in `/frontend`
- Production build is copied to `/src/main/resources/static`
- Spring Boot serves both frontend and backend together
- Single deployable JAR/WAR

---

## 9. References

- [Spring Boot Serving Static Content](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.servlet.spring-mvc.static-content)
- [Create React App Deployment](https://create-react-app.dev/docs/deployment/)
- [Spring Boot + React Example](https://www.baeldung.com/spring-boot-react-full-stack)

---

**This process is now ready for you to use in your project!**
