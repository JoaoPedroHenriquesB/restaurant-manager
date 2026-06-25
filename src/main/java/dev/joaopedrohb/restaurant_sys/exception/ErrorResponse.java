package dev.joaopedrohb.restaurant_sys.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    LocalDateTime timestamp,
    Integer statusCode,
    String error,
    List<String> errorsList
) {
    
}
