package dev.joaopedrohb.restaurant_sys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.joaopedrohb.restaurant_sys.domain.entity.AccountClosure;

public interface AccountClosureRepository extends JpaRepository<AccountClosure, Long> {

    Optional<AccountClosure> findByOrderId(Long orderId);
}
