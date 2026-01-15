package com.eventure.profile_service.exception;

import com.eventure.profile_service.DTO.ApiErrorDTO;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleNotFound(ProfileNotFoundException ex) {
        ApiErrorDTO error =
                new ApiErrorDTO(HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity<ApiErrorDTO> handleConflict(ProfileAlreadyExistsException ex) {
        ApiErrorDTO error =
                new ApiErrorDTO(HttpStatus.CONFLICT.value(), "Conflict", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<ApiErrorDTO> handleImageUploadError(ImageUploadException ex) {
        ApiErrorDTO error =
                new ApiErrorDTO(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Image Upload Error",
                        ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}
