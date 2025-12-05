package org.example.reviewservice.persistence;

import org.example.reviewservice.persistence.entity.ReviewJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewJpaEntity, Integer> {
    List<ReviewJpaEntity> findByProductId(Integer productId);
}
