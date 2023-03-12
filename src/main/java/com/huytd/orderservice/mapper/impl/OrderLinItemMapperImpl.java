package com.huytd.orderservice.mapper.impl;

import com.huytd.orderservice.dto.OrderLineItemsDto;
import com.huytd.orderservice.entity.OrderLineItem;
import com.huytd.orderservice.mapper.OrderLinItemMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderLinItemMapperImpl implements OrderLinItemMapper {

    @Override
    public OrderLineItem mapToEntity(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItem
                .builder()
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .skuCode(orderLineItemsDto.getSkuCode())
                .build();
    }
}
