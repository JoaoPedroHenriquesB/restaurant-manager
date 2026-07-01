package dev.joaopedrohb.restaurant_sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.joaopedrohb.restaurant_sys.DTOs.OrderItemRequest;
import dev.joaopedrohb.restaurant_sys.DTOs.OrderItemResponse;
import dev.joaopedrohb.restaurant_sys.DTOs.OrderRequest;
import dev.joaopedrohb.restaurant_sys.DTOs.OrderResponse;
import dev.joaopedrohb.restaurant_sys.domain.entity.Order;
import dev.joaopedrohb.restaurant_sys.domain.entity.OrderItem;
import dev.joaopedrohb.restaurant_sys.domain.entity.Product;
import dev.joaopedrohb.restaurant_sys.domain.entity.RestaurantTable;
import dev.joaopedrohb.restaurant_sys.domain.enums.ItemStatusOrder;
import dev.joaopedrohb.restaurant_sys.domain.enums.OrderStatus;
import dev.joaopedrohb.restaurant_sys.domain.enums.TableStatus;
import dev.joaopedrohb.restaurant_sys.exception.BusinessRuleException;
import dev.joaopedrohb.restaurant_sys.repository.OrderItemRepository;
import dev.joaopedrohb.restaurant_sys.repository.OrderRepository;
import dev.joaopedrohb.restaurant_sys.repository.ProductRepository;
import dev.joaopedrohb.restaurant_sys.repository.RestaurantTableRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, RestaurantTableRepository restaurantTableRepository,
            ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.restaurantTableRepository = restaurantTableRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
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
        Order order = findOrderById(id);
        return OrderResponse.fromEntity(order);
    }

    public OrderItemResponse addNewItemToOrder(Long orderId, OrderItemRequest request) {
        Order order = findOrderById(orderId);

        if (order.getStatus() != OrderStatus.OPEN) {
            throw new BusinessRuleException("cannot add items to a closed order");
        }

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new BusinessRuleException("product not found"));

        if (!product.getIsAvailable()) {
            throw new BusinessRuleException("product not available");
        }

        if (request.quantity() <= 0 || request.quantity() == null) {
            throw new BusinessRuleException("quantity must be greater than zero");
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(request.quantity());
        orderItem.setUnitPrice(product.getPrice());
        orderItem.setNote(request.note());
        orderItem.setStatus(ItemStatusOrder.PENDING);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        return OrderItemResponse.fromEntity(savedOrderItem);
    }

    public List<OrderItemResponse> listItemsByOrderId(Long orderId) {
        Order order = findOrderById(orderId);

        return orderItemRepository.findByOrderId(order.getId()).stream().map(OrderItemResponse::fromEntity).toList();
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new BusinessRuleException("order not found"));
    }
}
