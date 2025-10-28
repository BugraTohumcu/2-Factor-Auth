package com.bugra.dto;


import java.time.LocalDateTime;

public record ResponsePattern<T>(String message, T data, boolean success, String timestamp) {

    public ResponsePattern(String message, T data, boolean success) {
        this(message, data, success, LocalDateTime.now().toString());
    }
}
