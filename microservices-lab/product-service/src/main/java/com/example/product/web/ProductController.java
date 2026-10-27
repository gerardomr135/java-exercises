package com.example.product.web;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.product.service.ProductService;
import com.example.product.web.dto.ProductRequest;
import com.example.product.web.dto.ProductResponse;
import com.example.product.web.dto.ReserveRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductResponse> list(@RequestParam(required = false) String name) {
        return service.findAll(name);
    }

    @GetMapping("/low-stock")
    public List<ProductResponse> lowStock() {
        return service.findLowStock();
    }

    @GetMapping("/affordable")
    public List<ProductResponse> affordable(@RequestParam BigDecimal max) {
        return service.findAffordable(max);
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        var created = service.create(request);
        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);      // 201 + Location header
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    /** Internal endpoint used by order-service. X-Caller-Service is added by a Feign RequestInterceptor. */
    @PostMapping("/{id}/reserve")
    public ProductResponse reserve(@PathVariable Long id,
                                   @Valid @RequestBody ReserveRequest request,
                                   @RequestHeader(value = "X-Caller-Service", defaultValue = "unknown") String caller) {
        log.info("Reserve {} x product {} requested by {}", request.quantity(), id, caller);
        return service.reserve(id, request.quantity());
    }
}
