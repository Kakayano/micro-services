package org.example.productcompositeservice.controller;

import org.example.productcompositeservice.model.ProductComposite;
import org.example.productcompositeservice.model.Product;
import org.example.productcompositeservice.model.Recommendation;
import org.example.productcompositeservice.model.Review;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCompositeIntegration {
    private final WebClient webClient;
    private static final String ProductUrl = "http://product-service/product";
    private static final String ReviewUrl = "http://review-service/review";
    private static final String RecommendationUrl = "http://recommendation-service/recommendation";

    public ProductCompositeIntegration(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<ProductComposite> getProductComposite(int productId) {
        return Mono.zip(
                webClient.get().uri(ProductUrl + "/" + productId).retrieve().bodyToMono(Product.class),
                webClient.get().uri(RecommendationUrl + "?productId=" + productId).retrieve().bodyToFlux(Recommendation.class).collectList(),
                webClient.get().uri(ReviewUrl + "/" + productId).retrieve().bodyToFlux(Review.class).collectList()
        ).map(t -> new ProductComposite(t.getT1(), t.getT3(), t.getT2()));
    }

    public Mono<Void> createCompositeProduct(ProductComposite body) {
        List<Mono<Void>> tasks = new ArrayList<>();

        tasks.add(webClient.post()
                .uri(ProductUrl)
                .bodyValue(body.product())
                .retrieve()
                .bodyToMono(Void.class));

        if (body.recommendations() != null) {
            body.recommendations().forEach(r ->
                    tasks.add(webClient.post()
                            .uri(RecommendationUrl)
                            .bodyValue(r)
                            .retrieve()
                            .bodyToMono(Void.class))
            );
        }

        if (body.reviews() != null) {
            body.reviews().forEach(r ->
                    tasks.add(webClient.post()
                            .uri(ReviewUrl)
                            .bodyValue(r)
                            .retrieve()
                            .bodyToMono(Void.class))
            );
        }

        return Mono.when(tasks);
    }

    public Mono<Void> deleteCompositeProduct(int productId) {
        return Mono.zip(
                webClient.delete().uri(ProductUrl + "/" + productId).retrieve().bodyToMono(Void.class),
                webClient.delete().uri(RecommendationUrl + "?productId=" + productId).retrieve().bodyToMono(Void.class),
                webClient.delete().uri(ReviewUrl + "/" + productId).retrieve().bodyToMono(Void.class)
        ).then();
    }
}