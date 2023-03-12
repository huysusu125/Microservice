package com.huytd.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "\"order_line_item\"")
@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItem extends BaseEntity{
    @Column(name = "sku_code")
    private String skuCode;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // thông qua khóa ngoại order_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Order order;
}
