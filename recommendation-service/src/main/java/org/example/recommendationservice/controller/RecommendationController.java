package org.example.recommendationservice.controller;

import org.example.recommendationservice.model.Recommendation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class RecommendationController {

    private final AtomicInteger nextId = new AtomicInteger(100);


    @GetMapping("/recommendation")
    public Flux<Recommendation> getRecommendations(@RequestParam(value = "productId", required = true) int productId) {

        List<Recommendation> list = new ArrayList<>();

        list.add(new Recommendation(productId, 1, "Auteur 1", 4, "Super produit"));
        list.add(new Recommendation(productId, 2, "Auteur 2", 5, "Excellent !"));
        list.add(new Recommendation(productId, 3, "Auteur 3", 1, "Pas terrible..."));

        return Flux.fromIterable(list);
    }

    @GetMapping("/recommendation/{recommendationId}")
    public Recommendation getRecommendation(@PathVariable int recommendationId) {
        return new Recommendation(1, recommendationId, "Auteur 1", 4, "Super produit");
    }

    @PostMapping("/recommendation")
    public Recommendation addRecommendation(@RequestBody Recommendation recommendation) {
        recommendation.setRecommendationId(nextId.getAndIncrement());
        return recommendation;
    }

}
