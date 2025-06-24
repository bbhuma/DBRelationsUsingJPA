1. Enrollement object contains student and course by default. They fetch the objects from db, the extra details needed are supplied.
- that is called required business data, student and course are FK, referenced objects. 
- Enroll a new student with studentId and CourseId and grade details 
- http://localhost:9090/api/enroll?studentId=9&courseId=9&grade=C
- {
  "id": 1,
  "enrollmentDate": "2024-04-06",
  "grade": "A",
  "student": {
  "id": 2,
  "name": "Student2"
  },
  "course": {
  "id": 5,
  "title": "Course5"
  }
  }
- To post a new enrollment, end point has to fetch student based on Id, fetch course by Id, 
  then get rade from request, date is system.now and id auto generated. 
2.Get List of course-enrollments of a studentId into many courses. 
- http://localhost:9090/api/students/1/enrollments
- List<Enrollment> of a particular student. 
3.Get List of student-enrollments in a particular courseId 
- List<Enrollment> of a particular course. 
- http://localhost:9090/api/courses/1/enrollments

