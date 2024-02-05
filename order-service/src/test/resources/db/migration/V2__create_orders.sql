create table orders
(
    id          bigint primary key unique not null,
    code        varchar(20)               not null,
    status      varchar(20)
        constraint order_status_cc check ( status in ('PLACED', 'CANCELED', 'PURCHASED') ),
    created_date date,
    modified_date date,
    customer_id bigint not null references customer(id)
);

create index order_cust_id_idx on orders (customer_id);

create sequence orders_seq;