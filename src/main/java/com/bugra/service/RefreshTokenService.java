package com.bugra.service;

import com.bugra.model.RefreshToken;
import com.bugra.model.User;
import com.bugra.repo.RefreshTokenRepo;
import com.bugra.security.JwtTokenProvider;
import com.bugra.security.dto.TokenPayload;
import com.bugra.security.dto.TokensRefreshed;
import com.bugra.types.Token;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Ref;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;
    private final JwtTokenProvider jwtTokenProvider;

    public RefreshTokenService(RefreshTokenRepo refreshTokenRepo, JwtTokenProvider jwtTokenProvider) {
        this.refreshTokenRepo = refreshTokenRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public void save(String token, User user) {
        RefreshToken refreshToken = new RefreshToken();
        Instant expiresAt = Instant.now().plus(7, ChronoUnit.DAYS);

        refreshToken.setJti(jwtTokenProvider.extractJti(token));
        refreshToken.setExpires(expiresAt);
        refreshToken.setUser(user);
        refreshTokenRepo.save(refreshToken);
    }


    public TokensRefreshed refreshTokens(String refreshToken, User user) {
        String jti = jwtTokenProvider.extractJti(refreshToken);
        RefreshToken refreshTokenModel = refreshTokenRepo.findByJti(jti);
        if(refreshTokenModel == null) {
            throw new RuntimeException("Refresh Token Not Found");
        }

        TokenPayload payload = TokenPayload.fromUser(user);
        String newAccessToken = jwtTokenProvider.generateAccessToken(payload);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(payload);

        TokensRefreshed tokens = new TokensRefreshed(newAccessToken, newRefreshToken);
        refreshTokenRepo.delete(refreshTokenModel);
        save(newRefreshToken,user);
        return tokens;
    }
}
