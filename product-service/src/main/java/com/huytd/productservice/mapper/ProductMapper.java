package com.huytd.productservice.mapper;

import com.huytd.productservice.document.Product;
import com.huytd.productservice.dto.ProductRequest;

public interface ProductMapper {
    Product toDocument(ProductRequest productRequest);
}
