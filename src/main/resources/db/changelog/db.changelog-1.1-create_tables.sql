-- liquibase formatted sql
-- changeset huytd:1.1

create table if not exists "order"
(
    id           bigserial
    constraint order_pk
    primary key,
    order_number varchar(255)
);
create table if not exists order_line_item
(
    id       bigserial,
    sku_code varchar(255),
    price    numeric,
    quantity bigint,
    order_id bigint
);

ALTER TABLE order_line_item ADD CONSTRAINT order_line_item_order_fk
    FOREIGN KEY (order_id)
        REFERENCES "order" (id)