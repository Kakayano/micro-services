package org.example.productservice.service;

import org.example.productservice.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProduct(int productId);
    List<Product> getProducts();
    void deleteProduct(int productId);
}
