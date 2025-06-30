# Student API Documentation

This document describes the REST endpoints for querying student, class, course, and teacher information in your system.

## Endpoints

### 1. Get Full Info for a Student
- **GET** `/api/students/{id}/full-info`
- **Description:** Returns all details for a student, including class, enrolled courses, marks, and teachers for each course.
- **Response Example:**
```json
{
  "studentId": 1,
  "studentName": "Priya Sharma",
  "className": "10A",
  "courses": [
    {
      "courseId": 2,
      "courseTitle": "Mathematics",
      "marks": 95,
      "teachers": ["Mr. Verma"]
    }
  ]
}
```

### 2. Get Full Info for All Students
- **GET** `/api/students/full-info`
- **Description:** Returns a list of all students with their class, courses, marks, and teachers.

### 3. Get Students by Class
- **GET** `/api/students/by-class/{classId}`
- **Description:** Returns all students in a specific class, with their full info.

### 4. Get Students by Course
- **GET** `/api/students/by-course/{courseId}`
- **Description:** Returns all students enrolled in a specific course, with their full info.

### 5. Get Students by Teacher
- **GET** `/api/students/by-teacher/{teacherId}`
- **Description:** Returns all students taught by a specific teacher (in any course/class), with their full info.

### 6. Get Students by Class, Course, and Teacher
- **GET** `/api/students/by-class/{classId}/by-course/{courseId}/by-teacher/{teacherId}`
- **Description:** Returns all students in a specific class, for a specific course, taught by a specific teacher, with their full info.

---

## Notes
- All endpoints return a list of students (except the single-student endpoint).
- The `courses` array in the response includes marks and the list of teachers for each course.
- These endpoints are designed for flexible querying across students, classes, courses, and teachers.

## Recommendations
- For interactive API documentation and testing, consider integrating Swagger (OpenAPI) using Springdoc:
  - Add the dependency:
    ```xml
    <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.5.0</version>
    </dependency>
    ```
  - After adding, access Swagger UI at `/swagger-ui.html` for live API docs and testing.

---

For further customization or to add more endpoints, update the controller and this documentation accordingly.
