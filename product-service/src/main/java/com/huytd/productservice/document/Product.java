package com.huytd.productservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@CompoundIndex(name = "name_description_sku_code_price_idx",
        def = "{'name': 1, 'description': 1, 'sku_code':1, 'price': 1}") // 1 asc 2 desc
@CompoundIndex(name = "name_price_idx",
        def = "{'name' : 1, 'price': 1}")
public class Product {
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("sku_code")
    @Indexed
    private String skuCode;

    @Indexed
    @Field(targetType = FieldType.DECIMAL128, name = "price")
    private BigDecimal price;
}
