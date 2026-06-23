package dev.joaopedrohb.restaurant_sys.domain.entity;

import dev.joaopedrohb.restaurant_sys.domain.enums.TableStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "restaurant_tables")
@Getter
@Setter
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Integer number;
    private String note;
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private TableStatus status = TableStatus.AVAILABLE;
}
