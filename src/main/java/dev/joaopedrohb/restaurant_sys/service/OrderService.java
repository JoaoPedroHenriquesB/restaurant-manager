package dev.joaopedrohb.restaurant_sys.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.joaopedrohb.restaurant_sys.DTOs.OrderRequest;
import dev.joaopedrohb.restaurant_sys.DTOs.OrderResponse;
import dev.joaopedrohb.restaurant_sys.domain.entity.Order;
import dev.joaopedrohb.restaurant_sys.domain.entity.RestaurantTable;
import dev.joaopedrohb.restaurant_sys.domain.enums.OrderStatus;
import dev.joaopedrohb.restaurant_sys.domain.enums.TableStatus;
import dev.joaopedrohb.restaurant_sys.exception.BusinessRuleException;
import dev.joaopedrohb.restaurant_sys.repository.OrderRepository;
import dev.joaopedrohb.restaurant_sys.repository.RestaurantTableRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantTableRepository restaurantTableRepository;

    public OrderService(OrderRepository orderRepository, RestaurantTableRepository restaurantTableRepository) {
        this.orderRepository = orderRepository;
        this.restaurantTableRepository = restaurantTableRepository;

    }

    public OrderResponse openOrder(OrderRequest request) {
        RestaurantTable table = restaurantTableRepository.findById(request.tableId())
                .orElseThrow(() -> new BusinessRuleException("table not found"));

        if (table.getStatus() != TableStatus.AVAILABLE) {
            throw new BusinessRuleException("table not available");
        }

        Order order = new Order();
        order.setTable(table);
        order.setStatus(OrderStatus.OPEN);
        order.setNote(request.note());
        table.setStatus(TableStatus.OCCUPIED);

        Order newOrder = orderRepository.save(order);
        restaurantTableRepository.save(table);

        newOrder.setTable(table);

        return OrderResponse.fromEntity(newOrder);
    }

    public Page<OrderResponse> listOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderResponse::fromEntity);
    }

    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new BusinessRuleException("order not found"));
        return OrderResponse.fromEntity(order);
    }

}
