package org.example.recommendationservice.service;

import org.example.recommendationservice.model.Recommendation;

import java.util.List;

public interface RecommendationService {
    Recommendation createRecommendation(Recommendation recommendation);
    List<Recommendation> getRecommendations(int productId);
    void deleteRecommendations(int productId);
}
