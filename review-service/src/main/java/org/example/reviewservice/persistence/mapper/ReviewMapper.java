package org.example.reviewservice.persistence.mapper;

import org.example.reviewservice.model.Review;
import org.example.reviewservice.persistence.entity.ReviewJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public Review mapToDomainEntity(ReviewJpaEntity entity) {
        return new Review(
                entity.getProductId(),
                entity.getReviewId(),
                entity.getAuthor(),
                entity.getSubject(),
                entity.getContent()
        );
    }

    public ReviewJpaEntity mapToJpaEntity(Review review) {
        ReviewJpaEntity entity = new ReviewJpaEntity();
        entity.setReviewId(review.getReviewId());
        entity.setProductId(review.getProductId());
        entity.setAuthor(review.getAuthor());
        entity.setSubject(review.getSubject());
        entity.setContent(review.getContent());
        return entity;
    }
}
