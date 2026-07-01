package dev.joaopedrohb.restaurant_sys.DTOs;

import java.math.BigDecimal;

import dev.joaopedrohb.restaurant_sys.domain.entity.OrderItem;
import dev.joaopedrohb.restaurant_sys.domain.enums.ItemStatusOrder;

public record OrderItemResponse(
        Long id,
        Long orderId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price,
        BigDecimal totalAmount,
        String note,
        ItemStatusOrder status) {

    public static OrderItemResponse fromEntity(OrderItem orderItem) {
        BigDecimal totalAmount = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));

        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                totalAmount,
                orderItem.getNote(),
                orderItem.getStatus());

    }
}
