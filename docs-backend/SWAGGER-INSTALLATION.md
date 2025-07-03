# How to Enable Swagger (OpenAPI) Documentation in Your Spring Boot Project

## 1. Add Springdoc OpenAPI Dependency

Add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.5.0</version>
</dependency>
```

## 2. Run Your Application

Start your Spring Boot application as usual.

## 3. Access Swagger UI

Open your browser and go to:

```
http://localhost:8080/swagger-ui.html
```

You will see an interactive documentation page for all your REST APIs, including the student endpoints.

---

## 4. (Optional) Add Descriptions to Your Controllers

You can add OpenAPI annotations for more detailed documentation. Example:

```java
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/students")
public class StudentInfoController {
    @Operation(summary = "Get full info for a student", description = "Returns all details for a student, including class, courses, marks, and teachers.")
    @GetMapping("/{id}/full-info")
    public StudentFullInfoDTO getStudentFullInfo(@PathVariable Long id) { ... }
    // ...
}
```

---

## 5. More Info
- [Springdoc OpenAPI Documentation](https://springdoc.org/)
- [Swagger UI Demo](https://swagger.io/tools/swagger-ui/)

---

After following these steps, your API will have live, interactive documentation and testing via Swagger UI.
