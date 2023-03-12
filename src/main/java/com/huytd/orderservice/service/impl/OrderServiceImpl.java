package com.huytd.orderservice.service.impl;

import com.huytd.orderservice.dto.BaseResponse;
import com.huytd.orderservice.dto.OrderRequest;
import com.huytd.orderservice.entity.Order;
import com.huytd.orderservice.entity.OrderLineItem;
import com.huytd.orderservice.mapper.OrderLinItemMapper;
import com.huytd.orderservice.repository.OrderRepository;
import com.huytd.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderLinItemMapper orderLinItemMapper;
    @Override
    public BaseResponse placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLinItemMapper::mapToEntity)
                .toList();
        order.setOrderLineItemsList(orderLineItems);
        Long orderId = orderRepository.save(order).getId();
        return BaseResponse.builder().data(orderId).build();
    }
}
