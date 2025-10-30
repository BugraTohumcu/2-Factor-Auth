# Auth-System

Lightweight Spring Boot authentication demo with JWT and refresh token support (two-factor-ready architecture).

## Summary

This repository is a Spring Boot (Java 17) application providing a simple authentication system with JWT tokens and refresh tokens, backed by PostgreSQL. It includes endpoints for registering, logging in, and fetching the current user. The refresh endpoint exists but appears unimplemented in the controller.

## Tech stack

- Java 17
- Spring Boot 3.x (parent 3.5.6)
- Spring Security
- Spring Data JPA
- PostgreSQL (runtime dependency)
- jjwt (JSON Web Token)
- Lombok (compile-time convenience)
- BouncyCastle (crypto)

## Project structure (important files)

- `src/main/java/com/bugra/controller/AuthController.java` - REST endpoints for auth
- `src/main/java/com/bugra/service/UserService.java` - user registration/login logic
- `src/main/java/com/bugra/security` - JWT filters, provider and related security classes
- `src/main/java/com/bugra/facade/AuthFacade.java` - token cookie handling (saves cookie on login/register)
- `src/main/resources/application.properties` - configuration (DB, jwt.secret)

## Requirements

- Java 17 (JDK)
- Maven (the project contains the wrapper `mvnw` / `mvnw.cmd`)
- PostgreSQL (or change datasource to your DB)


## API Endpoints

Base path: `/auth`

1) Register

- POST `/auth/register`
- Request JSON (fields validated):

```json
{
  "username": "exampleUser",   // min length 6
  "password": "secret",       // min 3, max 6 (note: this small max appears in DTO validation)
  "email": "me@example.com"
}
```

- Success: returns a `ResponsePattern<UserResponse>` and sets tokens as cookie(s) via `AuthFacade`.

2) Login

- POST `/auth/login`
- Request JSON:

```json
{
  "email": "me@example.com",
  "password": "secret"
}
```

- On success: `ResponsePattern<UserResponse>` and token cookie(s) set.

3) Get current user

- GET `/auth/user`
- Reads user id from cookie via JWT service and returns `UserResponse`:

```json
{
  "id": "...",
  "email": "me@example.com",
  "username": "exampleUser",
  "createdAt": "2025-10-28T..."
}
```

4) Refresh token

- POST `/auth/refresh_token` is present in `AuthController` but currently returns `null` (unimplemented). See `RefreshTokenService` and `RefreshToken` model for intended behavior.
