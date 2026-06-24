package dev.joaopedrohb.restaurant_sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.joaopedrohb.restaurant_sys.domain.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
