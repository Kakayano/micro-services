package org.example.productcompositeservice.model;


import org.example.productservice.model.Product;
import org.example.recommendationservice.model.Recommendation;
import org.example.reviewservice.models.Review;

import java.util.List;

public class ProductComposite {
    private Product product;
    private List<Review> reviews;
    private List<Recommendation> recommendations;
}
