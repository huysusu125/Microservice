package com.huytd.orderservice.controller;

import com.huytd.orderservice.dto.BaseResponse;
import com.huytd.orderservice.dto.OrderLineItemsDto;
import com.huytd.orderservice.dto.OrderRequest;
import com.huytd.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public BaseResponse placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    @PostMapping("/data")
    public void placeOrderDataTest() {
        for (int i = 0; i < 10; ++i) {
            OrderRequest orderRequest = new OrderRequest();
            List<OrderLineItemsDto> orderLineItemsDtoList = new ArrayList<>();
            for (int j = 0; j < 2; ++j) {
                OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto();
                orderLineItemsDto.setPrice(BigDecimal.valueOf(randomNumber()));
                orderLineItemsDto.setQuantity(randomNumber());
                orderLineItemsDto.setSkuCode(generateRandomString(10));
                orderLineItemsDtoList.add(orderLineItemsDto);
            }
            orderRequest.setOrderLineItemsDtoList(orderLineItemsDtoList);
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> orderService.placeOrder(orderRequest));

        }

    }

    public static int randomNumber() {
        Random rand = new Random();
        return rand.nextInt(1000); // Tạo số ngẫu nhiên từ 0 đến 999
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
