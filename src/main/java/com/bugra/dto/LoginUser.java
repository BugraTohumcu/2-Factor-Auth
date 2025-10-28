package com.bugra.dto;

import com.bugra.shared.AuthMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUser(
        @NotBlank(message = AuthMessages.EMAIL_REQUIRED)
        @Email(message = AuthMessages.EMAIL_INVALID)
        String email,
        @NotBlank(message = AuthMessages.PASSWORD_REQUIRED)
        @Size(min = 3,max = 6)
        String password) {
}
