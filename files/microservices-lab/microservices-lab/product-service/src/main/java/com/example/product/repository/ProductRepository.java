package com.example.product.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.product.domain.Product;

/** Spring Data generates the implementation at startup; no SQL/boilerplate for CRUD. */
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Derived query: the method name IS the query
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByStockLessThanEqual(int threshold);

    // Explicit JPQL with a named parameter
    @Query("select p from Product p where p.price <= :max and p.stock > 0 order by p.price")
    List<Product> findAffordableInStock(@Param("max") BigDecimal max);
}
