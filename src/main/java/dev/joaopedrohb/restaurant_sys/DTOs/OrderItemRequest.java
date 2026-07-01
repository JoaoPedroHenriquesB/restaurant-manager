package dev.joaopedrohb.restaurant_sys.DTOs;

public record OrderItemRequest(
    Long productId,
    Integer quantity,
    String note
) {
    
}
