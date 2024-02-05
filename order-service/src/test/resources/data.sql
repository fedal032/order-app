insert into customer (id, code, name) VALUES (nextval('customer_seq'), 'cust 1', 'test name 1');
insert into customer (id, code, name) VALUES (nextval('customer_seq'), 'cust 2', 'test name 2');
insert into customer (id, code, name) VALUES (nextval('customer_seq'), 'cust 3', 'test name 3');
insert into customer (id, code, name) VALUES (nextval('customer_seq'), 'cust 4', 'test name 4');

insert into category (id, code, description) VALUES (nextval('category_seq'), 'clothes', 'clothes');
insert into category (id, code, description) VALUES (nextval('category_seq'), 'devices', 'devices');

insert into orders(id, code, status, customer_id) VALUES (nextval('orders_seq'), 'order 1', 'PLACED', 1);
insert into orders(id, code, status, customer_id) VALUES (nextval('orders_seq'), 'order 2', 'PURCHASED', 1);
insert into orders(id, code, status, customer_id) VALUES (nextval('orders_seq'), 'order 3', 'PURCHASED', 2);
insert into orders(id, code, status, customer_id) VALUES (nextval('orders_seq'), 'order 4', 'PLACED', 3);


insert into order_detail (id, code, category_id, order_id, amount, price) VALUES (nextval('order_detail_seq'), 'od-1', 1, 1, 1, 1000);
insert into order_detail (id, code, category_id, order_id, amount, price) VALUES (nextval('order_detail_seq'), 'od-1', 1, 1, 3, 1000);
insert into order_detail (id, code, category_id, order_id, amount, price) VALUES (nextval('order_detail_seq'), 'od-1', 2, 1, 10, 500);
insert into order_detail (id, code, category_id, order_id, amount, price) VALUES (nextval('order_detail_seq'), 'od-2', 1, 2, 10, 200);
insert into order_detail (id, code, category_id, order_id, amount, price) VALUES (nextval('order_detail_seq'), 'od-2', 1, 2, 30, 500);
insert into order_detail (id, code, category_id, order_id, amount, price) VALUES (nextval('order_detail_seq'), 'od-3', 1, 3, 1, 1000);
insert into order_detail (id, code, category_id, order_id, amount, price) VALUES (nextval('order_detail_seq'), 'od-4', 1, 4, 1, 1000);
