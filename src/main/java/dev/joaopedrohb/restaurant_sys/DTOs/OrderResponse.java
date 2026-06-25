package dev.joaopedrohb.restaurant_sys.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;

import dev.joaopedrohb.restaurant_sys.domain.entity.Order;
import dev.joaopedrohb.restaurant_sys.domain.enums.OrderStatus;

public record OrderResponse(
        Long id,
        Long tableId,
        Integer tableNumber,
        LocalDateTime openingDate,
        LocalDate closingDate,
        OrderStatus status,
        String note) {

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTable().getId(),
                order.getTable().getNumber(),
                order.getOpeningDate(),
                order.getClosingDate(),
                order.getStatus(),
                order.getNote());
    };

}
