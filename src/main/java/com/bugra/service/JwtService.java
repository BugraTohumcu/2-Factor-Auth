package com.bugra.service;

import com.bugra.dto.UserResponse;
import com.bugra.model.RefreshToken;
import com.bugra.model.User;
import com.bugra.security.JwtTokenProvider;
import com.bugra.types.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.bugra.types.Token.access_token;

@Service
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieService cookieService;
    private final RefreshTokenService refreshTokenService;
    private final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public JwtService(JwtTokenProvider jwtTokenProvider, CookieService cookieService, RefreshTokenService refreshTokenService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookieService = cookieService;
        this.refreshTokenService = refreshTokenService;
    }

    public void setJwtCookies(String accessToken, String refreshToken, HttpServletResponse response) {
        ResponseCookie accessCookie = cookieService.setTokensInCookies(access_token.toString(),accessToken);
        ResponseCookie refreshCookie = cookieService.setTokensInCookies(Token.refresh_token.toString(),refreshToken);
        response.addHeader("Set-Cookie", accessCookie.toString());
        response.addHeader("Set-Cookie", refreshCookie.toString());
    }

    public String createToken(Token token_name, UserResponse payload) {
        return switch (token_name) {
            case access_token -> jwtTokenProvider.generateAccessToken(payload);
            case refresh_token -> jwtTokenProvider.generateRefreshToken(payload);
        };
    }

    public void refreshAccessToken(){

    }

    public String getUserIdFromCookieService(HttpServletRequest request) {
        return cookieService.getUserIdFromCookies(request);
    }

    public String getTokenFromCookie(String tokenName, HttpServletRequest request) {
        return cookieService.extractTokenFromCookies(tokenName, request);
    }

    public String getUserMailFromToken(String token) {
        return jwtTokenProvider.extractMail(token);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return jwtTokenProvider.validateToken(token, userDetails);
    }

    public void saveRefreshToken(String token, User user) {
        RefreshToken refreshToken = new RefreshToken();
        Instant expiresAt = Instant.now().plus(7, ChronoUnit.DAYS);

        refreshToken.setJti(jwtTokenProvider.extractJti(token));
        refreshToken.setExpires(expiresAt);
        refreshToken.setUser(user);

        refreshTokenService.save(refreshToken);
    }
}
