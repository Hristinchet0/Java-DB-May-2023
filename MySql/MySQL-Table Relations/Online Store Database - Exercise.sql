CREATE DATABASE onlineStore ;

CREATE TABLE items (
	item_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    item_type_id INT
);

CREATE TABLE item_types (
item_type_id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL
);

CREATE TABLE cities (
	city_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE customers (
	customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    birthday DATE NOT NULL,
    city_id INT
);

CREATE TABLE orders (
	order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT
);

CREATE TABLE order_items (
	order_id INT,
    item_id INT,
    CONSTRAINT pk
    PRIMARY KEY (order_id, item_id)
);

#връзка между items - item_types -> FK
ALTER TABLE items
ADD CONSTRAINT fk_items_item_types
FOREIGN KEY (item_type_id)
REFERENCES item_types(item_type_id);

#връзка между  order_items - items -> FK
ALTER TABLE order_items
ADD CONSTRAINT fk_orderitems_items
FOREIGN KEY (item_id)
REFERENCES items(item_id);

#връзка между order_items - orders -> FK
ALTER TABLE order_items
ADD CONSTRAINT fk_orderitems_orders
FOREIGN KEY (order_id)
REFERENCES orders(order_id);

#връзка между customers - cities -> FK
ALTER TABLE customers
ADD CONSTRAINT fk_customers_cities
FOREIGN KEY (city_id)
REFERENCES cities(city_id);

#връзка между orders - customers -> FK
ALTER TABLE orders
ADD CONSTRAINT fk_orders_customers
FOREIGN KEY (customer_id)
REFERENCES customers(customer_id);




