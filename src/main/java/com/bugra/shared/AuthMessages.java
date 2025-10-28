package com.bugra.shared;
public interface AuthMessages {

    // ===== Registration =====
    String USER_CREATED_SUCCESSFULLY = "User registered successfully.";
    String EMAIL_ALREADY_EXISTS = "Email is already in use.";
    String USER_ALREADY_EXISTS = "User already exists.";
    String USER_NOT_FOUND = "User not found.";

    // ===== Login =====
    String INVALID_CREDENTIALS = "Invalid username or password.";
    String ACCOUNT_DISABLED = "Account is disabled.";
    String LOGIN_SUCCESS = "Login successful.";

    // ===== Token / Session =====
    String TOKEN_REFRESHED = "Access token refreshed successfully.";
    String TOKEN_INVALID = "Invalid or expired token.";
    String LOGOUT_SUCCESS = "Logout successful.";

    // ===== Password =====
    String PASSWORD_UPDATED = "Password updated successfully.";
    String PASSWORD_INCORRECT = "Incorrect current password.";

    // ===== Validation =====
    String USERNAME_REQUIRED = "Username is required.";
    String EMAIL_REQUIRED = "Email is required.";
    String EMAIL_INVALID = "Email format is invalid.";
    String PASSWORD_REQUIRED = "Password is required.";
    String PASSWORD_TOO_SHORT = "Password must be at least 8 characters long.";
    String PASSWORD_TOO_WEAK = "Password must contain uppercase, lowercase, digit and special character.";
}

