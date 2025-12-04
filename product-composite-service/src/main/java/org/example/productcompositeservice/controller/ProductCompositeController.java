package org.example.productcompositeservice.controller;

import org.example.productcompositeservice.model.ProductComposite;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-composite")
public class ProductCompositeController {
    private final ProductCompositeIntegration productCompositeIntegration;

    public ProductCompositeController(ProductCompositeIntegration productCompositeIntegration) {
        this.productCompositeIntegration = productCompositeIntegration;
    }

    @GetMapping("/{productId}")
    public ProductComposite getProductComposite(int productId) {
        return productCompositeIntegration.getProductComposite(productId);
    }
}
