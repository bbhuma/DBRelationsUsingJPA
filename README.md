====> Load data into H2 using data.sql at the start up of app. YOu can directly start querying on H2. 
1. Use H2 in memory database. Do set up using yaml config. 
2. Create a folder in some location in local machine, as testdb or customerdb. 
3. Reference to that location from JDBC URL -
   datasource:
   url: jdbc:h2:file:C:/Users/mf11475/customerdb #by defualt it will be test, you can create a folder as needed in root locn.
4. access it on console as h2/~customerdb
5. join multiple tables with customer as parent and all other tables which allow many entries for one customer. 
6. SELECT ai.*, a.*,cm.*, d.*,k.* FROM CUSTOMER c
   join account_info as ai on ai.customer_id = c.customer_id
   join address as a on a.customer_id = c.customer_id
   join communication as cm on cm.customer_id = c.customer_id
   join dob as d  on d.customer_id = c.customer_id
   join kit_info as k on k.customer_id = c.customer_id
7. Look for how many rows for each customer - 
8. ![img.png](img.png)
9. One customer with two accounts, two cards, two addresses has cartesian products 2*2*2 = 8 rows of data.
-- Customer basic info
SELECT * FROM CUSTOMER WHERE customer_id = 'cust002';

-- Addresses for customer
SELECT * FROM address WHERE customer_id = 'cust002';

-- Accounts for customer
SELECT * FROM account_info WHERE customer_id = 'cust002';

-- Cards (kit_info) for customer
SELECT * FROM kit_info WHERE customer_id = 'cust002';
10. Best way to group data into arrays of addresses,account_nos,cards is using group_concat function.
-SELECT
    c.customer_id,
    c.first_name,
    c.last_name,
    GROUP_CONCAT(DISTINCT a.address_line SEPARATOR '; ') AS addresses,
    GROUP_CONCAT(DISTINCT ai.account_no SEPARATOR '; ') AS account_nos,
    GROUP_CONCAT(DISTINCT k.alias_name SEPARATOR '; ') AS card_aliases
    FROM CUSTOMER c
    LEFT JOIN address a ON a.customer_id = c.customer_id
    LEFT JOIN account_info ai ON ai.customer_id = c.customer_id
    LEFT JOIN kit_info k ON k.customer_id = c.customer_id
    WHERE c.customer_id = 'C002'
    GROUP BY c.customer_id, c.first_name, c.last_name;
11. ![img_1.png](img_1.png)
