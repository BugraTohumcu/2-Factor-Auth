package com.bugra.config;

import com.bugra.dto.ResponsePattern;
import com.bugra.dto.UserResponse;
import com.bugra.exceptions.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponsePattern<UserResponse>> handleException(UsernameNotFoundException e){
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponsePattern<>(e.getMessage(),null,false));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponsePattern<UserResponse>> handleException(Exception e){
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponsePattern<>(e.getMessage(),null,false));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResponsePattern<String>> handleException(JwtException e){
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponsePattern<>(e.getMessage(),null,false));
    }
}
