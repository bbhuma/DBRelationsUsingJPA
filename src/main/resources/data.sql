insert into customer (customer_id, first_name, last_name, gender) values
('CUST001', 'Sunil', 'Darekar', 'M');

insert into address (customer_id, address_type, address_line, city, pincode) values
('CUST001', 'PERMANENT', 'Room No. 90, Shree Sai Aakansha', 'Thane', '401305'),
('CUST001', 'DELIVERY', 'Room No. 90, Shree Sai Aakansha', 'Thane', '401305');

insert into communication (customer_id, phone, email) values
('CUST001', '+919167447650', 'sunil.darekar@gmail.com');

insert into dob (customer_id, birth_date) values
('CUST001', '1976-01-15');

-- First row fixed to match column count
insert into kit_info (customer_id, card_type, card_category, alias_name) values
('CUST001', 'PHYSICAL', 'CARD001', null);

insert into kit_info (customer_id, card_type, card_category, card_status, alias_name) values
('CUST001', 'VIRTUAL', 'CREDIT', 'ACTIVE', 'CARD002');

insert into account_info (customer_id, account_no, account_type, currency, branch_id) values
('CUST001', 'ACC001', 'SAVING', 'INR', 'BR001');


INSERT INTO student (name) VALUES ('Student1');
INSERT INTO student (name) VALUES ('Student2');
INSERT INTO student (name) VALUES ('Student3');
INSERT INTO student (name) VALUES ('Student4');
INSERT INTO student (name) VALUES ('Student5');
INSERT INTO student (name) VALUES ('Student6');
INSERT INTO student (name) VALUES ('Student7');
INSERT INTO student (name) VALUES ('Student8');
INSERT INTO student (name) VALUES ('Student9');
INSERT INTO student (name) VALUES ('Student10');
INSERT INTO course (title) VALUES ('Course1');
INSERT INTO course (title) VALUES ('Course2');
INSERT INTO course (title) VALUES ('Course3');
INSERT INTO course (title) VALUES ('Course4');
INSERT INTO course (title) VALUES ('Course5');
INSERT INTO course (title) VALUES ('Course6');
INSERT INTO course (title) VALUES ('Course7');
INSERT INTO course (title) VALUES ('Course8');
INSERT INTO course (title) VALUES ('Course9');
INSERT INTO course (title) VALUES ('Course10');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 5, '2024-04-06', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (8, 4, '2024-02-07', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (7, 3, '2024-03-12', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (4, 9, '2024-08-29', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (10, 10, '2024-11-03', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 1, '2024-02-24', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (10, 2, '2024-11-28', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (10, 8, '2024-12-31', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (8, 8, '2024-04-05', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 2, '2024-05-27', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (7, 1, '2024-11-12', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 1, '2024-02-16', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 9, '2024-01-03', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 8, '2024-09-29', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (10, 5, '2024-11-25', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (7, 3, '2024-09-14', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 9, '2024-10-22', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (7, 10, '2024-08-10', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (9, 9, '2024-10-14', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (4, 7, '2024-02-24', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 7, '2024-03-20', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (8, 3, '2024-07-23', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 8, '2024-07-24', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (9, 3, '2024-03-19', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 9, '2024-11-14', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (9, 3, '2024-08-01', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 3, '2024-01-19', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 1, '2024-06-28', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (3, 5, '2024-02-05', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 7, '2024-03-26', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 10, '2024-01-26', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 7, '2024-06-16', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (3, 8, '2024-11-24', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 5, '2024-06-03', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 7, '2024-07-18', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (7, 8, '2024-07-17', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (9, 3, '2024-12-28', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (1, 3, '2024-10-21', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 2, '2024-05-18', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (1, 7, '2024-09-12', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 2, '2024-04-17', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 10, '2024-08-13', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (10, 4, '2024-09-25', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (9, 1, '2024-10-09', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (3, 10, '2024-07-06', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 6, '2024-03-20', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 2, '2024-09-07', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (10, 6, '2024-01-10', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (1, 6, '2024-03-26', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (4, 7, '2024-04-14', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (1, 7, '2024-01-01', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (3, 1, '2024-10-13', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 7, '2024-04-30', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 4, '2024-10-09', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (3, 10, '2024-09-08', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 8, '2024-09-26', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (3, 10, '2024-07-28', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 2, '2024-05-12', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 4, '2024-03-31', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (8, 8, '2024-06-06', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 7, '2024-11-20', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (3, 4, '2024-04-07', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (1, 6, '2024-12-19', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (4, 1, '2024-01-26', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 5, '2024-05-06', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (9, 8, '2024-08-19', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (1, 4, '2024-04-11', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 4, '2024-03-07', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (10, 6, '2024-07-28', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (7, 9, '2024-08-18', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 3, '2024-01-12', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (4, 3, '2024-01-28', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (8, 1, '2024-10-24', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 9, '2024-08-01', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (7, 6, '2024-04-27', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 6, '2024-09-14', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 8, '2024-09-19', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (7, 10, '2024-01-01', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 6, '2024-03-03', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (8, 4, '2024-08-28', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (9, 10, '2024-11-29', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 3, '2024-03-06', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 1, '2024-03-10', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 9, '2024-05-16', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (9, 8, '2024-05-23', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (7, 1, '2024-08-11', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (10, 10, '2024-07-14', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (3, 10, '2024-05-01', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (5, 5, '2024-11-27', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 2, '2024-06-15', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (9, 5, '2024-03-28', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (1, 9, '2024-10-18', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (3, 3, '2024-04-01', 'C');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (8, 5, '2024-01-06', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (4, 5, '2024-11-02', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (1, 7, '2024-07-13', 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (7, 10, '2024-11-30', 'B');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (10, 5, '2024-04-13', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (4, 2, '2024-04-09', 'D');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (6, 7, '2024-01-14', 'C');

