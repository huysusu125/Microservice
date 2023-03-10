package com.huytd.productservice.service;

import com.huytd.productservice.document.Product;
import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.ProductRequest;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Mono<BaseResponse> createProduct(ProductRequest productRequest);

    Mono<BaseResponse> getAllProducts(String search, BigDecimal priceFrom, BigDecimal priceTo, Integer page, Integer size);

    Mono<BaseResponse> createProduct(List<Product> list);
}
