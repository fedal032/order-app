create table category
(
    id          bigint primary key unique not null,
    code        varchar(20)               not null,
    description varchar(100)
);
create unique index categ_code_uidx on category (code);

create sequence category_seq;