package dev.joaopedrohb.restaurant_sys.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.joaopedrohb.restaurant_sys.DTOs.OrderRequest;
import dev.joaopedrohb.restaurant_sys.DTOs.OrderResponse;
import dev.joaopedrohb.restaurant_sys.service.OrderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse openOrder(@RequestBody OrderRequest request) {
        return orderService.openOrder(request);
    }

    @GetMapping()
    public Page<OrderResponse> listOrders(Pageable pageable) {
        return orderService.listOrders(pageable);
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return orderService.findById(id);
    }
}
