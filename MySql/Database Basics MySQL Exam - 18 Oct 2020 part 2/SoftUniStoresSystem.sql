CREATE DATABASE SoftUniStoresSystem;
USE SoftUniStoresSystem;

/*Data Definition Language (DDL) – 40 pts
Make sure you implement the whole database correctly on your local machine, so that you could work with it.
The instructions you’ll be given will be the minimal required for you to implement the database.

1.	Table Design
You have been tasked to create the tables in the database by the following models:
*/

CREATE TABLE pictures(
id INT PRIMARY KEY AUTO_INCREMENT,
url VARCHAR(100) NOT NULL,
added_on DATETIME NOT NULL
);

CREATE TABLE categories(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE products(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(40) NOT NULL UNIQUE,
best_before DATE,
price DECIMAL(10, 2) NOT NULL,
description TEXT,
category_id INT NOT NULL,
picture_id INT NOT NULL,
CONSTRAINT fk_products_categories
FOREIGN KEY (category_id)
REFERENCES categories(id),
CONSTRAINT fk_products_pictures
FOREIGN KEY (picture_id)
REFERENCES pictures(id)
);

CREATE TABLE towns(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE addresses(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL UNIQUE,
town_id INT NOT NULL,
CONSTRAINT fk_addresses_towns
FOREIGN KEY (town_id)
REFERENCES towns(id)
);

CREATE TABLE stores(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20) NOT NULL UNIQUE,
rating FLOAT NOT NULL,
has_parking TINYINT(1) DEFAULT FALSE,
address_id INT NOT NULL,
CONSTRAINT fk_stores_addresses
FOREIGN KEY (address_id)
REFERENCES addresses(id)
);


CREATE TABLE employees(
id INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(15) NOT NULL,
middle_name CHAR,
last_name VARCHAR(20) NOT NULL,
salary DECIMAL(19, 2) DEFAULT 0,
hire_date DATE NOT NULL,
manager_id INT,
store_id INT NOT NULL,
CONSTRAINT fk_employee_store
FOREIGN KEY (store_id)
REFERENCES stores(id),
CONSTRAINT sf_fk_employee_manager
FOREIGN KEY (manager_id)
REFERENCES employees(id)
);

CREATE TABLE products_stores(
product_id INT NOT NULL,
store_id INT NOT NULL,
PRIMARY KEY (product_id, store_id),
CONSTRAINT fk_product_store_product 
FOREIGN KEY (product_id)
REFERENCES products(id),
CONSTRAINT fk_product_store_store  
FOREIGN KEY (store_id)
REFERENCES stores(id)
);

insert into pictures (id, url, added_on) values (1, 'http://dummyimage.com/102x210.jpg/cc0000/ffffff', '2019-12-31 04:02:09');
insert into pictures (id, url, added_on) values (2, 'http://dummyimage.com/184x230.jpg/ff4444/ffffff', '2020-01-21 06:50:01');
insert into pictures (id, url, added_on) values (3, 'http://dummyimage.com/243x132.jpg/cc0000/ffffff', '2018-11-14 07:43:32');
insert into pictures (id, url, added_on) values (4, 'http://dummyimage.com/113x194.jpg/5fa2dd/ffffff', '2019-10-21 05:46:54');
insert into pictures (id, url, added_on) values (5, 'http://dummyimage.com/241x194.jpg/5fa2dd/ffffff', '2018-10-03 15:44:27');
insert into pictures (id, url, added_on) values (6, 'http://dummyimage.com/119x189.jpg/dddddd/000000', '2020-04-22 02:28:19');
insert into pictures (id, url, added_on) values (7, 'http://dummyimage.com/235x227.jpg/cc0000/ffffff', '2018-10-02 16:53:52');
insert into pictures (id, url, added_on) values (8, 'http://dummyimage.com/208x226.jpg/cc0000/ffffff', '2018-08-21 21:35:00');
insert into pictures (id, url, added_on) values (9, 'http://dummyimage.com/102x172.jpg/cc0000/ffffff', '2020-02-28 21:45:35');
insert into pictures (id, url, added_on) values (10, 'http://dummyimage.com/171x139.jpg/ff4444/ffffff', '2020-06-12 22:54:29');
insert into pictures (id, url, added_on) values (11, 'http://dummyimage.com/204x242.jpg/5fa2dd/ffffff', '2019-08-19 23:00:33');
insert into pictures (id, url, added_on) values (12, 'http://dummyimage.com/145x238.jpg/dddddd/000000', '2019-02-17 01:57:52');
insert into pictures (id, url, added_on) values (13, 'http://dummyimage.com/189x151.jpg/dddddd/000000', '2018-10-01 22:12:36');
insert into pictures (id, url, added_on) values (14, 'http://dummyimage.com/249x208.jpg/cc0000/ffffff', '2018-12-04 16:53:24');
insert into pictures (id, url, added_on) values (15, 'http://dummyimage.com/114x161.jpg/ff4444/ffffff', '2018-04-12 16:15:54');
insert into pictures (id, url, added_on) values (16, 'http://dummyimage.com/221x111.jpg/cc0000/ffffff', '2019-01-28 09:46:22');
insert into pictures (id, url, added_on) values (17, 'http://dummyimage.com/193x103.jpg/ff4444/ffffff', '2018-11-21 07:51:15');
insert into pictures (id, url, added_on) values (18, 'http://dummyimage.com/178x248.jpg/5fa2dd/ffffff', '2020-01-06 23:45:02');
insert into pictures (id, url, added_on) values (19, 'http://dummyimage.com/243x233.jpg/cc0000/ffffff', '2018-12-18 04:40:51');
insert into pictures (id, url, added_on) values (20, 'http://dummyimage.com/197x104.jpg/cc0000/ffffff', '2018-07-28 01:43:34');


insert into categories values
(1, 'foods'),
(2, 'drinks'),
(3, 'clothes'),
(4, 'others'),
(5, 'vehicles');


insert into towns (id, name) values 
(1, 'Sofia'),
(2, 'Plovdiv'), 
(3, 'Varna'),
(4, 'Burgas'),
(5, 'Blagoevgrad'),
(6, 'Vidin'),
(7, 'Lovech'),
(8, 'Pleven'),
(9, 'Silistra'),
(10, 'Razgrad'),
(11, 'Gabrovo'),
(12, 'Sliven'),
(13, 'Dobrich'),
(14, 'Shumen'),
(15, 'Ruse'),
(16, 'Pazardjik'),
(17, 'Nesebar'),
(18, 'Sozopol'),
(19, 'Sandanski'),
(20, 'Pamporovo');

insert into addresses (id, name, town_id) values (1, '1 Cody Pass', 13);
insert into addresses (id, name, town_id) values (2, '07 Armistice Parkway', 4);
insert into addresses (id, name, town_id) values (3, '2388 Erie Drive', 4);
insert into addresses (id, name, town_id) values (4, '4 Hansons Way', 15);
insert into addresses (id, name, town_id) values (5, '96 Pankratz Drive', 8);
insert into addresses (id, name, town_id) values (6, '51515 Loftsgordon Trail', 14);
insert into addresses (id, name, town_id) values (7, '29395 Larry Pass', 17);
insert into addresses (id, name, town_id) values (8, '396 Summerview Center', 18);
insert into addresses (id, name, town_id) values (9, '2217 Fieldstone Court', 20);
insert into addresses (id, name, town_id) values (10, '0155 Morningstar Junction', 1);
insert into addresses (id, name, town_id) values (11, '789 Lien Plaza', 5);
insert into addresses (id, name, town_id) values (12, '83637 Reinke Alley', 6);
insert into addresses (id, name, town_id) values (13, '1656 Anniversary Way', 14);
insert into addresses (id, name, town_id) values (14, '91 Bayside Pass', 1);
insert into addresses (id, name, town_id) values (15, '35952 Stoughton Circle', 5);
insert into addresses (id, name, town_id) values (16, '61346 Melody Lane', 6);
insert into addresses (id, name, town_id) values (17, '32759 Dwight Plaza', 5);
insert into addresses (id, name, town_id) values (18, '6 Caliangt Parkway', 8);
insert into addresses (id, name, town_id) values (19, '18645 Hollow Ridge Drive', 14);
insert into addresses (id, name, town_id) values (20, '816 Old Gate Point', 17);

insert into stores (id, name, rating, has_parking, address_id) values (1, 'Wrapsafe', 7.1, false, 20);
insert into stores (id, name, rating, has_parking, address_id) values (2, 'Solarbreeze', 2.3, true, 1);
insert into stores (id, name, rating, has_parking, address_id) values (3, 'Regrant', 1.2, false, 18);
insert into stores (id, name, rating, has_parking, address_id) values (4, 'Aerified', 6.5, false, 4);
insert into stores (id, name, rating, has_parking, address_id) values (5, 'Cardguard', 6.8, false, 16);
insert into stores (id, name, rating, has_parking, address_id) values (6, 'Duobam', 8.1, false, 15);
insert into stores (id, name, rating, has_parking, address_id) values (7, 'Keylex', 3.6, false, 14);
insert into stores (id, name, rating, has_parking, address_id) values (8, 'Alphazap', 6.5, true, 6);
insert into stores (id, name, rating, has_parking, address_id) values (9, 'Flexidy', 7.3, true, 12);
insert into stores (id, name, rating, has_parking, address_id) values (10, 'Bitwolf', 8.5, true, 8);
insert into stores (id, name, rating, has_parking, address_id) values (11, 'Home Ing', 6.3, true, 10);
insert into stores (id, name, rating, has_parking, address_id) values (12, 'SafeWithUs', 2.0, false, 9);
insert into stores (id, name, rating, has_parking, address_id) values (13, 'Konklab', 5.5, true, 11);
insert into stores (id, name, rating, has_parking, address_id) values (14, 'Veribet', 7.6, true, 7);
insert into stores (id, name, rating, has_parking, address_id) values (15, 'Zaam-Dox', 7.4, false, 13);
insert into stores (id, name, rating, has_parking, address_id) values (16, 'Lotstring', 8.2, false, 5);
insert into stores (id, name, rating, has_parking, address_id) values (17, 'Stronghold', 5.1, true, 17);
insert into stores (id, name, rating, has_parking, address_id) values (18, 'DuoStore', 7.8, false, 3);
insert into stores (id, name, rating, has_parking, address_id) values (19, 'Transcof', 3.0, false, 2);
insert into stores (id, name, rating, has_parking, address_id) values (20, 'Voltsillam', 9.9, false, 19);

insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (1, 'Clemence', 'F', 'Darthe', '9253.81', '2008-10-12', NULL, 18);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (2, 'Florian', 'E', 'Bamlet', '6266.27', '2018-02-19', NULL, 14);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (3, 'Carolyn', 'Q', 'Dyett', '1223.45', '2000-02-23', NULL, 5);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (4, 'Eba', 'X', 'Oleszkiewicz', '5268.40', '2015-02-09', NULL, 5);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (5, 'Fletch', 'Y', 'de Najera', '2144.71', '2007-04-14', NULL, 8);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (6, 'Roz', 'U', 'Dewdney', '9316.56', '2018-10-20', NULL, 6);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (7, 'Shae', 'O', 'Fasey', '7463.52', '2018-02-03', NULL, 12);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (8, 'Breena', 'S', 'Hymans', '7849.65', '2017-03-15', NULL, 17);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (9, 'Jonie', 'J', 'McNicol', '3597.01', '2012-01-11', NULL, 4);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (10, 'Goldina', 'G', 'Seabrocke', '3285.16', '2009-03-17', NULL, 17);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (11, 'Rhona', 'Q', 'Pettwood', '2651.20', '2011-04-08', NULL, 5);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (12, 'Jacintha', 'S', 'Polly', '2505.62', '2009-10-27', NULL, 7);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (13, 'Elwin', 'G', 'Rennock', '9538.20', '2017-05-12', NULL, 12);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (14, 'Dierdre', 'B', 'Jaggard', '3943.56', '2014-09-25', NULL, 4);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (15, 'Richy', 'E', 'Cady', '3091.96', '2002-12-21', NULL, 19);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (16, 'Rube', 'F', 'Daveren', '1028.20', '2014-02-24', NULL, 19);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (17, 'Faun', 'U', 'Winkworth', '6709.77', '2007-05-06', NULL, 1);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (18, 'Xylina', 'W', 'Apfelmann', '6845.08', '2013-05-03', NULL, 7);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (19, 'Frasquito', 'Y', 'Wimpey', '9124.43', '2011-11-25', NULL, 12);
insert into employees (id, first_name, middle_name, last_name, salary, hire_date, manager_id, store_id) values (20, 'Leigh', 'D', 'Vedenyakin', '2159.55', '2002-07-11', NULL, 17);

insert into products (id, name, best_before, price, description, category_id, picture_id) values (1, 'Container Clear 8 Oz', '2020-04-05', 6.12, 'Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo. Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.', 4, 4);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (2, 'Oil - Sunflower', '2019-10-25', 48.0, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin interdum mauris non ligula pellentesque ultrices.', 4, 19);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (3, 'Goat - Whole Cut', '2020-08-15', 39.78, 'Mauris ullamcorper purus sit amet nulla.', 1, 2);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (4, 'Cranberries - Fresh', '2019-10-18', 23.21, 'In hac habitasse platea dictumst.', 2, 3);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (5, 'Bamboo Shoots - Sliced', '2020-05-27', 14.46, 'Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl. Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum. Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.', 1, 1);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (6, 'Pasta - Bauletti, Chicken White', '2020-02-08', 48.85, 'Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat. In congue. Etiam justo. Etiam pretium iaculis justo. In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum.', 1, 5);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (7, 'Lemonade - Mandarin, 591 Ml', '2020-04-03', 25.53, 'Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.', 2, 8);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (8, 'Daikon Radish', '2019-12-28', 29.95, 'Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui. Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti. Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum.', 4, 10);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (9, 'Ostrich - Fan Fillet', '2019-10-08', 42.63, 'Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh. Quisque id justo sit amet sapien dignissim vestibulum.', 1, 6);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (10, 'Rabbit - Saddles', '2020-04-19', 32.01, 'Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque. Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus. In sagittis dui vel nisl.', 1, 11);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (11, 'Green Scrubbie Pad H.duty', '2020-05-21', 31.29, 'Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl. Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum. Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est. Phasellus sit amet erat.', 4, 7);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (12, 'Chicken - Whole', '2020-05-25', 3.76, 'Morbi quis tortor id nulla ultrices aliquet. Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui. Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti. Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum.', 1, 9);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (13, 'Rice - Wild', '2019-12-21', 18.85, 'Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis. Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem.', 1, 15);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (14, 'Pork - Back, Short Cut, Boneless', '2020-02-16', 32.83, 'Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', 1, 13);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (15, 'Spic And Span All Purpose', '2020-05-31', 36.53, 'Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus. Phasellus in felis. Donec semper sapien a libero. Nam dui.', 4, 14);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (16, 'Soup - Cream Of Potato / Leek', '2020-07-29', 26.41, 'Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit. Vivamus vel nulla eget eros elementum pellentesque. Quisque porta volutpat erat.', 1, 12);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (17, 'Wine - Ruffino Chianti Classico', '2020-03-03', 21.63, 'Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo. Maecenas pulvinar lobortis est.', 2, 18);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (18, 'Food Colouring - Green', '2020-03-12', 34.18, 'Morbi ut odio. Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo.', 1, 17);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (19, 'Sour Puss Raspberry', '2019-09-03', 37.93, 'Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.', 1, 16);
insert into products (id, name, best_before, price, description, category_id, picture_id) values (20, 'Sugar - White Packet', '2019-11-14', 40.89, 'Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus. Nulla suscipit ligula in lacus. Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam. Nam tristique tortor eu pede.', 1, 20);


insert into products_stores (product_id, store_id) values (17, 2);
insert into products_stores (product_id, store_id) values (19, 20);
insert into products_stores (product_id, store_id) values (7, 9);
insert into products_stores (product_id, store_id) values (11, 12);
insert into products_stores (product_id, store_id) values (7, 17);
insert into products_stores (product_id, store_id) values (6, 10);
insert into products_stores (product_id, store_id) values (1, 14);
insert into products_stores (product_id, store_id) values (2, 18);
insert into products_stores (product_id, store_id) values (15, 19);
insert into products_stores (product_id, store_id) values (12, 11);
insert into products_stores (product_id, store_id) values (4, 5);
insert into products_stores (product_id, store_id) values (8, 15);
insert into products_stores (product_id, store_id) values (8, 13);
insert into products_stores (product_id, store_id) values (19, 18);
insert into products_stores (product_id, store_id) values (14, 3);
insert into products_stores (product_id, store_id) values (1, 11);
insert into products_stores (product_id, store_id) values (3, 7);
insert into products_stores (product_id, store_id) values (11, 3);
insert into products_stores (product_id, store_id) values (5, 14);
insert into products_stores (product_id, store_id) values (15, 18);
insert into products_stores (product_id, store_id) values (4, 12);
insert into products_stores (product_id, store_id) values (20, 6);
insert into products_stores (product_id, store_id) values (6, 8);
insert into products_stores (product_id, store_id) values (14, 13);
insert into products_stores (product_id, store_id) values (1, 18);
insert into products_stores (product_id, store_id) values (2, 8);
insert into products_stores (product_id, store_id) values (4, 2);
insert into products_stores (product_id, store_id) values (2, 6);
insert into products_stores (product_id, store_id) values (11, 11);

/*Data Manipulation Language (DML) – 30 pts
Here we need to do several manipulations in the database, like changing data, adding data etc.

2.	Insert
You will have to insert records of data into the products_stores table, based on the products table. 
Find all products that are not offered in any stores (don’t have a relation with stores) and insert data in the 
products_stores. For every product saved -> product_id and 1(one) as a store_id. And now this product will be offered in store with name Wrapsafe and id 1.
•	product_id – id of product
•	store_id – set it to be 1 for all products.
*/

INSERT INTO products_stores (product_id, store_id) 
(SELECT p.id, 1 FROM products AS p
		WHERE p.id NOT IN 
        (SELECT product_id FROM products_stores));


/*3.	Update
Update all employees that hire after 2003(exclusive) year and not work in store Cardguard and Veribet. 
Set their manager to be Carolyn Q Dyett (with id 3) and decrease salary with 500.
*/

UPDATE employees AS e
SET e.salary = e.salary - 500, e.manager_id = 3
WHERE year(e.hire_date) >= 2003
AND e.store_id NOT IN (SELECT s.id FROM stores AS s
						WHERE s.name = 'Cardguard' OR s.name = 'Veribet');

/*4.	Delete
It is time for the stores to start working. All good employees already are in their stores. 
But some of the employers are too expensive and we need to cut them, because of finances restrictions.
Be careful not to delete managers they are also employees.
Delete only those employees that have managers and a salary is more than 6000(inclusive)
*/

DELETE FROM employees
WHERE salary >= 6000 AND manager_id IS NOT NULL;

/*Querying – 50 pts
And now we need to do some data extraction. Note that the example results from this section use a fresh database. It is highly recommended that you clear the database that has been manipulated by the previous problems from the DML section and insert again the dataset you’ve been given, to ensure maximum consistency with the examples given in this section.

5.	Employees 
Extract from the SoftUni Stores System database, info about all of the employees. 
Order the results by employees hire date in descending order.
Required Columns
•	first_name
•	middle_name
•	last_name
•	salary
•	hire_date
*/

SELECT first_name, middle_name, last_name, salary, hire_date FROM employees
ORDER BY hire_date DESC;

/*6.	Products with old pictures
A photographer wants to take pictures of products that have old pictures. You must select all of the products that have a description more than 100 characters long description, and a picture that is made before 2019 (exclusive) and the product price being more than 20. Select a short description column that consists of first 10 characters of the picture's description plus '…'. Order the results by product price in descending order.

Required Columns
•	name (product)
•	price 
•	best_before
•	short_description  
o	only first 10 characters of product description + '...'
•	url 
*/

SELECT p.name AS 'product_name', p.price, p.best_before, CONCAT(left(p.description, 10), "...") AS short_description, pi.url FROM products AS p
JOIN pictures AS pi ON p.picture_id = pi.id
WHERE year(pi.added_on) < 2019 AND length(p.description) > 100 AND p.price > 20
ORDER BY p.price DESC;

/*7.	Counts of products in stores and their average 
The managers needs to know in which stores sell different products and their average price.
Extract from the database all of the stores (with or without products) and the count of the products that they have. Also you can show the average price of all products (rounded to the second digit after decimal point) that sells in store.
Order the results descending by count of products in store, then by average price in descending order and finally by store id. 
Required Columns
•	Name (store)
•	product_count
•	avg
*/

SELECT s.name, COUNT(p.id) AS product_count, round(avg(p.price), 2) AS avg FROM  stores AS s
LEFT JOIN products_stores AS ps ON s.id = ps.store_id
LEFT JOIN products AS p ON ps.product_id = p.id
GROUP BY s.id
ORDER BY product_count desc, avg DESC, s.id;

/*8.	Specific employee

There are many employees in our shop system, but we need to find only the one that passes some specific criteria. 
Extract from the database, the full name of employee, name of store that he works, address of store, and salary. The employee's salary must be lower than 4000, the address of the store must contain '5' somewhere, the length of the store name needs to be more than 8 characters and the employee’s last name must end with an 'n'.
Required Columns
•	Full name (employee)
•	Store name 
•	Address
•	Salary
*/

SELECT concat_ws(" ", e.first_name, e.last_name) AS Full_name, s.name AS Store_name, a.name AS address, e.salary FROM employees AS e
JOIN stores AS s ON e.store_id = s.id
JOIN addresses AS a ON s.address_id = a.id
WHERE e.salary < 4000 AND a.name LIKE '%5%' 
	AND char_length(s.name) > 8 AND e.last_name LIKE '%n';

/*9.	Find all information of stores
The managers always want to know how the business goes. 
Now, they want from us to show all store names, but for security, the name must be in the reversed order.
Select the name of stores (in reverse order). 
After that, the full_address in format: {town name in upper case}-{address name}.
The next info is the count of employees, that work in the store.
Filter only the stores that have a one or more employees.
Order the results by the full_address in ascending order.

Required Columns
•	reversed_name (store name) 
•	full_address (full_address)
•	employees_count
*/

SELECT reverse(s.name) AS reversed_name,
		CONCAT(UPPER(t.name), '-', a.name) AS full_address,
		(SELECT COUNT(e.id) FROM employees as e 
        WHERE e.store_id = s.id) AS employees_count FROM stores AS s
JOIN addresses AS a ON s.address_id = a.id
JOIN towns AS t ON t.id = a.town_id
WHERE (SELECT COUNT(e.id) FROM employees as e 
        WHERE e.store_id = s.id) > 0
ORDER BY full_address;

/*10.	Find full name of top paid employee by store name
Create a user defined function with the name udf_top_paid_employee_by_store(store_name VARCHAR(50)) that receives a store name and returns the full name of top paid employee. 
Full info must be in format:
 	{first_name} {middle_name}. {last_name} works in store for {years of experience} years
The years of experience is the difference when they were hired and 2020-10-18
*/
DELIMITER $$
CREATE FUNCTION udf_top_paid_employee_by_store(store_name VARCHAR(50))
RETURNS VARCHAR(255)
BEGIN
RETURN (SELECT concat(e.first_name, " ", e.middle_name, ". ", e.last_name, " works in store for ", 2020 - YEAR(e.hire_date), " years") 
					AS full_info FROM employees AS e
JOIN stores AS s ON e.store_id = s.id
WHERE s.name = store_name
ORDER BY e.salary DESC
LIMIT 1);
END $$

DELIMITER ;

SET GLOBAL log_bin_trust_function_creators = 1;

SELECT udf_top_paid_employee_by_store('Stronghold') as 'full_info';
SELECT udf_top_paid_employee_by_store('Keylex') as 'full_info';

/*11.	Update product price by address
CREATE user define procedure udp_update_product_price (address_name VARCHAR (50)), that receives as parameter an address name.
Increase the product's price with 100 if the address starts with 0 (zero) otherwise increase the price with 200.
*/

DELIMITER $$
CREATE PROCEDURE udp_update_product_price (address_name VARCHAR (50))
BEGIN
	DECLARE increase_level INT;
    IF address_name LIKE '0%' THEN SET increase_level = 100;
    ELSE SET increase_level = 200;
    END IF;
UPDATE products AS p SET price = price + increase_level
WHERE p.id IN (SELECT ps.product_id FROM addresses AS a
				JOIN stores AS s ON a.id = s.address_id
                JOIN products_stores AS ps ON ps.store_id = s.id
                WHERE a.name = address_name);
END$$

DELIMITER ;

CALL udp_update_product_price('07 Armistice Parkway');
CALL udp_update_product_price('1 Cody Pass');

