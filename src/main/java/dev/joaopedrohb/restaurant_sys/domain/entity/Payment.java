package dev.joaopedrohb.restaurant_sys.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import dev.joaopedrohb.restaurant_sys.domain.enums.PaymentMethods;
import dev.joaopedrohb.restaurant_sys.domain.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethods paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "external_transaction_code")
    private String externalTransactionCode;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
