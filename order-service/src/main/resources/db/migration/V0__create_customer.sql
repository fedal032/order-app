create table customer
(
    id           bigint primary key unique not null,
    code         varchar(50)               not null,
    name         varchar(100)              not null,
    created_date date
);
create unique index customer_code_uidx on customer (code);

create sequence customer_seq;

