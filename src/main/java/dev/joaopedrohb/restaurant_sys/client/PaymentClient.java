package dev.joaopedrohb.restaurant_sys.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import dev.joaopedrohb.restaurant_sys.DTOs.PaymentRequest;
import dev.joaopedrohb.restaurant_sys.DTOs.PaymentResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "payment-client", url = "${PAYMENT_SERVICE_URL}")
public interface PaymentClient {

    @PostMapping("/process-payment")
    PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest);

}
