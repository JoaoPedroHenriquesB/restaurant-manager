package dev.joaopedrohb.restaurant_sys.DTOs;

public record OrderRequest(
    Long tableId,
    String note
) {
    
}
