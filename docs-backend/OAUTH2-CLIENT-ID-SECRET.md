# OAuth2 Client ID and Secret: How to Create and Use Them

## What Are Client ID and Secret?

- **Client ID** and **Client Secret** are credentials used by your application to authenticate with an OAuth2 provider (like Google, Microsoft, etc.).
- They are required for OAuth2 login to work.

## Do I Need an Account?

- **Yes**, you need a (free) account with the provider (e.g., Google) to register your app and obtain these credentials.
- For learning/testing, you can use your personal Google account.

## How to Create Google OAuth2 Credentials

1. Go to the [Google Cloud Console](https://console.cloud.google.com/).
2. Sign in with your Google account (create one if you don't have it).
3. Create a new project (or select an existing one).
4. In the left menu, go to **APIs & Services > Credentials**.
5. Click **Create Credentials** > **OAuth client ID**.
6. Set the application type to **Web application**.
7. Add an authorized redirect URI:
   - For local testing: `http://localhost:8080/login/oauth2/code/google`
8. Click **Create**.
9. Copy the **Client ID** and **Client Secret** shown.

## How to Use in Spring Boot

- Place the values in your `application.yml`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_CLIENT_ID
            client-secret: YOUR_CLIENT_SECRET
            scope: openid, profile, email
```

## Sample (Non-Working) Values

> There are no public test credentials for Google OAuth2. You must use your own.

```yaml
client-id: 1234567890-abc123def456.apps.googleusercontent.com
client-secret: GOCSPX-abcdefg1234567hijklmnop
```

These are **examples only** and will not work for real authentication.

## Notes

- Never share your real client secret publicly.
- You can delete credentials from the Google Cloud Console at any time.
- For other providers (Microsoft, Facebook, etc.), the process is similar—register your app in their developer portal.

## References

- [Google OAuth2 Docs](https://developers.google.com/identity/protocols/oauth2)
- [Spring Security OAuth2 Docs](https://docs.spring.io/spring-security/reference/servlet/oauth2/login/index.html)
