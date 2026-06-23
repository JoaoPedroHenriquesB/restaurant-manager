package dev.joaopedrohb.restaurant_sys.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import dev.joaopedrohb.restaurant_sys.domain.enums.OrderStatus;
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
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opening_date")
    private LocalDateTime openingDate;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.OPEN;

    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    @PrePersist
    public void prePersist() {
        this.openingDate = LocalDateTime.now();
    }

}
