package org.example.productservice.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    ProductEntity findByProductId(int productId);
}
