package dev.joaopedrohb.restaurant_sys.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import dev.joaopedrohb.restaurant_sys.DTOs.PaymentRequest;
import dev.joaopedrohb.restaurant_sys.DTOs.PaymentResponse;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-client", url = "${PAYMENT_SERVICE_URL}")
public interface PaymentClient {

    @PostMapping("/payments/process")
    PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest);

}
