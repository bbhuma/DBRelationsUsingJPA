# OAuth2 Security Bean Conflict Error Documentation

## Problem

When both `SecurityConfig` and `OAuth2SecurityConfig` were present in the project, the following error occurred on application startup:

```
The bean 'securityFilterChain', defined in class path resource [com/example/spring_data_jpa_complex_object/config/SecurityConfig.class], could not be registered. A bean with that name has already been defined in class path resource [com/example/spring_data_jpa_complex_object/config/OAuth2SecurityConfig.class] and overriding is disabled.
```

**Spring Boot** does not allow two beans with the same name (`securityFilterChain`) unless bean overriding is explicitly enabled.

## Cause

- Both `SecurityConfig` and `OAuth2SecurityConfig` defined a `@Bean` named `securityFilterChain`.
- By default, Spring Boot does not allow bean definition overriding for safety.
- This caused a startup failure and the application context could not be created.

## Solutions

1. **Remove or comment out one of the conflicting configuration classes.**
   - In this project, `OAuth2SecurityConfig` was removed to resolve the conflict.
2. **Alternatively, enable bean overriding** (not recommended for most cases):
   - Add to `application.properties`:
     ```
     spring.main.allow-bean-definition-overriding=true
     ```
   - This allows Spring to override beans with the same name, but can hide configuration mistakes.

## Resolution

- All OAuth2-related configuration and files were removed.
- The application now starts up without bean conflicts.

---

_This document records the error, its cause, and the steps taken to resolve it for future reference._
