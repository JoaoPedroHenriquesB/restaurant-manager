package dev.joaopedrohb.restaurant_sys.DTOs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import dev.joaopedrohb.restaurant_sys.domain.entity.Product;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Boolean isAvailable,
        Integer preparationTimeMinutes,
        Long category,
        String categoryName,
        LocalDateTime createdAt) {

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getIsAvailable(),
                product.getPreparationTimeMinutes(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCreatedAt());
    }

}
