package org.example.recommendationservice.service;

import org.example.recommendationservice.model.Recommendation;
import org.example.recommendationservice.persistence.RecommendationMapper;
import org.example.recommendationservice.persistence.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository repository;
    private final RecommendationMapper mapper;

    public RecommendationServiceImpl(RecommendationRepository repository, RecommendationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Recommendation createRecommendation(Recommendation recommendation){
        var entity = mapper.apiToEntity(recommendation);
        var savedEntity = repository.save(entity);
        return mapper.entityToApi(savedEntity);
    }

    @Override
    public List<Recommendation> getRecommendations(int productId){
        return repository.findByProductId(productId)
                .stream()
                .map(mapper::entityToApi)
                .toList();
    }

    @Override
    public void deleteRecommendations(int productId){
        repository.deleteAll(repository.findByProductId(productId));
    }
}
