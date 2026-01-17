package org.example.recommendationservice.controller;

import org.example.recommendationservice.model.Recommendation;
import org.example.recommendationservice.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Recommendation> getRecommendations(@RequestParam int productId) {
        return service.getRecommendations(productId);
    }

    @PostMapping
    public Recommendation create(@RequestBody Recommendation recommendation) {
        return service.createRecommendation(recommendation);
    }

    @DeleteMapping
    public void delete(@RequestParam int productId) {
        service.deleteRecommendations(productId);
    }

}
