package com.huytd.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity{
    @Column(name = "order_number")
    private String orderNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @ToString.Exclude
    private List<OrderLineItem> orderLineItemsList;

}
