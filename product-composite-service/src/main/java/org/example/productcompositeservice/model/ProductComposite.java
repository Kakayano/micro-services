package org.example.productcompositeservice.model;


import org.example.productservice.model.Product;
import org.example.recommendationservice.model.Recommendation;
import org.example.reviewservice.models.Review;

import java.util.List;

public record ProductComposite (
     Product product,
     List<Review> reviews,
     List<Recommendation> recommendations
) {};
