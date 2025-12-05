package org.example.reviewservice.config;

import org.example.reviewservice.model.Review;
import org.example.reviewservice.service.ReviewService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ReviewService reviewService) {
        return args -> {
            // Vérifier si la base de données est vide
            if (reviewService.getAllReviews().isEmpty()) {
                // Ajouter des données de test
                reviewService.createReview(new Review(1, 0, "Author 1", "Subject 1", "Content 1"));
                reviewService.createReview(new Review(1, 0, "Author 2", "Subject 2", "Content 2"));
                reviewService.createReview(new Review(2, 0, "Author 3", "Subject 3", "Content 3"));

                System.out.println("Base de données initialisée avec des données de test");
            }
        };
    }
}

