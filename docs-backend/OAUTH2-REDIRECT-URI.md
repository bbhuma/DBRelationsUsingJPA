# OAuth2 Redirect URI: How It Works

## What is a Redirect URI?

A **redirect URI** (sometimes called a callback URL) is the endpoint in your application where the OAuth2 provider (Google, Microsoft, GitHub, etc.) will send the user after they have authenticated and authorized your app.

## How Does It Work?

1. **User starts login:**
   - Your app sends the user to the provider's authorization URL, including your registered redirect URI as a parameter.
2. **User authenticates:**
   - The user logs in and grants permissions.
3. **Provider redirects back:**
   - The provider redirects the user to your redirect URI, appending an authorization code (or error) as a query parameter.
   - Example: `http://localhost:8080/login/oauth2/code/google?code=AUTH_CODE&state=xyz`
4. **Your app exchanges the code:**
   - Your backend receives the code at the redirect URI and exchanges it for an access token.

## Why is it Important?

- The redirect URI must match exactly what you registered with the provider (including protocol, port, and path).
- This prevents attackers from intercepting the authorization code.

## Typical Redirect URIs

- For Spring Boot with Google: `http://localhost:8080/login/oauth2/code/google`
- For Microsoft: `http://localhost:8080/login/oauth2/code/azure`
- For GitHub: `http://localhost:8080/login/oauth2/code/github`
- For Facebook: `http://localhost:8080/login/oauth2/code/facebook`

## In Spring Security

- Spring Security automatically handles the redirect URI for you if you use the default `/login/oauth2/code/{registrationId}` pattern.
- The `{registrationId}` matches the provider key in your `application.yml` (e.g., `google`, `github`).

## In Postman

- When testing OAuth2, set the callback/redirect URI in Postman to match your app's registered redirect URI.
- You must also register this URI with the provider in their developer console.

## Security Note

- Never use a wildcard or overly broad redirect URI in production.
- Always use HTTPS in production.

## References

- [Spring Security OAuth2 Docs](https://docs.spring.io/spring-security/reference/servlet/oauth2/login/authorization-code.html)
- [Google OAuth2 Redirect URIs](https://developers.google.com/identity/protocols/oauth2/web-server#redirect-uri)
