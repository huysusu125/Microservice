package com.huytd.productservice.service.impl;

import com.huytd.productservice.document.Product;
import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.ProductRequest;
import com.huytd.productservice.mapper.ProductMapper;
import com.huytd.productservice.repository.ProductRepository;
import com.huytd.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<BaseResponse> createProduct(ProductRequest productRequest) {
        return productRepository
                .save(productMapper.mapToDocument(productRequest))
                .map(product -> BaseResponse
                        .builder()
                        .data(product.getId())
                        .build());
    }

    @Override
    public Mono<BaseResponse> getAllProducts(String search, BigDecimal priceFrom, BigDecimal priceTo, Integer page, Integer size) {
        Criteria criteria = new Criteria();
        Collection<Criteria> criteriaCollection = new ArrayList<>();
        if (StringUtils.isNotBlank(search)) {
            criteriaCollection.add(new Criteria().orOperator(Criteria.where("name").regex(search.trim(), "i"), Criteria.where("description").regex(search.trim(), "i")));
        }
        if (priceFrom != null) {
            criteriaCollection.add(Criteria.where("price").gte(priceFrom)); // gte >= gt >
        }
        if (priceTo != null) {
            criteriaCollection.add(Criteria.where("price").lte(priceTo));   // lte <= lt >
        }
        if (!criteriaCollection.isEmpty()) {
            criteria = criteria.andOperator(criteriaCollection);
        }
        Sort sort = Sort.by(Sort.Direction.ASC, "price").and(Sort.by(Sort.Direction.ASC, "name"))
                .and(Sort.by(Sort.Direction.ASC, "description"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Query query = new Query(criteria);
        return reactiveMongoTemplate
                .find(query.with(pageRequest), Product.class)
                .collectList()
                .zipWith(reactiveMongoTemplate.count(query, Product.class))
                .map(data -> BaseResponse.builder().data(data).build());

    }

    @Override
    public Mono<BaseResponse> createProduct(List<Product> list) {
        return productRepository
                .saveAll(list)
                .collectList()
                .map(data -> BaseResponse
                        .builder()
                        .data((long) data.size())
                        .build());
    }
}
