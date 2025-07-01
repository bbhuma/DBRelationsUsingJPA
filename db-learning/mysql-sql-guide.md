# MySQL SQL Guide: Syntax, Examples, and Best Practices

## 1. Creating Database and Tables

```sql
CREATE DATABASE school;
USE school;

CREATE TABLE student (
  student_id INT PRIMARY KEY,
  name VARCHAR(100),
  birth_date DATE
);

CREATE TABLE course (
  course_id INT PRIMARY KEY,
  title VARCHAR(100)
);

CREATE TABLE enrollment (
  enrollment_id INT PRIMARY KEY,
  student_id INT,
  course_id INT,
  FOREIGN KEY (student_id) REFERENCES student(student_id),
  FOREIGN KEY (course_id) REFERENCES course(course_id)
);
```

## 2. Insert Records

```sql
INSERT INTO student (student_id, name, birth_date) VALUES (1, 'Alice', '2005-01-01');
INSERT INTO course (course_id, title) VALUES (1, 'Math');
INSERT INTO enrollment (enrollment_id, student_id, course_id) VALUES (1, 1, 1);
```

## 3. Indexing

```sql
CREATE INDEX idx_student_name ON student(name);
```

## 4. Querying Data: Joins, Group By, Having

```sql
-- INNER JOIN
SELECT s.name, c.title
FROM student s
JOIN enrollment e ON s.student_id = e.student_id
JOIN course c ON e.course_id = c.course_id;

-- LEFT OUTER JOIN
SELECT s.name, c.title
FROM student s
LEFT JOIN enrollment e ON s.student_id = e.student_id
LEFT JOIN course c ON e.course_id = c.course_id;

-- GROUP BY, HAVING
SELECT c.title, COUNT(*) AS num_students
FROM enrollment e
JOIN course c ON e.course_id = c.course_id
GROUP BY c.title
HAVING COUNT(*) > 1;
```

## 5. Subqueries & Self Joins

```sql
-- Subquery
SELECT name FROM student WHERE student_id IN (
  SELECT student_id FROM enrollment WHERE course_id = 1
);

-- Self Join (find students with the same birth date)
SELECT s1.name, s2.name AS other_student
FROM student s1
JOIN student s2 ON s1.birth_date = s2.birth_date AND s1.student_id <> s2.student_id;
```

## 6. Normalization

- See main SQL-ADVANCED-DOCS.md for normalization steps and examples.

## 7. MySQL-Specific Notes

- Use `NOW()` for current date/time.
- String concatenation: `CONCAT(first_name, ' ', last_name)`
- Use `LIMIT` for limiting results: `SELECT * FROM student LIMIT 5;`

---

## 8. Advanced Practice & Interview Queries (MySQL)

```sql
-- Top 3 students by marks in each class
SELECT * FROM (
  SELECT s.name, s.classroom_id, e.marks,
    ROW_NUMBER() OVER (PARTITION BY s.classroom_id ORDER BY e.marks DESC) AS rn
  FROM student s
  JOIN enrollment e ON s.student_id = e.student_id
) ranked WHERE rn <= 3;

-- Students with above-average marks in their class
SELECT s.name, e.marks, s.classroom_id
FROM student s
JOIN enrollment e ON s.student_id = e.student_id
WHERE e.marks > (
  SELECT AVG(e2.marks) FROM enrollment e2 WHERE e2.classroom_id = s.classroom_id
);

-- Courses with no enrollments
SELECT c.title FROM course c
LEFT JOIN enrollment e ON c.course_id = e.course_id
WHERE e.course_id IS NULL;

-- Teachers who teach more than 3 courses
SELECT t.name FROM teacher t
JOIN teacher_course tc ON t.teacher_id = tc.teacher_id
GROUP BY t.name
HAVING COUNT(tc.course_id) > 3;

-- Students who share the same birthday
SELECT s1.name, s2.name AS other_student
FROM student s1
JOIN student s2 ON s1.birth_date = s2.birth_date AND s1.student_id <> s2.student_id;

-- Second highest mark in each class
SELECT * FROM (
  SELECT s.name, e.marks, s.classroom_id,
    DENSE_RANK() OVER (PARTITION BY s.classroom_id ORDER BY e.marks DESC) AS rnk
  FROM student s
  JOIN enrollment e ON s.student_id = e.student_id
) ranked WHERE rnk = 2;

-- Find duplicate emails
SELECT email, COUNT(*) FROM user_account GROUP BY email HAVING COUNT(*) > 1;

-- Running total of marks
SELECT s.name, e.marks, SUM(e.marks) OVER (ORDER BY s.name) AS running_total
FROM student s JOIN enrollment e ON s.student_id = e.student_id;

-- Students with no marks (NULL handling)
SELECT s.name, COALESCE(e.marks, 0) AS marks
FROM student s LEFT JOIN enrollment e ON s.student_id = e.student_id;

-- Students with birthdays this month
SELECT name FROM student WHERE MONTH(birth_date) = MONTH(CURDATE());
```
