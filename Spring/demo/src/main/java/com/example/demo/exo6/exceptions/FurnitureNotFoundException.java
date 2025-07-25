package com.example.demo.exo6.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class FurnitureNotFoundException extends RuntimeException {
    private HttpStatus httpStatus;
    private LocalDateTime time;

    public FurnitureNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
        this.time = LocalDateTime.now();
    }
}