package org.example.productservice.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public record ProductEntity(
        @Id
        String id,
        int productId,
        String name,
        int weight
) {}
