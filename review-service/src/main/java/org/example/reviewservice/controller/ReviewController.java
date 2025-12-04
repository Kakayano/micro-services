package org.example.reviewservice.controller;

import org.example.reviewservice.model.Review;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    public final static List<Review> REVIEWS = Arrays.asList(
            new Review(1, 1, "Author 1", "Subject 1", "Content 1"),
            new Review(1, 2, "Author 2", "Subject 2", "Content 2"),
            new Review(2, 1, "Author 3", "Subject 3", "Content 3")
    );

    @GetMapping("/{productId}")
    public List<Review> getReviews(@PathVariable int productId) {
        List<Review> reviews = new ArrayList<Review>();
        for (Review review : REVIEWS) {
            if (review.getProductId() == productId) {
                reviews.add(review);
            }
        }
        return reviews;
    }
}
