CREATE DATABASE user;

USE user;

CREATE TABLE user (
    user_id INT PRIMARY KEY,
    username VARCHAR(20),
    password VARCHAR(20),
    user_type INT CHECK (user_type IN (1, 2))
);

USE user;

INSERT INTO user (user_id, username, password, user_type) VALUES
(1, 'user1', 'password1', 1),
(2, 'user2', 'password2', 1),
(3, 'user3', 'password3', 1),
(4, 'user4', 'password4', 1),
(5, 'user5', 'password5', 1),
(6, 'user6', 'password6', 1),
(7, 'user7', 'password7', 1),
(8, 'user8', 'password8', 1);
INSERT INTO user (user_id, username, password, user_type) VALUES
(9, 'user9', 'password9', 2),
(10, 'user10', 'password10', 2);