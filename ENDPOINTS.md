1. Core Principle:
   - 🔁 The child table holds the foreign key (FK) to the parent.
   - Parent is the main table with lots of redundancy, so create a child out of parent, to hold multiple records of same parent
   - Establish One to many from parent to child, associate the parent PK in child table to tell they are records of parent 
   - Read this relation as One parent has many children
   - Data fow is from many children grouped into a single parent. 
   - This keeps the data flexible, normalized, and avoids redundant columns in the main Student table.
2. Student is primary table, address is associated to student so it becomes a secondary table.
3. Always data flow is from child to parent, where child has pk(id) of parent associated
4. Meaning child is referring to parent object, child id contributing to parent and od not exist independently.
5. Enrollment object contains student and course by default. They fetch the objects from db, the extra details needed are supplied.
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
6. Get List of course-enrollments of a studentId into many courses. 
   - http://localhost:9090/api/students/1/enrollments
   - List<Enrollment> of a particular student. 
7. Get List of student-enrollments in a particular courseId 
   - List<Enrollment> of a particular course. 
   - http://localhost:9090/api/courses/1/enrollments
8. Student-> one to many with Enrollments, to delete student table you need to first delete enrollments. 
9. Course-> one to many with Enrollments, to delete student table you need to first delete enrollments. 
   - Student and Course are independent tables, but they are referenced by some other table, so that needs to be deleted first.
10. Once you commit changes in db, never use Auto_commit, manually run COMMIT; 
    - to commit chnage,s unless changes are committed they are not visible to sprin-boot application 
    - In oracle, Creating db is like creating a database. 
    - You can enable autocommit here:
    In SQL Developer: Tools → Preferences → Database → Worksheet → Autocommit
    - in SQL CLI - DELETE FROM student;
      COMMIT;
11. Transactions are usually managed by Spring (@Transactional) and commit automatically at the end of the method — unless there's an error.
12. Delete child tables first to **avoid FK constraint violations** or Use @Transactional on top of Service or controller/mapping methods.
    - **@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)**
    - cascade = CascadeType.ALL → deletes child entities,when Parent is deleted. (e.g., deletes  Address) when you delete a Customer.
    - orphanRemoval = true → deletes child rows that are removed from the parent list.
    - Customer -> Address,Cards_Info are child tables, records do not exist when customer dont exist. 
      Deleting Customer would delete all his records. That is called Cascade_all delete.
13. **If those are NOT set:**
14. You will get a foreign key constraint error, or
    - The parent (Customer) may be deleted, but the child rows (e.g., Address) will remain, breaking referential integrity.
15. 
    - To avoid foreign key constraint violations, make sure that:
    - You are not directly saving child entities without setting their customer field properly.
    - All child records must be associated with a parent Customer.