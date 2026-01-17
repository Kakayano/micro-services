package org.example.productcompositeservice.model;

import java.util.List;

public record ProductComposite (
     Product product,
     List<Review> reviews,
     List<Recommendation> recommendations
) {};
