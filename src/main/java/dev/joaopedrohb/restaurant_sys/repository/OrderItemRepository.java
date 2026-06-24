package dev.joaopedrohb.restaurant_sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.joaopedrohb.restaurant_sys.domain.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
