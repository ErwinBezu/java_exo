package com.example.demo.exo6.model.dto;

import java.time.LocalDateTime;

public record ExceptionDTO(String message, int status, LocalDateTime time) {
}
