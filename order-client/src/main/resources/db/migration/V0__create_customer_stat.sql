create table customer_stat
(
    id            bigint primary key unique not null,
    customer_code varchar(50)               not null,
    total_amount  numeric(17, 2)            not null,
    modified_date date
);
create unique index customer_stat_uidx on customer_stat (customer_code);

create sequence customer_stat_seq;

