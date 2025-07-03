1. Customer table has 1 id and 3 main fields, and 5 one-many relations to other tables.

   - @Data
     @Entity
     @Table(name = "Customer")
     public class Customer {
     // Getters and Setters
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "customerId")
     private Long customerId;

     @Column(name = "firstName", nullable = false)
     private String firstName;

     @Column(name = "lastName")
     private String lastName;

     @Column(name = "gender")
     private String gender;

     @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
     private List<Address> addresses;

     @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
     private List<AccountInfo> accounts;

     @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
     private List<Communication> communications;

     @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
     private List<DOB> dobs;

     @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
     private List<KitInfo> kits;

   - 🔹 Simple Columns (3 fields)
     These are mapped **directly to table columns**:
     customerId (Primary Key), firstName, lastName, gender
   - 🔹 Relationships / Collections (5 fields)
     These represent child entities in a one-to-many relationship:
     - addresses → List<Address>
     - accounts → List<AccountInfo>
     - communications → List<Communication>
     - dobs → List<DOB>
     - kits → List<KitInfo>

2. ![img_3.png](img/img_3.png)
3. ![img_4.png](img/img_4.png)
4. ![img_5.png](img/img_5.png)
5. What data you need to insert into customer
   - To insert into the Customer table, you only need 4 fields of data — because only those 4 are actual columns in the Customer table:
   - 🔁 The other 5 (like addresses, accounts, etc.) are not needed unless you are:
   - _Populating the child tables (address, account_info, etc.) at the same time._
   - **Performing a full insert via Customer object with nested children using JPA and cascading**.
6. Example 1: Just inserting Customer
   {
   "firstName": "Priya",
   "lastName": "Sharma",
   "gender": "F"
   }
   → This is enough for customerRepository.save(customer).
7. Example 2: Insert Customer with addresses/accounts/etc.
   {
   "firstName": "Priya",
   "lastName": "Sharma",
   "gender": "F",
   "addresses": [...],
   "accounts": [...],
   ...
   }
   → This requires the full structure with correct foreign keys and cascades.
8. Child entities have customer id as non-nullable filed and as a FK column.
   - So, child tables can not exist without having associated to a customer object.
9. API design for easier operations

   - ✅ You Should Have Both: This is very practical, realistic, and often required in large systems.

   1. Parent-level APIs (CustomerController)
      Create, update, delete, and get full customer with nested child entities.
      Use this when the entire customer object (including addresses, accounts, etc.) is being created or updated in one go.

   2. Child-level APIs (AddressController, AccountController, etc.)
      For individual operations on child entities (like adding a new address, updating phone number, deleting a card, etc.)

10. ![img_6.png](img/img_6.png)
11. ![img_7.png](img/img_7.png)
12. ![img_8.png](img/img_8.png)
    - All child tables will have customer_id as FK, it becomes a main fields of table, must enter the value.
    - This is different from parent table columns, since parent can exist with out child info.
    - But child can not exist without parent info, concept of **Cascade and Orphans.**
    - [img_9.png](img/img_9.png)
13. Explanation of mappedBy, LAZY, orphanRemoval, CASCADE.ALL.

    - @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
      private List<Address> addresses;
    - ![img_10.png](img/img_10.png)

    - Customer customer = customerRepository.findById(id).orElseThrow();
      // JPA has NOT yet loaded addresses

    - List<Address> addresses = **customer.getAddresses()**; // triggers lazy load here!
    - When Spring returns customer in a REST response, **it accesses customer.getAddresses(), which triggers lazy loading** → this is why full address list appears in the JSON output.

14. Option:1 Issue of infinite recursion of JSON between customer and children.
    - This issue you're seeing is a classic case of infinite recursion in JSON serialization — caused by bi-directional relationships between Customer and Address.
    - ![img_11.png](img/img_11.png)
    - ✅ Solution:
    - Use @JsonManagedReference and
      ![img_12.png](img/img_12.png)
    - @JsonBackReference
      ![img_13.png](img/img_13.png)
    - ![img_14.png](img/img_14.png)
15. Add a customer mapper to map DTO's and Entity
    - @PostMapping("/customers")
      public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO dto) {
      Customer customer = customerMapper.toEntity(dto);
      Customer saved = customerRepo.save(customer);
      return ResponseEntity.ok(customerMapper.toDto(saved));
      }
16. ![alt text](img/img.png)
    Here is a PlantUML code snippet for your student-related ER diagram. You can copy and paste this into any PlantUML editor (like https://plantuml.com/ or https://www.planttext.com/) to generate a graphical ER diagram:
