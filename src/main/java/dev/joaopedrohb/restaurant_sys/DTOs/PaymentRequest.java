package dev.joaopedrohb.restaurant_sys.DTOs;

import java.math.BigDecimal;

public record PaymentRequest(
    BigDecimal amount,
    String paymentMethod
) {
    
}
