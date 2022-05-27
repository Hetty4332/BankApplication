INSERT INTO bank(name)
VALUES ('Тинькофф'),
       ('Сбербанк'),
       ('ВТБ');

INSERT INTO client(email, name, passport_number, phone_number)
VALUES ('pushkin@mail.ru', 'Александр Сергеевич Пушкин', '1234567890', '555555'),
       ('mocart@google.com', 'Вольфганг Амадей Моцарт', '0987456123', '444444'),
       ('butusov@yandex.ru', 'Вячеслав Геннадьевич Бутусов', '123654789', '333333'),
       ('geralt@yandex.ru', 'Геральт из Ривии', '123654789', '888888');

INSERT INTO credit(id, credit_limit, interest_rate)
VALUES (1, 10000, 15),
       (2, 15000, 10),
       (3, 20000, 20);

INSERT INTO bank_clients(bank_id, clients_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO bank_credits(bank_id, credits_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO role(id, name)
VALUES (1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

INSERT INTO PERSON (email, password, status, login)
VALUES ('apiUser@test.ru', '$2a$12$bqI3z5vKArCmRHEmFrDPwu.LxNB1h5PUnlniahy03AAZK7kLUd5q6', 'ACTIVE', 'apiUser2');

INSERT INTO PERSON_ROLES
VALUES (1, 2);


