package dev.joaopedrohb.restaurant_sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.joaopedrohb.restaurant_sys.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
