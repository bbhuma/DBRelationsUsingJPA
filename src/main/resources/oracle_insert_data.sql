
insert into customer (customer_id, first_name, last_name, gender) values
('1', 'Sunil', 'Darekar', 'M');

insert into address (customer_id, address_type, address_line, city, pincode) values
('1', 'PERMANENT', 'Room No. 90, Shree Sai Aakansha', 'Thane', '401305');
insert into address (customer_id, address_type, address_line, city, pincode) values
('1', 'DELIVERY', 'Room No. 90, Shree Sai Aakansha', 'Thane', '401305');

insert into communication (customer_id, phone, email) values
('1', '+919167447650', 'sunil.darekar@gmail.com');

insert into dob (customer_id, birth_date) values
('1', to_date('1976-01-15', 'YYYY-MM-DD'));

insert into kit_info (customer_id, card_type, card_category, alias_name) values
('1', 'PHYSICAL', 'CARD001', null);

insert into kit_info (customer_id, card_type, card_category, card_status, alias_name) values
('1', 'VIRTUAL', 'CREDIT', 'ACTIVE', 'CARD002');

insert into account_info (customer_id, account_no, account_type, currency, branch_id) values
('1', 'ACC001', 'SAVING', 'INR', 'BR001');

-- Sample student and enrollment inserts (truncated for brevity)
INSERT INTO student (name) VALUES ('Student1');
INSERT INTO student (name) VALUES ('Student2');
INSERT INTO course (title) VALUES ('Course1');
INSERT INTO course (title) VALUES ('Course2');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (2, 5, to_date('2024-04-06', 'YYYY-MM-DD'), 'A');
INSERT INTO enrollment (student_id, course_id, enrollment_date, grade) VALUES (8, 4, to_date('2024-02-07', 'YYYY-MM-DD'), 'C');
-- Add the remaining records similarly
