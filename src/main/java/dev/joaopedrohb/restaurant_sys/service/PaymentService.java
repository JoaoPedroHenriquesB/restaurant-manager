package dev.joaopedrohb.restaurant_sys.service;

import org.springframework.stereotype.Service;

import dev.joaopedrohb.restaurant_sys.DTOs.PaymentRequest;
import dev.joaopedrohb.restaurant_sys.DTOs.PaymentResponse;
import dev.joaopedrohb.restaurant_sys.client.PaymentClient;
import dev.joaopedrohb.restaurant_sys.domain.entity.AccountClosure;
import dev.joaopedrohb.restaurant_sys.domain.entity.Order;
import dev.joaopedrohb.restaurant_sys.domain.entity.Payment;
import dev.joaopedrohb.restaurant_sys.domain.entity.RestaurantTable;
import dev.joaopedrohb.restaurant_sys.domain.enums.OrderStatus;
import dev.joaopedrohb.restaurant_sys.domain.enums.PaymentMethods;
import dev.joaopedrohb.restaurant_sys.domain.enums.PaymentStatus;
import dev.joaopedrohb.restaurant_sys.domain.enums.TableStatus;
import dev.joaopedrohb.restaurant_sys.exception.BusinessRuleException;
import dev.joaopedrohb.restaurant_sys.repository.AccountClosureRepository;
import dev.joaopedrohb.restaurant_sys.repository.OrderRepository;
import dev.joaopedrohb.restaurant_sys.repository.PaymentRepository;
import dev.joaopedrohb.restaurant_sys.repository.RestaurantTableRepository;
import jakarta.transaction.Transactional;

@Service
public class PaymentService {
    private final PaymentClient paymentClient;
    private final AccountClosureRepository accountClosureRepository;
    private final OrderRepository orderRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentClient paymentClient, AccountClosureRepository accountClosureRepository,
            OrderRepository orderRepository, RestaurantTableRepository restaurantTableRepository,
            PaymentRepository paymentRepository) {
        this.paymentClient = paymentClient;
        this.accountClosureRepository = accountClosureRepository;
        this.orderRepository = orderRepository;
        this.restaurantTableRepository = restaurantTableRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void pay(Long orderId, String paymentMethod) {
        AccountClosure accountClosure = accountClosureRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessRuleException("Account closure not found for order id: " + orderId));

        PaymentResponse response = paymentClient
                .processPayment(new PaymentRequest(accountClosure.getTotalAmount(), paymentMethod));

        if ("APPROVED".equals(response.status())) {
            Order order = accountClosure.getOrder();
            order.setStatus(OrderStatus.PAID);

            RestaurantTable table = order.getTable();
            table.setStatus(TableStatus.AVAILABLE);

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setPaymentMethod(PaymentMethods.valueOf(paymentMethod));
            payment.setStatus(PaymentStatus.APPROVED);
            payment.setAmount(accountClosure.getTotalAmount());
            payment.setPaymentDate(accountClosure.getClosingDate());

            paymentRepository.save(payment);
            restaurantTableRepository.save(table);
        }

    }

}
