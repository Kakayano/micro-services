package org.example.productcompositeservice.controller;

import org.example.productservice.model.Product;
import org.example.recommendationservice.model.Recommendation;
import org.example.reviewservice.models.Review;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class ProductCompositeIntegration {
    private final WebClient webClient;

    // Utilisez l'injection de dépendances (Dependency Injection) si vous configurez WebClient
    // Sinon, instanciez-le directement.
    public ProductCompositeIntegration() {
        this.webClient = WebClient.builder().build();
    }

    // Appel 1 : Récupérer les informations Produit (simulé ici, à adapter)
    public Flux<Product> getProduct(int productId) {
        String url = "http://localhost:7002/recommendation/" + productId;


        // Puisque nous n'avons pas la BDD, supposons que l'objet Product est retourné.
        return webClient.get().uri(url)
                .retrieve()
                .bodyToFlux(Product.class)
                .onErrorResume(e -> Flux.empty());
    }

    // Appel 2 : Récupérer les Recommandations
    public Flux<Recommendation> getRecommendations(int productId) {
        String url = "http://localhost:7003/recommendation?productId=" + productId;

        return webClient.get().uri(url)
                .retrieve()
                .bodyToFlux(Recommendation.class)
                .onErrorResume(e -> Flux.empty()); // Retourne vide si l'appel échoue
    }

    // Appel 3 : Récupérer les Avis (similaire à l'appel Recommandation)
    public Flux<Review> getReviews(int productId) {
        // L'URL du service Review est http://localhost:7004/review (à définir dans le service Review)
        String url = "http://localhost:7004/review?productId=" + productId;

        return webClient.get().uri(url)
                .retrieve()
                .bodyToFlux(Review.class)
                .onErrorResume(e -> Flux.empty()); // Retourne vide si l'appel échoue
    }
}
