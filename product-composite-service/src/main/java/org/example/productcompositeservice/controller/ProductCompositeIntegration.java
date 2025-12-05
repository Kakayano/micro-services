package org.example.productcompositeservice.controller;

import org.example.productcompositeservice.model.ProductComposite;
import org.example.productcompositeservice.model.Product;
import org.example.productcompositeservice.model.Recommendation;
import org.example.productcompositeservice.model.Review;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class ProductCompositeIntegration {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String ProductUrl = "http://product-service:8080/product";
    private static final String ReviewUrl = "http://review-service:8080/review";
    private static final String RecommendationUrl = "http://recommendation-service:8080/recommendation";


    public ProductCompositeIntegration() {}

    public ProductComposite getProductComposite(int productId) {
        Product product = restTemplate.getForObject(ProductUrl + "/" + productId, Product.class);
        List<Review> reviews = List.of(Objects.requireNonNull(restTemplate.getForObject(ReviewUrl + "/" + productId, Review[].class)));
        List<Recommendation> recommendations = List.of(Objects.requireNonNull(restTemplate.getForObject(RecommendationUrl + "?productId=" + productId, Recommendation[].class)));

        return new ProductComposite(product, reviews, recommendations);
    }

    public void createCompositeProduct(ProductComposite body) {
        restTemplate.postForObject(
                ProductUrl,
                new Product(body.product().getProductId(), body.product().getName(), body.product().getWeight()),
                Product.class
        );

        if (body.recommendations() != null) {
            body.recommendations().forEach(r ->
                    restTemplate.postForObject(
                            RecommendationUrl,
                            r,
                            Recommendation.class
                    )
            );
        }

        if (body.reviews() != null) {
            body.reviews().forEach(r ->
                    restTemplate.postForObject(
                            ReviewUrl,
                            r,
                            Review.class
                    )
            );
        }
    }

    public void deleteCompositeProduct(int productId) {

        restTemplate.delete(ProductUrl + "/" + productId);
        restTemplate.delete(RecommendationUrl + "?productId=" + productId);
        restTemplate.delete(ReviewUrl + "/" + productId);

    }
}


