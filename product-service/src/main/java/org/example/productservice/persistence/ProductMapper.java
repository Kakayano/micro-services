package org.example.productservice.persistence;

import org.example.productservice.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product entityToApi(ProductEntity entity);
    ProductEntity apiToEntity(Product api);
    List<Product> entityToApi(List<ProductEntity> entities);
}
