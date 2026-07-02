package dev.joaopedrohb.restaurant_sys.DTOs;

import java.math.BigDecimal;

import dev.joaopedrohb.restaurant_sys.domain.entity.OrderItem;
import dev.joaopedrohb.restaurant_sys.domain.enums.ItemStatusOrder;

public record KitchenItemResponse(
        Long id,
        Long orderId,
        Integer tableNumber,
        String productName,
        Integer quantity,
        String note,
        BigDecimal unitPrice,
        ItemStatusOrder status) {

    public static KitchenItemResponse fromEntity(OrderItem item) {
        return new KitchenItemResponse(
                item.getId(),
                item.getOrder().getId(),
                item.getOrder().getTable().getNumber(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getNote(),
                item.getUnitPrice(),
                item.getStatus());
    }
}