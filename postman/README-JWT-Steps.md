# Step 1: Design User and Role Database Schema

**Goal:** Store users and roles in the database for secure, scalable authentication and authorization.

## 1.1. Create User, Role, and UserRole Tables

Add the following tables to your database (can be in `data.sql` or a migration):

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
```

- `users`: Stores login credentials and status.
- `roles`: Stores role names (e.g., USER, ADMIN).
- `user_roles`: Many-to-many mapping between users and roles.

## 1.2. (Optional) Link Users to Students/Customers

If you want to associate a user with a student or customer record, add a nullable foreign key to `users`:

```sql
ALTER TABLE users ADD COLUMN student_id BIGINT;
ALTER TABLE users ADD COLUMN customer_id BIGINT;
-- Add foreign key constraints as needed
```

---

**Next:**
- Step 2: Create JPA entities and repositories for User, Role, and UserRole.
- Step 3: Implement user registration, login, and password hashing.
- Step 4: Implement JWT token generation and validation.
- Step 5: Secure endpoints by role.

Each step will be documented in detail for your review and learning.

---

# Step 2: Create JPA Entities and Repositories for User, Role, and UserRole

**Goal:** Map the user/role schema to Java entities and repositories for use with Spring Data JPA and Oracle DB.

## 2.1. Create Entity Classes

Create the following files in `src/main/java/com/example/spring_data_jpa_complex_object/entity/`:

### User.java
```java
package com.example.spring_data_jpa_complex_object.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    // Optionally link to student/customer
    // @OneToOne
    // private Student student;
    // @OneToOne
    // private Customer customer;

    // getters and setters
}
```

### Role.java
```java
package com.example.spring_data_jpa_complex_object.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // getters and setters
}
```

---

## 2.2. Create Repository Interfaces

Create the following files in `src/main/java/com/example/spring_data_jpa_complex_object/repository/`:

### UserRepository.java
```java
package com.example.spring_data_jpa_complex_object.repository;

import com.example.spring_data_jpa_complex_object.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
```

### RoleRepository.java
```java
package com.example.spring_data_jpa_complex_object.repository;

import com.example.spring_data_jpa_complex_object.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
```

---

## 2.3. Oracle DB Notes
- Use `@GeneratedValue(strategy = GenerationType.IDENTITY)` if your Oracle version supports identity columns (12c+). Otherwise, use sequences.
- Adjust column types if needed for Oracle compatibility.

---

**Next:**
- Step 3: Implement user registration, login, and password hashing (BCrypt).
- Step 4: Implement JWT token generation and validation.
- Step 5: Secure endpoints by role.

All steps will be documented in `README-JWT-Steps.md` for your review and learning.

---

# Step 3: Implement User Registration, Login, and Password Hashing (BCrypt)

**Goal:** Allow users to register and log in securely, storing passwords hashed with BCrypt.

## 3.1. Add BCrypt Password Encoder Bean

In your `@Configuration` class (e.g., `SecurityConfig`):

```java
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

## 3.2. User Registration Service

- When registering a user, hash the password before saving.
- Assign default roles (e.g., USER) as needed.

Example service method:

```java
@Autowired
private UserRepository userRepository;
@Autowired
private RoleRepository roleRepository;
@Autowired
private PasswordEncoder passwordEncoder;

public User registerUser(String username, String rawPassword, Set<String> roleNames) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(rawPassword));
    user.setEnabled(true);
    Set<Role> roles = roleNames.stream()
        .map(roleName -> roleRepository.findByName(roleName).orElseThrow())
        .collect(Collectors.toSet());
    user.setRoles(roles);
    return userRepository.save(user);
}
```

## 3.3. User Login Endpoint

- Implement an endpoint (e.g., `/api/auth/login`) that accepts username and password.
- Authenticate the user using Spring Security's `AuthenticationManager`.
- On success, generate and return a JWT (see next step).

Example login controller:

```java
@PostMapping("/api/auth/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtTokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtResponse(jwt));
}
```

---

**Next:**
- Step 4: Implement JWT token generation and validation.
- Step 5: Secure endpoints by role.

All steps will be documented in `README-JWT-Steps.md` for your review and learning.

---

# Step 4: Implement JWT Token Generation and Validation

**Goal:** Securely generate JWT tokens on login and validate them on each request.

## 4.1. Add JWT Utility Class

Create a utility class (e.g., `JwtTokenProvider`) to handle token creation and validation. Use a strong secret key and set token expiration.

Example (using io.jsonwebtoken.Jwts):

```java
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String jwtSecret = "your-very-strong-secret-key";
    private final long jwtExpirationMs = 86400000; // 1 day

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User userPrincipal =
            (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // log error
        }
        return false;
    }
}
```

## 4.2. Add JWT Filter to Spring Security

- Create a filter that checks for the Authorization header, validates the JWT, and sets authentication in the security context.
- Register this filter in your `SecurityConfig`.

Example filter registration:

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
            .antMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
        .and()
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
}
```

---

**Best Practices:**
- Never store JWT tokens in the database; they are stateless.
- Use strong, environment-specific secrets (not hardcoded in code for production).
- Set reasonable expiration and support refresh tokens if needed.
- Include user roles/authorities in the JWT if you want to avoid DB lookups on every request.

---

**Next:**
- Step 5: Secure endpoints by role (e.g., only ADMIN can edit/delete, USER can view).

All steps will be documented in `README-JWT-Steps.md` for your review and learning.

---

# Step 5: Secure Endpoints by Role (Authorization)

**Goal:** Restrict access to endpoints based on user roles (e.g., ADMIN, USER).

## 5.1. Method-Level Security with Annotations

- Use `@PreAuthorize` or `@Secured` annotations on controller methods.
- Example: Only ADMIN can create/update/delete, USER can view.

```java
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Student> getAllStudents() { ... }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Student createStudent(@RequestBody Student student) { ... }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) { ... }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(@PathVariable Long id) { ... }
}
```

- Enable method security in your config:

```java
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter { ... }
```

## 5.2. URL-Based Security (Alternative)

- You can also restrict by URL pattern in your `SecurityConfig`:

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("ADMIN", "USER")
            .antMatchers(HttpMethod.POST, "/api/students/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/students/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/students/**").hasRole("ADMIN")
            .anyRequest().authenticated();
}
```

## 5.3. Best Practices
- Use method-level security for fine-grained control.
- Always test with different roles to ensure correct access.
- Document which endpoints require which roles for your team.

---

# Summary

You now have a full enterprise JWT authentication and authorization setup:
- Users and roles stored in Oracle DB
- Passwords hashed with BCrypt
- JWT tokens generated and validated
- Endpoints secured by role
- All steps documented for learning and review

For further enhancements: add refresh tokens, audit logging, user management UIs, or integrate with SSO/LDAP as needed.
