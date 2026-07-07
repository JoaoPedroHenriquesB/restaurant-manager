package dev.joaopedrohb.restaurant_sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dev.joaopedrohb.restaurant_sys.domain.entity.OrderItem;
import dev.joaopedrohb.restaurant_sys.domain.enums.ItemStatusOrder;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findByStatusOrderByIdAsc(ItemStatusOrder status);

    @Query("""
            SELECT i FROM OrderItem i
            JOIN FETCH i.product
            JOIN FETCH i.order o
            JOIN FETCH o.table t
            WHERE i.status = :status
            ORDER BY i.id ASC
            """)
    List<OrderItem> findItemsWithProductAndOrder(ItemStatusOrder status);

}
