package dev.joaopedrohb.restaurant_sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.joaopedrohb.restaurant_sys.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
