package com.bugra.service;

import com.bugra.model.RefreshToken;
import com.bugra.repo.RefreshTokenRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;

    public RefreshTokenService(RefreshTokenRepo refreshTokenRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
    }


    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepo.save(refreshToken);
    }
}
