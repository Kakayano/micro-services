package org.example.productservice.controller;// product-service/src/main/java/com/example/productservice/controller/ProductController.java

import org.example.productservice.model.Product; // Assurez-vous d'importer la bonne classe
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    // Liste "stockée en dur" de produits
    private static final List<Product> PRODUCTS = Arrays.asList(
            new Product(1, "Ordinateur Portable - 1", 1500),
            new Product(2, "Clavier mécanique - 2", 200),
            new Product(3, "Souris sans fil - 3", 100),
            new Product(4, "Pc Oled Kyllian le gros PN", 3000)
    );

    // Endpoint GET /product -> retourne la liste complète
    @GetMapping
    public Flux<Product> getProducts() {
        return Flux.fromIterable(PRODUCTS);
    }

    // Endpoint GET /product/{productId} -> retourne un produit par id (404 si non trouvé)
    @GetMapping("/{productId}")
    public Mono<Product> getProduct(@PathVariable int productId) {

        // Recherche dans la liste en dur
        return Flux.fromIterable(PRODUCTS)
                .filter(p -> p.getProductId() == productId)
                .next()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")));
    }
}