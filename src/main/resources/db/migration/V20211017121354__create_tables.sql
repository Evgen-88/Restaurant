CREATE TABLE IF NOT EXISTS `dish`
(
    `id`          BIGINT                                NOT NULL AUTO_INCREMENT,
    `dish_name`   VARCHAR(256)                          NOT NULL,
    `price`       INT                                   NULL,
    `group`       ENUM ('Горячее', 'Салаты', 'Напитки') NOT NULL,
    `description` VARCHAR(5000)                         NULL,
    `image_path`  VARCHAR(512)                          NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT dish_unique UNIQUE (`dish_name`)
);

CREATE TABLE IF NOT EXISTS `ingredient`
(
    `id`              BIGINT                                                               NOT NULL AUTO_INCREMENT,
    `ingredient_name` VARCHAR(256)                                                         NOT NULL,
    `price`           INT                                                                  NOT NULL,
    `remainder`       INT                                                                  NOT NULL,
    `measurement`     ENUM ('Килограмм', 'Грамм', 'Штука', 'Бутылка', 'Литр', 'Миллилитр') NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT ingredient_unique UNIQUE (`ingredient_name`)
);

CREATE TABLE IF NOT EXISTS `composition`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT,
    `dish_id`       BIGINT NOT NULL,
    `ingredient_id` BIGINT NOT NULL,
    `quantity`      INT    NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT dish_ingredient_unique UNIQUE (`dish_id`, `ingredient_id`),
    CONSTRAINT composition_ingredient_fk
        FOREIGN KEY (`ingredient_id`)
            REFERENCES `ingredient` (`id`),
    CONSTRAINT composition_dish_fk
        FOREIGN KEY (`dish_id`)
            REFERENCES `dish` (`id`)
);

CREATE TABLE IF NOT EXISTS `user`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `last_name`  VARCHAR(64)  NOT NULL,
    `first_name` VARCHAR(64)  NOT NULL,
    `login`      VARCHAR(32)  NOT NULL,
    `password`   VARCHAR(256) NOT NULL,
    `address`    VARCHAR(256) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT login_unique UNIQUE (`login`)
);

CREATE TABLE IF NOT EXISTS `order`
(
    `id`           BIGINT                                           NOT NULL AUTO_INCREMENT,
    `price`        INT                                              NOT NULL,
    `date`         DATETIME                                         NOT NULL,
    `address`      VARCHAR(256)                                     NOT NULL,
    `order_status` ENUM ('New', 'Accepted', 'Cooking', 'Completed') NOT NULL,
    `user_id`      BIGINT                                           NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT order_user_fk
        FOREIGN KEY (`user_id`)
            REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `order_dish_link`
(
    `order_id` BIGINT NOT NULL,
    `dish_id`  BIGINT NOT NULL,
    CONSTRAINT order_dish_link_dish_fk
        FOREIGN KEY (`dish_id`)
            REFERENCES `dish` (`id`),
    CONSTRAINT order_dish_link_order_fk
        FOREIGN KEY (`order_id`)
            REFERENCES `order` (`id`)
);

CREATE TABLE IF NOT EXISTS `role`
(
    `id`        BIGINT      NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(16) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT role_unique UNIQUE (`role_name`)
);

CREATE TABLE IF NOT EXISTS `user_role_link`
(
    `user_id` BIGINT NOT NULL,
    `role_id` BIGINT NOT NULL,
    CONSTRAINT user_role_unique UNIQUE (`user_id`, `role_id`),
    CONSTRAINT user_role_link_user_fk
        FOREIGN KEY (`user_id`)
            REFERENCES `user` (`id`),
    CONSTRAINT user_role_link_role_fk
        FOREIGN KEY (`role_id`)
            REFERENCES `role` (`id`)
);
COMMIT;