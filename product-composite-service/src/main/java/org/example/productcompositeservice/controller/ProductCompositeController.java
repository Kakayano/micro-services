package org.example.productcompositeservice.controller;

import org.example.productcompositeservice.model.ProductComposite;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product-composite")
public class ProductCompositeController {
    private final ProductCompositeIntegration productCompositeIntegration;

    public ProductCompositeController(ProductCompositeIntegration productCompositeIntegration) {
        this.productCompositeIntegration = productCompositeIntegration;
    }

    @GetMapping("/{productId}")
    public Mono<ProductComposite> getProductComposite(
            @PathVariable("productId") Integer productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID must not be null");
        }

        return productCompositeIntegration.getProductComposite(productId);
    }

    @PostMapping()
    public Mono<Void> createComposite(@RequestBody ProductComposite body) {
        return productCompositeIntegration.createCompositeProduct(body);
    }

    @DeleteMapping("/{productId}")
    public Mono<Void> deleteComposite(@PathVariable int productId) {
        return productCompositeIntegration.deleteCompositeProduct(productId);
    }

}
