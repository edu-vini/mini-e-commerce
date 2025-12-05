package com.grupo5.trainee.minsait.MiniEcommerce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
@Builder
public record ResponseExceptionDTO (
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp,
        String status,
        Integer code,
        String message,
        List<String> errors
){
}
