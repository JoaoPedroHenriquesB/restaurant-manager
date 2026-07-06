package dev.joaopedrohb.restaurant_sys.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import dev.joaopedrohb.restaurant_sys.domain.enums.ItemStatusOrder;
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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    private String note;

    @Enumerated(EnumType.STRING)
    private ItemStatusOrder status = ItemStatusOrder.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "preparation_date")
    private LocalDateTime preparationDate;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;
}
