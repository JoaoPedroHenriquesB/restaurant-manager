package dev.joaopedrohb.restaurant_sys.DTOs;

import java.math.BigDecimal;

import dev.joaopedrohb.restaurant_sys.domain.entity.Product;
import dev.joaopedrohb.restaurant_sys.domain.entity.ProductCategory;

public record ProductRequest(
        Long categoryId,
        String name,
        String description,
        BigDecimal price,
        Boolean isAvailable,
        Integer preparationTimeMinutes) {

    public Product toEntity(ProductCategory category) {
        Product product = new Product();
        fill(product, category);
        return product;
    }

    public void fill(Product product, ProductCategory category) {
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setIsAvailable(isAvailable != null ? isAvailable : true);
        product.setPreparationTimeMinutes(preparationTimeMinutes);
    }

}
