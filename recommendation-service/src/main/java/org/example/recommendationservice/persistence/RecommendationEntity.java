package org.example.recommendationservice.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recommendations")
public record RecommendationEntity(
        @Id
        String id,
        int productId,
        int recommendationId,
        String author,
        int rate,
        String content
) {}
