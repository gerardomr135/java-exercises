package com.example.product.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.product.common.ApiException;
import com.example.product.config.ProductProperties;
import com.example.product.domain.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.web.dto.ProductRequest;
import com.example.product.web.dto.ProductResponse;

@Service
@Transactional(readOnly = true)      // default for the class; write methods override it below
public class ProductService {

    private final ProductRepository repository;
    private final ProductProperties properties;

    public ProductService(ProductRepository repository, ProductProperties properties) {
        this.repository = repository;
        this.properties = properties;
    }

    public List<ProductResponse> findAll(String name) {
        var products = (name == null || name.isBlank())
                ? repository.findAll()
                : repository.findByNameContainingIgnoreCase(name);
        return products.stream().map(ProductResponse::from).toList();
    }

    public ProductResponse findById(Long id) {
        return ProductResponse.from(getOrThrow(id));
    }

    public List<ProductResponse> findLowStock() {
        return repository.findByStockLessThanEqual(properties.lowStockThreshold())
                .stream().map(ProductResponse::from).toList();
    }

    public List<ProductResponse> findAffordable(BigDecimal max) {
        return repository.findAffordableInStock(max).stream().map(ProductResponse::from).toList();
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        var saved = repository.save(new Product(request.name(), request.price(), request.stock()));
        return ProductResponse.from(saved);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        var product = getOrThrow(id);
        product.update(request.name(), request.price(), request.stock());
        return ProductResponse.from(product);     // no save() needed: dirty checking flushes at commit
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getOrThrow(id));
    }

    /** Called by order-service (through Feign). Read-check-write inside one transaction + @Version. */
    @Transactional
    public ProductResponse reserve(Long id, int quantity) {
        var product = getOrThrow(id);
        if (product.getStock() < quantity) {
            throw ApiException.conflict("Insufficient stock for product %d: requested %d, available %d"
                    .formatted(id, quantity, product.getStock()));
        }
        product.decreaseStock(quantity);
        return ProductResponse.from(product);
    }

    private Product getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> ApiException.notFound("Product " + id + " not found"));
    }
}
