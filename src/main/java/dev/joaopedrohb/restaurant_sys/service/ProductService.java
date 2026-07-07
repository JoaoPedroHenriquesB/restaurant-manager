package dev.joaopedrohb.restaurant_sys.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.joaopedrohb.restaurant_sys.DTOs.ProductRequest;
import dev.joaopedrohb.restaurant_sys.DTOs.ProductResponse;
import dev.joaopedrohb.restaurant_sys.domain.entity.Product;
import dev.joaopedrohb.restaurant_sys.domain.entity.ProductCategory;
import dev.joaopedrohb.restaurant_sys.exception.BusinessRuleException;
import dev.joaopedrohb.restaurant_sys.repository.ProductCategoryRepository;
import dev.joaopedrohb.restaurant_sys.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductResponse create(ProductRequest request) {
        ProductCategory productCategory = findCategoryById(request.categoryId());
        Product product = request.toEntity(productCategory);
        Product newProduct = productRepository.save(product);
        return ProductResponse.fromEntity(newProduct);
    }

    public Page<ProductResponse> list(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductResponse::fromEntity);
    }

    public ProductResponse findById(Long id) {
        Product product = findProductById(id);
        return ProductResponse.fromEntity(product);
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findProductById(id);
        ProductCategory productCategory = findCategoryById(request.categoryId());
        request.fill(product, productCategory);
        Product updatedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(updatedProduct);
    }

    public void delete(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new BusinessRuleException("product not found"));
    }

    private ProductCategory findCategoryById(Long id) {
        return productCategoryRepository.findById(id).orElseThrow(() -> new BusinessRuleException("category not found"));
    }
}
