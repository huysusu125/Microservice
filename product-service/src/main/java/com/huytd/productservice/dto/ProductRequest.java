package com.huytd.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank(message = "Name is required ")
    private String name;
    @NotBlank(message = "Description is required ")
    private String description;
    @NotBlank(message = "Price is required ")
    private BigDecimal price;
}
