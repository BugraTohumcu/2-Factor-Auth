package com.bugra.exceptions;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JwtException extends AuthenticationException {
    private final int statusCode;
    public JwtException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }
}
