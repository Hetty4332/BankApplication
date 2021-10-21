INSERT INTO bank(id, name)
VALUES (1, 'Тинькофф'),(2, 'Сбербанк'), (3, 'ВТБ');

    INSERT INTO client(
    id, email, name, passport_number, phone_number)
VALUES (1, 'pushkin@mail.ru', 'Александр Сергеевич Пушкин', 1234567890, 555555),
    (2, 'mocart@google.com', 'Вольфганг Амадей Моцарт',0987456123, 444444),
    (3, 'butusov@yandex.ru', 'Вячеслав Геннадьевич Бутусов',123654789, 333333);

INSERT INTO credit(
    id, credit_limit, interest_rate)
VALUES (1, 10000, 15),
    (2, 15000, 10),
    (3, 20000, 20);

INSERT INTO bank_clients(
    bank_id, clients_id)
VALUES (1, 1),
    (2,2),
    (3,3);

INSERT INTO bank_credits(
    bank_id, credits_id)
VALUES (1, 1),
    (2,2),
    (3,3);

INSERT INTO role(
    id, name)
VALUES (1, 'USER');

INSERT INTO PERSON VALUES
(1,'user@test.ru','$2a$12$bqI3z5vKArCmRHEmFrDPwu.LxNB1h5PUnlniahy03AAZK7kLUd5q6','ACTIVE','user')

INSERT INTO PERSON_ROLES VALUES (1,1)