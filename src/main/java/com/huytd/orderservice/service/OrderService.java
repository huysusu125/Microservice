package com.huytd.orderservice.service;

import com.huytd.orderservice.dto.BaseResponse;
import com.huytd.orderservice.dto.OrderRequest;

public interface OrderService {
    BaseResponse placeOrder(OrderRequest orderRequest);
}
