package com.grupo5.trainee.minsait.MiniEcommerce.exception;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.ResponseExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseExceptionDTO> genericException(Exception e){
        ResponseExceptionDTO dto = ResponseExceptionDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(e.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.internalServerError().body(dto);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseExceptionDTO> responseStatus(ResponseStatusException e){
        ResponseExceptionDTO dto = ResponseExceptionDTO.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .message(e.getReason())
                .code(e.getStatusCode().value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseExceptionDTO> entityNotFoundHandler(EntityNotFoundException e){
        ResponseExceptionDTO dto = ResponseExceptionDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(e.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.internalServerError().body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseExceptionDTO> handleValidationErrors(MethodArgumentNotValidException e) {

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ResponseExceptionDTO dto = ResponseExceptionDTO.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .message("Erro de validação nos campos enviados")
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();

        return ResponseEntity.badRequest().body(dto);
    }
}
