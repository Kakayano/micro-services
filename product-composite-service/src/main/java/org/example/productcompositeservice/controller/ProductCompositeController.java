package org.example.productcompositeservice.controller;

import org.example.productcompositeservice.model.ProductComposite;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-composite")
public class ProductCompositeController {
    private final ProductCompositeIntegration productCompositeIntegration;

    public ProductCompositeController(ProductCompositeIntegration productCompositeIntegration) {
        this.productCompositeIntegration = productCompositeIntegration;
    }

    @GetMapping("/{productId}")
    public ProductComposite getProductComposite(
            @PathVariable("productId") Integer productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID must not be null");
        }

        return productCompositeIntegration.getProductComposite(productId);
    }

    @PostMapping({"", "/"})
    public void createComposite(@RequestBody ProductComposite body) {
        productCompositeIntegration.createCompositeProduct(body);
    }

    @DeleteMapping("/{productId}")
    public void deleteComposite(@PathVariable int productId) {
        productCompositeIntegration.deleteCompositeProduct(productId);
    }

}
