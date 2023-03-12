package com.huytd.productservice.controller;

import com.huytd.productservice.document.Product;
import com.huytd.productservice.dto.BaseResponse;
import com.huytd.productservice.dto.ProductRequest;
import com.huytd.productservice.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(productController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createProduct() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setDescription("Test Description");
        productRequest.setPrice(BigDecimal.TEN);
        BaseResponse expectedResponse = new BaseResponse();

        when(productService.createProduct(productRequest)).thenReturn(Mono.just(expectedResponse));

        // when
        webTestClient.post()
                .uri("/api/product")
                .body(BodyInserters.fromValue(productRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BaseResponse.class)
                .isEqualTo(expectedResponse);

        // then
        verify(productService).createProduct(productRequest);
    }

    @Test
    void createMultiple() {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setDescription("Test Description");
        productRequest.setPrice(BigDecimal.TEN);
        BaseResponse expectedResponse = new BaseResponse();

        when(productService.createProduct(productRequest)).thenReturn(Mono.just(expectedResponse));

        // when
        webTestClient.post()
                .uri("/api/product")
                .body(BodyInserters.fromValue(productRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BaseResponse.class)
                .isEqualTo(expectedResponse);

        // then
        verify(productService).createProduct(productRequest);
    }

    @Test
    void getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(Product.builder()
                .name("roduct 1")
                .description("escription 1")
                .price(BigDecimal.TEN)
                .build());
        products.add(Product.builder()
                .name("roduct 2")
                .description("Test Description 2")
                .price(BigDecimal.valueOf(20))
                .build());

        when(productService.getAllProducts(anyString(), any(), any(), anyInt(), anyInt()))
                .thenReturn(Mono.just(BaseResponse.builder().data(products).build()));

        webTestClient.get()
                .uri("/api/product?search=test&priceFrom=0&priceTo=100&page=1&size=2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BaseResponse.class)
                .value(response -> {
                    assertEquals("successfully", response.getMessage());
                    assertEquals(2, ((List<Product>) response.getData()).size());
                });
    }
}