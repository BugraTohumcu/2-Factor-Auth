package com.bugra.mapper;

import com.bugra.dto.UserResponse;
import com.bugra.model.User;

public class UserResponseMapper {


    public static UserResponse mapToUserResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getCreatedAt()
        );
    }
}
