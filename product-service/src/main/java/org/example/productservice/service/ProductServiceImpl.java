package org.example.productservice.service;

import org.example.productservice.model.Product;
import org.example.productservice.persistence.ProductEntity;
import org.example.productservice.persistence.ProductMapper;
import org.example.productservice.persistence.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Product createProduct(Product product){
        var entity = mapper.apiToEntity(product);
        var savedEntity = repository.save(entity);
        return mapper.entityToApi(savedEntity);
    }

    @Override
    public List<Product> getProducts(){
        List<ProductEntity> productsEntities = repository.findAll();
        return mapper.entityToApi(productsEntities);
    }

    @Override
    public Product getProduct(int productId){
        ProductEntity productEntity = repository.findByProductId(productId);
        return mapper.entityToApi(productEntity);
    }

    @Override
    public void deleteProduct(int productId){
        repository.delete(repository.findByProductId(productId));
    }
}
