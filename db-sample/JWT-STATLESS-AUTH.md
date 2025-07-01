# JWT Authentication: In-Depth Technical Guide

## 1. What is JWT?

JWT (JSON Web Token) is a compact, URL-safe means of representing claims to be transferred between two parties. In authentication, it is used to securely transmit user identity and roles.

---

## 2. JWT Auth Flow in This Project

### a. User Login

- User sends credentials to `/api/auth/login`.
- Backend authenticates and generates a JWT:
  ```java
  String token = Jwts.builder()
      .setSubject(user.getUsername())
      .claim("roles", user.getRoles().stream().map(Role::getName).toList())
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + 3600_000))
      .signWith(SignatureAlgorithm.HS256, secretKey)
      .compact();
  ```
- JWT is returned to the client:
  ```json
  { "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..." }
  ```

### b. Client Usage

- Client stores JWT (e.g., localStorage, memory).
- Sends JWT in `Authorization: Bearer <token>` header for each API call.

### c. Stateless Validation

- On each request, a JWT filter extracts and validates the token:
  ```java
  String header = request.getHeader("Authorization");
  if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      Claims claims = Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody();
      String username = claims.getSubject();
      List<String> roles = claims.get("roles", List.class);
      // Set authentication in SecurityContext
  }
  ```
- No DB lookup is needed for authentication; all info is in the token.

---

## 3. JWT Structure

- **Header:** Algorithm and token type
- **Payload:** User ID, username, roles, and other claims
- **Signature:** HMAC or RSA signature using the server's secret key

### Example JWT Payload

```json
{
  "sub": "student1",
  "roles": ["STUDENT"],
  "iat": 1719874800,
  "exp": 1719878400
}
```

---

## 4. SecurityConfig Example (JWT Filter Integration)

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }
}
```

## 5. JWT Filter Example

```java
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String secretKey;
    public JwtAuthenticationFilter(String secretKey) { this.secretKey = secretKey; }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
                String username = claims.getSubject();
                List<String> roles = claims.get("roles", List.class);
                // Build Authentication object and set in context
                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, null, roles.stream().map(SimpleGrantedAuthority::new).toList());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JwtException e) {
                // Invalid token
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
```

---

## 6. Security Best Practices

- Keep the secret key safe and never expose it.
- Use HTTPS for all API calls.
- Set short expiration times for JWTs (e.g., 15-60 minutes).
- For logout or token revocation, use a blacklist or refresh token system.
- Validate all claims and check user status in the DB if needed for sensitive actions.

---

## 7. Advanced: Refresh Tokens

- For longer sessions, issue a short-lived JWT and a long-lived refresh token.
- Store refresh tokens securely (DB or cache) and require them for re-issuing JWTs.

---

## 8. References

- See `SecurityConfig.java` for JWT filter integration.
- See `README-JWT-Steps.md` for implementation steps and best practices.
- See Spring Security and jjwt (Java JWT) documentation for more.
