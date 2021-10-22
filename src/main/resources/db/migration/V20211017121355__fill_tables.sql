INSERT INTO role(role_name)
VALUES ('admin');
INSERT INTO role(role_name)
VALUES ('user');
INSERT INTO role(role_name)
VALUES ('cook');

INSERT INTO user(last_name, first_name, login, password, address)
VALUES ('Коляго', 'Владислав', 'kaliaha.vladzislav', '1111', 'г.Витебск');
INSERT INTO user(last_name, first_name, login, password, address)
VALUES ('Молочко', 'Юрий', 'molochko.urey', '2222', 'г.Хойники');
INSERT INTO user(last_name, first_name, login, password, address)
VALUES ('Рубанов', 'Владислав', 'rubanov', '3333', 'г.Жлобин');
INSERT INTO user(last_name, first_name, login, password, address)
VALUES ('Петров', 'Сергей', 'petrov', '4444', 'г.Москва');

INSERT INTO user_role_link(user_id, role_id)
VALUES (1, 1);
INSERT INTO user_role_link(user_id, role_id)
VALUES (1, 2);
INSERT INTO user_role_link(user_id, role_id)
VALUES (2, 2);
INSERT INTO user_role_link(user_id, role_id)
VALUES (3, 2);
INSERT INTO user_role_link(user_id, role_id)
VALUES (4, 3);

INSERT INTO `order` (`price`, `date`, `address`, `order_status`, `user_id`) VALUES ('1500', '2021-10-21 00:00:00', 'г. Минск', 'Cooking', '1');
INSERT INTO `order` (`price`, `date`, `address`, `order_status`, `user_id`) VALUES ('2800', '2021-10-22 00:00:00', 'г. Минск', 'Cooking', '2');
INSERT INTO `order` (`price`, `date`, `address`, `order_status`, `user_id`) VALUES ('1200', '2021-10-23 00:00:00', 'г. Витебск', 'Cooking', '1');
INSERT INTO `order` (`price`, `date`, `address`, `order_status`, `user_id`) VALUES ('1500', '2021-11-24 00:00:00', 'г. Минск', 'New', '3');
INSERT INTO `order` (`price`, `date`, `address`, `order_status`, `user_id`) VALUES ('2800', '2021-11-25 00:00:00', 'г. Минск', 'New', '4');
INSERT INTO `order` (`price`, `date`, `address`, `order_status`, `user_id`) VALUES ('1200', '2021-12-26 00:00:00', 'г. Витебск', 'New', '1');

INSERT INTO dish (dish_name, price, `group`, description, image_path) VALUES ('Картошка с грибами', '2', 'Горячее', 'Очень вкусно', 'photo.img');
INSERT INTO dish (dish_name, price, `group`, description, image_path) VALUES ('Салат по-французски', '7', 'Салаты', 'Невкусно', 'photo1.img');
INSERT INTO dish (dish_name, price, `group`, description, image_path) VALUES ('Макароны по-европейски', '11', 'Напитки', 'Невероятно', 'photo2.img');

INSERT INTO order_dish_link(order_id, dish_id) VALUES (1, 1);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (1, 2);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (1, 3);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (2, 1);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (2, 2);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (3, 2);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (3, 3);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (4, 1);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (4, 2);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (5, 1);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (6, 2);
INSERT INTO order_dish_link(order_id, dish_id) VALUES (6, 3);


INSERT INTO ingredient(ingredient_name, price, remainder, measurement)
VALUES ('Мясо', 800, 1500, 'Килограмм');
INSERT INTO ingredient(ingredient_name, price, remainder, measurement)
VALUES ('Картошка', 300, 777, 'Килограмм');
INSERT INTO ingredient(ingredient_name, price, remainder, measurement)
VALUES ('Рис', 350, 1111, 'Килограмм');
INSERT INTO ingredient(ingredient_name, price, remainder, measurement)
VALUES ('Чеснок', 15, 500, 'Грамм');
INSERT INTO ingredient(ingredient_name, price, remainder, measurement)
VALUES ('Помидор', 13, 500, 'Грамм');

INSERT INTO composition (dish_id, ingredient_id, quantity) VALUES ('1', '1', '100');
INSERT INTO composition (dish_id, ingredient_id, quantity) VALUES ('1', '2', '450');
INSERT INTO composition (dish_id, ingredient_id, quantity) VALUES ('1', '3', '43');
INSERT INTO composition (dish_id, ingredient_id, quantity) VALUES ('2', '1', '132');
INSERT INTO composition (dish_id, ingredient_id, quantity) VALUES ('2', '2', '12');
INSERT INTO composition (dish_id, ingredient_id, quantity) VALUES ('3', '4', '100');
INSERT INTO composition (dish_id, ingredient_id, quantity) VALUES ('3', '1', '450');
INSERT INTO composition (dish_id, ingredient_id, quantity) VALUES ('3', '5', '43');
INSERT INTO composition (dish_id, ingredient_id, quantity) VALUES ('3', '2', '132');

COMMIT;
