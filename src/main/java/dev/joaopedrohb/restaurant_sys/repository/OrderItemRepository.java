package dev.joaopedrohb.restaurant_sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.joaopedrohb.restaurant_sys.domain.entity.OrderItem;
import dev.joaopedrohb.restaurant_sys.domain.enums.ItemStatusOrder;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findByStatusOrderByIdAsc(ItemStatusOrder status);

}
