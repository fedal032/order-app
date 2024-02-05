create table order_detail
(
    id          bigint primary key unique not null,
    code        varchar(20)               not null,
    category_id bigint                    not null references category (id) not null,
    order_id    bigint                    not null references orders (id),
    amount      bigint                    not null,
    price       numeric(17, 2)            not null

);
create index order_detail_id_idx on order_detail (order_id);
create index order_detail_cat_id_idx on order_detail (category_id);

create sequence order_detail_seq;