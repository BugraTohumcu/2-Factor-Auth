package com.bugra.security.dto;

import com.bugra.model.User;

import java.util.Date;

public record TokenPayload(String id,
                           String email,
                           String username,
                           Date createdAt)
{

    public static TokenPayload fromUser(User user){
        return new TokenPayload(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }
}

