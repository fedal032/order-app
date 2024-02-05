insert into customer (id, code, name) VALUES (nextval('customer_seq'), 'cust 1', 'test name 1');
insert into customer (id, code, name) VALUES (nextval('customer_seq'), 'cust 2', 'test name 2');

insert into category (id, code, description) VALUES (nextval('category_seq'), 'clothes', 'clothes');
insert into category (id, code, description) VALUES (nextval('category_seq'), 'devices', 'devices');

commit ;