package org.example.reviewservice.service;

import org.example.reviewservice.model.Review;
import org.example.reviewservice.persistence.ReviewRepository;
import org.example.reviewservice.persistence.entity.ReviewJpaEntity;
import org.example.reviewservice.persistence.mapper.ReviewMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByProductId(Integer productId) {
        List<ReviewJpaEntity> entities = reviewRepository.findByProductId(productId);
        return entities.stream()
                .map(reviewMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Review> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Review> getReviewById(Integer reviewId) {
        return reviewRepository.findById(reviewId)
                .map(reviewMapper::mapToDomainEntity);
    }

    @Transactional
    public Review createReview(Review review) {
        ReviewJpaEntity entity = reviewMapper.mapToJpaEntity(review);
        entity.setReviewId(null); // Pour s'assurer que c'est une nouvelle entité
        ReviewJpaEntity savedEntity = reviewRepository.save(entity);
        return reviewMapper.mapToDomainEntity(savedEntity);
    }

    @Transactional
    public Review updateReview(Integer reviewId, Review review) {
        ReviewJpaEntity entity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));

        entity.setProductId(review.getProductId());
        entity.setAuthor(review.getAuthor());
        entity.setSubject(review.getSubject());
        entity.setContent(review.getContent());

        ReviewJpaEntity savedEntity = reviewRepository.save(entity);
        return reviewMapper.mapToDomainEntity(savedEntity);
    }

    @Transactional
    public void deleteAllReviews(Integer productId) {
        reviewRepository.deleteAll(reviewRepository.findByProductId(productId));
    }
}

