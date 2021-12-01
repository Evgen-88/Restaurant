INSERT INTO user_role(role_name)
VALUES ('admin'),
       ('user');

INSERT INTO users(last_name, first_name, login, password, address)
VALUES ('Коляго', 'Владислав', 'kaliaha.vladzislav', '1111', 'г.Витебск'),
       ('Молочко', 'Юрий', 'molochko.urey', '2222', 'г.Хойники'),
       ('Рубанов', 'Владислав', 'rubanov', '3333', 'г.Жлобин'),
       ('Петров', 'Сергей', 'petrov', '4444', 'г.Москва');

INSERT INTO user_role_link(user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 2);

INSERT INTO user_order (price, order_date, address, order_status, user_id)
VALUES ('1500', '2021-10-21 00:00:00', 'г. Минск', 'COOKING', '1'),
       ('2800', '2021-10-22 00:00:00', 'г. Минск', 'COOKING', '2'),
       ('1200', '2021-10-23 00:00:00', 'г. Витебск', 'COOKING', '1'),
       ('1500', '2021-11-24 00:00:00', 'г. Минск', 'NEW', '3'),
       ('2800', '2021-11-25 00:00:00', 'г. Минск', 'NEW', '4'),
       ('1200', '2021-12-26 00:00:00', 'г. Витебск', 'NEW', '1');

INSERT INTO dish (dish_name, price, dish_group, dish_description, image_path)
VALUES ('Картошка с грибами', '2', 'HOT', 'Очень вкусно', 'photo.img'),
       ('Салат по-французски', '7', 'SALAD', 'Невкусно', 'photo1.img'),
       ('Макароны по-европейски', '11', 'DRINK', 'Невероятно', 'photo2.img');

INSERT INTO order_dish_link(order_id, dish_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (5, 1),
       (6, 2),
       (6, 3);

INSERT INTO ingredient(ingredient_name, price, remainder, measurement)
VALUES ('Мясо', 800, 1500, 'KILOGRAM'),
       ('Картошка', 300, 777, 'KILOGRAM'),
       ('Рис', 350, 1111, 'KILOGRAM'),
       ('Чеснок', 15, 500, 'GRAM'),
       ('Помидор', 13, 500, 'GRAM');

INSERT INTO composition (dish_id, ingredient_id, quantity)
VALUES ('1', '1', '100'),
       ('1', '2', '450'),
       ('1', '3', '43'),
       ('2', '1', '132'),
       ('2', '2', '12'),
       ('3', '4', '100'),
       ('3', '1', '450'),
       ('3', '5', '43'),
       ('3', '2', '132');
COMMIT;