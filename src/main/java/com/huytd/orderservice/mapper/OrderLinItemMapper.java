package com.huytd.orderservice.mapper;

import com.huytd.orderservice.dto.OrderLineItemsDto;
import com.huytd.orderservice.entity.OrderLineItem;

public interface OrderLinItemMapper {
    OrderLineItem mapToEntity(OrderLineItemsDto orderLineItemsDto);
}
