package com.example.demo.exo6.exceptions;

import com.example.demo.exo6.model.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FurnitureNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handlerConflitException(FurnitureNotFoundException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex.getMessage(), ex.getHttpStatus().value(), ex.getTime());
        return new ResponseEntity<>(exceptionDTO, ex.getHttpStatus());
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleCartNotFoundException(CartNotFoundException ex) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex.getMessage(), ex.getHttpStatus().value(), ex.getTime());
        return new ResponseEntity<>(exceptionDTO, ex.getHttpStatus());
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleCartItemNotFoundException(CartItemNotFoundException ex) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex.getMessage(), ex.getHttpStatus().value(), ex.getTime());
        return new ResponseEntity<>(exceptionDTO, ex.getHttpStatus());
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ExceptionDTO> handleOutOfStockException(OutOfStockException ex) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(ex.getMessage(), ex.getHttpStatus().value(), ex.getTime());
        return new ResponseEntity<>(exceptionDTO, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collect(Collectors.joining(" | "));

        ExceptionDTO exceptionDTO = new ExceptionDTO(errorMessage, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
}
