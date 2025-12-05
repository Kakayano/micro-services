package org.example.recommendationservice.persistence;

import org.example.recommendationservice.model.Recommendation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {
    Recommendation entityToApi(RecommendationEntity entity);
    RecommendationEntity apiToEntity(Recommendation api);
}
