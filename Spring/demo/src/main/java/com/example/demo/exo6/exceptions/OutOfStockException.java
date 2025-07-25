package com.example.demo.exo6.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class OutOfStockException extends RuntimeException {
    private HttpStatus httpStatus;
    private LocalDateTime time;

    public OutOfStockException(String message) {
        super(message);
        this.httpStatus = HttpStatus.CONFLICT;
        this.time = LocalDateTime.now();
    }
}
