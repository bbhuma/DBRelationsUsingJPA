
INSERT INTO Customer (customerId, firstName, lastName, gender) VALUES
('CUST001', 'Sunil', 'Darekar', 'M');

INSERT INTO Address (customerId, addressType, addressLine, city, pincode) VALUES
('CUST001', 'PERMANENT', 'Room No. 90, Shree Sai Aakansha', 'Thane', '401305'),
('CUST001', 'DELIVERY', 'Room No. 90, Shree Sai Aakansha', 'Thane', '401305');

INSERT INTO Communication (customerId, phone, email) VALUES
('CUST001', '+919167447650', 'sunil.darekar@gmail.com');

INSERT INTO DOB (customerId, birthDate) VALUES
('CUST001', '1976-01-15');

INSERT INTO KitInfo (customerId, cardType, cardCategory, cardStatus, aliasName) VALUES
('CUST001', 'PHYSICAL', 'CARD001'),
('CUST001', 'VIRTUAL', 'CREDIT', 'ACTIVE', 'CARD002');

INSERT INTO AccountInfo (customerId, accountNo, accountType, currency, branchId) VALUES
('CUST001', 'ACC001', 'SAVING', 'INR', 'BR001');
