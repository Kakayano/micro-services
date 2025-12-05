package org.example.productservice.controller;// product-service/src/main/java/com/example/productservice/controller/ProductController.java

import org.example.productservice.model.Product; // Assurez-vous d'importer la bonne classe
import org.example.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping
    public List<Product> getProducts(){
        return service.getProducts();
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable int productId){
        return service.getProduct(productId);
    }

    @PostMapping
    public Product create(@RequestBody Product product){
        return service.createProduct(product);
    }

    @DeleteMapping("/{productId}")
    public void delete(@PathVariable int productId){
        service.deleteProduct(productId);
    }
}