package com.eventure.profile_service.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorDTO {
    private String timestamp;
    private int status;
    private String error;
    private String message;

    public ApiErrorDTO(int status, String error, String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
