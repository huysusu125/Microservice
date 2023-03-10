package com.huytd.productservice.mapper.impl;

import com.huytd.productservice.document.Product;
import com.huytd.productservice.dto.ProductRequest;
import com.huytd.productservice.mapper.ProductMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public Product toDocument(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    }
}
