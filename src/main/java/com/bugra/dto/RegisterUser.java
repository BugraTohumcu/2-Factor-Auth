package com.bugra.dto;

import com.bugra.shared.AuthMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUser(
        @NotBlank(message = AuthMessages.USERNAME_REQUIRED)
        @Size(min = 6)
        String username,

        @NotBlank(message = AuthMessages.PASSWORD_REQUIRED)
        @Size(min = 3,max = 6)
        String password,

        @Email(message = AuthMessages.EMAIL_INVALID)
        @NotBlank(message = AuthMessages.EMAIL_REQUIRED)
        String email) {
}
