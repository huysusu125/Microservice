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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
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
                .save(productMapper.toDocument(productRequest))
                .map(product -> BaseResponse
                        .builder()
                        .data(product.getId())
                        .build());
    }

    @Override
    public Mono<BaseResponse> getAllProducts(String search, BigDecimal priceFrom, BigDecimal priceTo, Integer page, Integer size) {
        Criteria criteria = new Criteria();
//        if (StringUtils.isNotBlank(search)) {
//            criteria = criteria.andOperator(Criteria.where("name").regex(search, "i").orOperator(Criteria.where("description").regex(search, "i")));
//        }
//        if (priceFrom != null) {
//            criteria = criteria.andOperator(Criteria.where("price").gte(priceFrom)); // gte >= gt >
//        }
//
//        if (priceTo != null) {
//            criteria = criteria.andOperator(Criteria.where("price").lte(priceTo));   // lte >= lt >
//        }
        Criteria priceCriteria = new Criteria();
        if (priceFrom != null) {
            priceCriteria = priceCriteria.andOperator(Criteria.where("price").gte(priceFrom));
        }
        if (priceTo != null) {
            priceCriteria = priceCriteria.andOperator(Criteria.where("price").lte(priceTo));
        }

        criteria = criteria.andOperator(priceCriteria);
        Sort sort = Sort.by(Sort.Direction.ASC, "price").and(Sort.by(Sort.Direction.ASC, "name"))
                .and(Sort.by(Sort.Direction.ASC,"description"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Query query = new Query(criteria);
        return reactiveMongoTemplate
                .find(query.with(pageRequest), Product.class)
                .collectList()
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
