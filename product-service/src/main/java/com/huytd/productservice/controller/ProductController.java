package com.huytd.productservice.controller;

import com.huytd.productservice.document.Product;
import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.ProductRequest;
import com.huytd.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Mono<BaseResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PostMapping("s")
    public Mono<BaseResponse> createMultiple(@RequestBody ProductRequest productRequest) {
        List<Product> list = new LinkedList<>();

        for (int i = 0; i < 20000; ++i) {
            list.add(Product.builder()
                    .name(productRequest.getName() + i)
                    .description(productRequest.getDescription() + i)
                    .price(productRequest.getPrice().add(BigDecimal.valueOf(i)))
                    .build());
        }
        return productService.createProduct(list);
    }

    @GetMapping
    public Mono<BaseResponse> getAllProducts(@RequestParam(required = false) String search,
                                             @RequestParam(required = false, defaultValue = "0") Integer page,
                                             @RequestParam(required = false, defaultValue = "12") Integer size,
                                             @RequestParam(required = false) BigDecimal priceFrom,
                                             @RequestParam(required = false) BigDecimal priceTo
    ) {
        return productService.getAllProducts(search, priceFrom, priceTo, page, size);
    }
}
