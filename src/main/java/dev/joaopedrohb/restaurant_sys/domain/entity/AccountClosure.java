package dev.joaopedrohb.restaurant_sys.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "account_closures")
@Getter
@Setter
public class AccountClosure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subtotal;

    @Column(name = "service_tax")
    private BigDecimal serviceTax;

    private BigDecimal discount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "closing_date")
    private LocalDateTime closingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @PrePersist
    public void prePersist(){
        this.closingDate = LocalDateTime.now();
    }
}
