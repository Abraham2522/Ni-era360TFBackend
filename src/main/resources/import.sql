INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_CLIENTE');
INSERT INTO roles (name) VALUES ('ROLE_CUIDADOR');
INSERT INTO users(username, password) VALUES ('user1','$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06');
INSERT INTO users(username, password) VALUES ('admin','$2a$12$fTFyzf47485ofBSUfq3AUOtIyz7ztN/n.lFHW6I17K41HJqR0lDr6');
INSERT INTO users(username, password) VALUES ('Abraham','$2a$12$IAr.9iROimyeeaKLPt86c.0wv6m9BnQ3A7chTKcpIBwUB1Uvwwr/u'); --1234
INSERT INTO users(username, password) VALUES ('Nana','$2a$12$0mSUBby1U8SsIKBKtzUpnOuXq9LpeWz3zDqUwLhKk.KbrPb7OE1wS');--abcd
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- user1 with ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- admin with ROLE_ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3); -- Abraham with ROLE_CLIENTE
INSERT INTO user_roles (user_id, role_id) VALUES (4, 4); -- Nana with ROLE_CUIDADOR

