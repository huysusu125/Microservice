package com.huytd.orderservice.dto;

import lombok.Data;

@Data
public class InventoryResponse {
    private String skuCode;
    private Long totalInStock;
}
