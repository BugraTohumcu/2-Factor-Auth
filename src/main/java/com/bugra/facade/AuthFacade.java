package com.bugra.facade;

import com.bugra.dto.UserResponse;
import com.bugra.mapper.UserResponseMapper;
import com.bugra.model.User;
import com.bugra.service.JwtService;
import com.bugra.service.RefreshTokenService;
import com.bugra.types.Token;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthFacade {

    private final JwtService jwtService;

    public AuthFacade(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void saveTokenAndSetCookie(User user, HttpServletResponse response) {
        UserResponse payload = UserResponseMapper.mapToUserResponse(user);
        String refreshToken = jwtService.createToken(Token.refresh_token, payload);
        String accessToken = jwtService.createToken(Token.access_token, payload);
        jwtService.saveRefreshToken(refreshToken, user);
        jwtService.setJwtCookies(accessToken, refreshToken, response);
    }
}
