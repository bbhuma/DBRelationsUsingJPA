# SQL Concepts, Examples, and Best Practices for Enterprise Applications

This document is organized for learning and practicing SQL concepts, with many code examples and interview-style advanced queries.

---

## 1. Table Design & Constraints

### Primary Key

- Uniquely identifies each row in a table.

```sql
CREATE TABLE student (
  student_id INT PRIMARY KEY,
  name VARCHAR(100)
);
```

### Foreign Key

- Enforces referential integrity between tables.

```sql
CREATE TABLE enrollment (
  enrollment_id INT PRIMARY KEY,
  student_id INT,
  course_id INT,
  FOREIGN KEY (student_id) REFERENCES student(student_id),
  FOREIGN KEY (course_id) REFERENCES course(course_id)
);
```

### NOT NULL & UNIQUE

```sql
CREATE TABLE user_account (
  user_id INT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE
);
```

---

## 2. Basic SELECT, WHERE, ORDER BY

```sql
SELECT * FROM student;
SELECT name, marks FROM student WHERE marks > 80 ORDER BY marks DESC;
```

---

## 3. Aggregate Functions

```sql
SELECT COUNT(*) FROM student;
SELECT AVG(marks) FROM student;
SELECT MAX(marks), MIN(marks) FROM student;
SELECT SUM(marks) FROM student WHERE classroom_id = 1;
```

---

## 4. GROUP BY, HAVING, and Aggregation

```sql
SELECT classroom_id, COUNT(*) AS student_count
FROM student
GROUP BY classroom_id;

SELECT classroom_id, AVG(marks) AS avg_marks
FROM student
GROUP BY classroom_id
HAVING AVG(marks) > 70;
```

---

## 5. JOINs (INNER, LEFT, RIGHT, FULL, SELF)

### INNER JOIN

```sql
SELECT s.name, c.name AS class_name
FROM student s
INNER JOIN classroom c ON s.classroom_id = c.id;
```

### LEFT JOIN

```sql
SELECT s.name, c.name AS class_name
FROM student s
LEFT JOIN classroom c ON s.classroom_id = c.id;
```

### RIGHT JOIN

```sql
SELECT s.name, c.name AS class_name
FROM student s
RIGHT JOIN classroom c ON s.classroom_id = c.id;
```

### FULL OUTER JOIN

```sql
SELECT s.name, c.name AS class_name
FROM student s
FULL OUTER JOIN classroom c ON s.classroom_id = c.id;
```

### SELF JOIN

```sql
SELECT e1.name AS employee, e2.name AS manager
FROM employee e1
LEFT JOIN employee e2 ON e1.manager_id = e2.employee_id;
```

---

## 6. Subqueries & Correlated Subqueries

```sql
SELECT name FROM student WHERE marks = (SELECT MAX(marks) FROM student);

SELECT name FROM student WHERE classroom_id IN (
  SELECT id FROM classroom WHERE name = 'Math'
);

-- Correlated subquery
SELECT name FROM student s WHERE marks > (
  SELECT AVG(marks) FROM student WHERE classroom_id = s.classroom_id
);
```

---

## 7. COALESCE and NULL Handling

```sql
SELECT name, COALESCE(marks, 0) AS marks FROM student;
SELECT name, NVL(marks, 0) AS marks FROM student; -- Oracle
```

---

## 8. Date Functions & Examples

```sql
SELECT CURRENT_DATE;
SELECT SYSDATE FROM dual; -- Oracle
SELECT name, birth_date FROM student WHERE birth_date > '2005-01-01';
SELECT name, EXTRACT(YEAR FROM birth_date) AS birth_year FROM student;
SELECT name, birth_date,
  CASE WHEN birth_date < '2000-01-01' THEN 'Old' ELSE 'Young' END AS age_group
FROM student;
SELECT name, birth_date,
  birth_date + INTERVAL '1 year' AS next_year_birthday
FROM student;
```

---

## 9. UNION, UNION ALL, INTERSECT, EXCEPT

```sql
SELECT name FROM student WHERE marks > 90
UNION
SELECT name FROM student WHERE name LIKE 'A%';

SELECT name FROM student WHERE marks > 90
UNION ALL
SELECT name FROM student WHERE name LIKE 'A%';

SELECT name FROM student
INTERSECT
SELECT name FROM alumni;

SELECT name FROM student
EXCEPT
SELECT name FROM alumni;
```

---

## 10. Window Functions & Ranking

```sql
SELECT name, marks, RANK() OVER (ORDER BY marks DESC) AS rank FROM student;
SELECT name, marks, DENSE_RANK() OVER (ORDER BY marks DESC) AS dense_rank FROM student;
SELECT name, marks, ROW_NUMBER() OVER (ORDER BY marks DESC) AS row_num FROM student;
SELECT classroom_id, name, marks,
  AVG(marks) OVER (PARTITION BY classroom_id) AS avg_class_marks
FROM student;
```

---

## 11. Advanced Interview-Style Queries

### Top N per Group

```sql
SELECT * FROM (
  SELECT name, marks, classroom_id,
    ROW_NUMBER() OVER (PARTITION BY classroom_id ORDER BY marks DESC) AS rn
  FROM student
) WHERE rn <= 3;
```

### Find Duplicates

```sql
SELECT name, COUNT(*) FROM student GROUP BY name HAVING COUNT(*) > 1;
```

### Running Total

```sql
SELECT name, marks, SUM(marks) OVER (ORDER BY name) AS running_total FROM student;
```

### Percentage of Total

```sql
SELECT name, marks,
  100.0 * marks / SUM(marks) OVER () AS percent_of_total
FROM student;
```

---

## 12. Constraints & Indexing

```sql
CREATE UNIQUE INDEX idx_unique_email ON user_account(email);
CREATE INDEX idx_student_marks ON student(marks);
ALTER TABLE student ADD CONSTRAINT chk_marks CHECK (marks >= 0 AND marks <= 100);
```

---

## 13. Normalization (1NF, 2NF, 3NF) & Examples

-- (See previous section for detailed normalization steps and examples)

---

## 14. Practice: Write Your Own Queries

- Try to write queries for:
  - Students with above-average marks in their class
  - Courses with no enrollments
  - Teachers who teach more than 3 courses
  - Students who share the same birthday
  - Find the second highest mark in each class

---

## 15. References & Further Reading

- [SQLBolt Interactive Tutorial](https://sqlbolt.com/)
- [LeetCode SQL Interview Questions](https://leetcode.com/problemset/database/)
- [Mode SQL Tutorial](https://mode.com/sql-tutorial/)
- [Oracle SQL Functions](https://docs.oracle.com/en/database/oracle/oracle-database/19/sqlrf/SQL-Functions.html)

---

## 16. Relational Concepts: Parent/Child, References, and Dependencies

### Parent Table, Child Table, and References

- **Parent Table:** The table referenced by a foreign key (e.g., `student` in `enrollment`).
- **Child Table:** The table containing the foreign key (e.g., `enrollment`).
- **Dependency Table:** Another term for child table; depends on the parent for referential integrity.
- **REFERENCES:** Used in table creation to define foreign key relationships.

**Example:**

```sql
CREATE TABLE student (
  student_id INT PRIMARY KEY,
  name VARCHAR(100)
);

CREATE TABLE enrollment (
  enrollment_id INT PRIMARY KEY,
  student_id INT REFERENCES student(student_id),
  course_id INT
);
```

---

### Relationship Types

#### One-to-Many

- One parent row relates to many child rows.
- Example: One student can have many enrollments.

#### Many-to-One

- Many child rows relate to one parent row.
- Example: Many enrollments for one student.

#### Many-to-Many

- Requires a join table.
- Example: Students and courses (a student can enroll in many courses, a course can have many students).

**Example:**

```sql
CREATE TABLE student_course (
  student_id INT REFERENCES student(student_id),
  course_id INT REFERENCES course(course_id),
  PRIMARY KEY (student_id, course_id)
);
```

#### One-to-One

- Each row in one table relates to one row in another.
- Example: Each student has one profile.

**Example:**

```sql
CREATE TABLE student_profile (
  student_id INT PRIMARY KEY REFERENCES student(student_id),
  bio TEXT
);
```

---

### Inserting Records with Relationships

-- Insert parent first, then child:

```sql
INSERT INTO student (student_id, name) VALUES (1, 'Alice');
INSERT INTO enrollment (enrollment_id, student_id, course_id) VALUES (1, 1, 101);
```

-- Many-to-many:

```sql
INSERT INTO student (student_id, name) VALUES (2, 'Bob');
INSERT INTO course (course_id, title) VALUES (201, 'Science');
INSERT INTO student_course (student_id, course_id) VALUES (2, 201);
```

---

### Querying Parent and Child Data

-- Get all enrollments for a student (parent to child):

```sql
SELECT * FROM enrollment WHERE student_id = 1;
```

-- Get student info for an enrollment (child to parent):

```sql
SELECT s.* FROM student s JOIN enrollment e ON s.student_id = e.student_id WHERE e.enrollment_id = 1;
```

-- Get all students and their courses (many-to-many):

```sql
SELECT s.name, c.title
FROM student s
JOIN student_course sc ON s.student_id = sc.student_id
JOIN course c ON sc.course_id = c.course_id;
```

---

### Updating Child Table Data

-- Update a single child row:

```sql
UPDATE enrollment SET course_id = 102 WHERE enrollment_id = 1;
```

-- Update all enrollments for a student:

```sql
UPDATE enrollment SET course_id = 103 WHERE student_id = 1;
```

---

### Querying Parent Table Without Relations

-- Get all students who have no enrollments:

```sql
SELECT s.* FROM student s LEFT JOIN enrollment e ON s.student_id = e.student_id WHERE e.enrollment_id IS NULL;
```

---

### Querying Parent Table With Relations

-- Get all students and their enrollments:

```sql
SELECT s.*, e.* FROM student s LEFT JOIN enrollment e ON s.student_id = e.student_id;
```

---

### When to Use Each Relationship

- **One-to-One:** For rare, unique extensions (e.g., profile, settings).
- **One-to-Many:** Most common, e.g., orders for a customer.
- **Many-to-Many:** For peer relationships, e.g., students/courses, tags/posts.
- **Many-to-One:** The child table points to a single parent, e.g., many enrollments for one student.

---

## 17. More Advanced Queries

-- Find students with no courses (many-to-many):

```sql
SELECT s.* FROM student s LEFT JOIN student_course sc ON s.student_id = sc.student_id WHERE sc.course_id IS NULL;
```

-- Find courses with only one student:

```sql
SELECT c.title FROM course c JOIN student_course sc ON c.course_id = sc.course_id GROUP BY c.title HAVING COUNT(sc.student_id) = 1;
```

-- Update all students in a course:

```sql
UPDATE student SET name = 'Updated' WHERE student_id IN (SELECT student_id FROM student_course WHERE course_id = 201);
```

---

### More Advanced SQL Practice & Interview Queries

-- 1. Find the third highest mark in each class

```sql
SELECT * FROM (
  SELECT name, marks, classroom_id,
    DENSE_RANK() OVER (PARTITION BY classroom_id ORDER BY marks DESC) AS rnk
  FROM student
) WHERE rnk = 3;
```

-- 2. List students who are enrolled in all courses

```sql
SELECT s.name FROM student s
WHERE NOT EXISTS (
  SELECT 1 FROM course c
  WHERE NOT EXISTS (
    SELECT 1 FROM enrollment e WHERE e.student_id = s.student_id AND e.course_id = c.course_id
  )
);
```

-- 3. Find courses with more than 5 students and average marks above 80

```sql
SELECT c.title, COUNT(e.student_id) AS num_students, AVG(e.marks) AS avg_marks
FROM course c
JOIN enrollment e ON c.course_id = e.course_id
GROUP BY c.title
HAVING COUNT(e.student_id) > 5 AND AVG(e.marks) > 80;
```

-- 4. Find students who have never enrolled in any course

```sql
SELECT s.* FROM student s
LEFT JOIN enrollment e ON s.student_id = e.student_id
WHERE e.enrollment_id IS NULL;
```

-- 5. Find the most recent enrollment for each student

```sql
SELECT * FROM (
  SELECT e.*, ROW_NUMBER() OVER (PARTITION BY student_id ORDER BY enrollment_date DESC) AS rn
  FROM enrollment e
) WHERE rn = 1;
```

-- 6. Find students who have the same set of courses as another student

```sql
SELECT s1.name, s2.name AS same_courses_as
FROM student s1
JOIN student s2 ON s1.student_id <> s2.student_id
WHERE NOT EXISTS (
  SELECT 1 FROM student_course sc1
  WHERE sc1.student_id = s1.student_id
    AND NOT EXISTS (
      SELECT 1 FROM student_course sc2 WHERE sc2.student_id = s2.student_id AND sc2.course_id = sc1.course_id
    )
) AND NOT EXISTS (
  SELECT 1 FROM student_course sc2
  WHERE sc2.student_id = s2.student_id
    AND NOT EXISTS (
      SELECT 1 FROM student_course sc1 WHERE sc1.student_id = s1.student_id AND sc1.course_id = sc2.course_id
    )
);
```

-- 7. Find students who have improved their marks in consecutive enrollments

```sql
SELECT s.name, e1.marks AS previous_marks, e2.marks AS current_marks
FROM student s
JOIN enrollment e1 ON s.student_id = e1.student_id
JOIN enrollment e2 ON s.student_id = e2.student_id AND e2.enrollment_date > e1.enrollment_date
WHERE e2.marks > e1.marks;
```

-- 8. Find the average, min, and max marks for each course, and the student with the highest mark

```sql
SELECT c.title, AVG(e.marks) AS avg_marks, MIN(e.marks) AS min_marks, MAX(e.marks) AS max_marks,
  (SELECT s.name FROM student s JOIN enrollment e2 ON s.student_id = e2.student_id WHERE e2.course_id = c.course_id AND e2.marks = MAX(e.marks) FETCH FIRST 1 ROWS ONLY) AS top_student
FROM course c
JOIN enrollment e ON c.course_id = e.course_id
GROUP BY c.title;
```

-- 9. Find students who have enrolled in at least 2 courses in the same semester

```sql
SELECT student_id, semester, COUNT(*) AS num_courses
FROM enrollment
GROUP BY student_id, semester
HAVING COUNT(*) >= 2;
```

-- 10. Find all pairs of students who share at least two courses

```sql
SELECT s1.name, s2.name AS peer, COUNT(*) AS shared_courses
FROM student_course sc1
JOIN student_course sc2 ON sc1.course_id = sc2.course_id AND sc1.student_id < sc2.student_id
JOIN student s1 ON sc1.student_id = s1.student_id
JOIN student s2 ON sc2.student_id = s2.student_id
GROUP BY s1.name, s2.name
HAVING COUNT(*) >= 2;
```

---

## 18. CASE Statements, UPDATE, ALTER, and DELETE Examples

### CASE Statements

```sql
SELECT name, marks,
  CASE
    WHEN marks >= 90 THEN 'A+'
    WHEN marks >= 80 THEN 'A'
    WHEN marks >= 70 THEN 'B'
    WHEN marks >= 60 THEN 'C'
    ELSE 'F'
  END AS grade
FROM student;
```

---

### UPDATE Records

-- Update a single record

```sql
UPDATE student SET name = 'Alice Smith' WHERE student_id = 1;
```

-- Update multiple records

```sql
UPDATE student SET marks = marks + 5 WHERE classroom_id = 2;
```

---

### DELETE Records

-- Delete a single record

```sql
DELETE FROM student WHERE student_id = 1;
```

-- Delete all students with marks below 40

```sql
DELETE FROM student WHERE marks < 40;
```

---

### ALTER TABLE

-- Add a new column

```sql
ALTER TABLE student ADD COLUMN email VARCHAR(255);
```

-- Modify a column (datatype/size)

```sql
ALTER TABLE student MODIFY COLUMN name VARCHAR(200); -- MySQL/Oracle
ALTER TABLE student ALTER COLUMN name TYPE VARCHAR(200); -- PostgreSQL
```

-- Drop a column

```sql
ALTER TABLE student DROP COLUMN email;
```

-- Add a constraint

```sql
ALTER TABLE student ADD CONSTRAINT chk_marks CHECK (marks >= 0 AND marks <= 100);
```

-- Drop a constraint

```sql
ALTER TABLE student DROP CONSTRAINT chk_marks; -- PostgreSQL/Oracle
ALTER TABLE student DROP CHECK chk_marks; -- MySQL
```

---

### TRUNCATE and DROP TABLE

-- Remove all records (cannot be rolled back)

```sql
TRUNCATE TABLE student;
```

-- Drop (delete) the table structure and all data

```sql
DROP TABLE student;
```

---

## 19. Computed/Generated Columns, Aliasing, Pattern Matching, and String Operations

### Computed/Generated Columns

- Computed columns are calculated from other columns in the same row.
- Syntax varies by RDBMS (see below).

**MySQL:**

```sql
CREATE TABLE sales (
  id INT PRIMARY KEY,
  price DECIMAL(10,2),
  quantity INT,
  total DECIMAL(12,2) GENERATED ALWAYS AS (price * quantity) STORED
);
```

**PostgreSQL:**

```sql
CREATE TABLE sales (
  id SERIAL PRIMARY KEY,
  price NUMERIC,
  quantity INT,
  total NUMERIC GENERATED ALWAYS AS (price * quantity) STORED
);
```

**Oracle:**

```sql
CREATE TABLE sales (
  id NUMBER PRIMARY KEY,
  price NUMBER,
  quantity NUMBER,
  total AS (price * quantity) VIRTUAL
);
```

- Use computed columns for derived values (e.g., totals, full names, age from birthdate).
- Can be VIRTUAL (computed on read) or STORED (physically stored, updated on write).

---

### Aliasing Columns and Tables with AS

- `AS` gives a temporary name to a column or table in a query.
- Useful for readability, especially with expressions or joins.

```sql
SELECT name AS student_name, marks AS score FROM student;
SELECT s.name, c.title AS course_title FROM student s JOIN course c ON ...;
SELECT price * quantity AS total_price FROM sales;
```

- Table aliasing:

```sql
SELECT s.name, e.enrollment_date FROM student AS s JOIN enrollment AS e ON s.student_id = e.student_id;
```

---

### Pattern Matching with LIKE and ILIKE

- `LIKE` is used for simple pattern matching (case-sensitive in most DBs).
- `%` matches any sequence of characters, `_` matches a single character.

```sql
SELECT * FROM student WHERE name LIKE 'A%';      -- Names starting with A
SELECT * FROM student WHERE name LIKE '%son';    -- Names ending with 'son'
SELECT * FROM student WHERE name LIKE '_a%';     -- Second letter is 'a'
```

- `ILIKE` (PostgreSQL) is case-insensitive:

```sql
SELECT * FROM student WHERE name ILIKE 'a%';
```

- For case-insensitive search in MySQL/Oracle:

```sql
SELECT * FROM student WHERE LOWER(name) LIKE 'a%';
```

---

### String Concatenation

- Combine columns or strings in SELECT.
- MySQL/PostgreSQL: `CONCAT(a, b)`
- Oracle: `a || b`

```sql
SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM student; -- MySQL/PostgreSQL
SELECT first_name || ' ' || last_name AS full_name FROM student;     -- Oracle/PostgreSQL
```

---

### CASE Expressions for Computed Values

- Use `CASE` to compute values based on conditions.

```sql
SELECT name, marks,
  CASE
    WHEN marks >= 90 THEN 'Excellent'
    WHEN marks >= 75 THEN 'Good'
    ELSE 'Needs Improvement'
  END AS performance
FROM student;
```

---

### More Examples and Interview Tips

- Use computed columns for business logic (e.g., discounts, status flags).
- Use `AS` for clarity in subqueries and complex joins.
- Use `LIKE` for flexible search, but be aware of performance on large tables (consider indexes or full-text search).
- Combine string functions and pattern matching for advanced filtering.

---
