-- insert into performance (id, title, performer, price,  created_at, updated_at) values (1, 'test', 'test', 10000, now(), now());
-- insert into performance_detail (id, performance_id, amount_seats, remain_seats, start_date, created_at, updated_at) values (1, 1, 50, 50, '2024-05-31', now(), now());

INSERT INTO PERFORMANCE (TITLE, PERFORMER, PRICE, CREATED_AT, UPDATED_AT) VALUES ('TEST', 'TEST', 10000, NOW(), NOW());
INSERT INTO PERFORMANCE_DETAIL (PERFORMANCE_ID, AMOUNT_SEATS, REMAIN_SEATS, START_DATE, CREATED_AT, UPDATED_AT) VALUES (1, 50, 50, '2024-05-31', NOW(), NOW());
INSERT INTO WALLET (USER_ID, AMOUNT, CREATED_AT, UPDATED_AT) VALUES (1, 5000, NOW(), NOW());
INSERT INTO POINT_HISTORY (USER_ID, AMOUNT, ACTIVITY, CREATED_AT, UPDATED_AT) VALUES (1, 5000, 'CHARGE', NOW(), NOW());
