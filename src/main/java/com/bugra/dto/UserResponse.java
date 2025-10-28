package com.bugra.dto;

import java.util.Date;

public record UserResponse(String id,
                           String email,
                           String username,
                           Date createdAt) {
}
