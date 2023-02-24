INSERT INTO country (id, name, code, version) VALUES (RANDOM_UUID(), 'USA','US',0);
INSERT INTO country (id, name, code, version) VALUES (RANDOM_UUID(), 'France','FR',0);
INSERT INTO country (id, name, code, version) VALUES (RANDOM_UUID(), 'Brazil','BR',0);
INSERT INTO country (id, name, code, version) VALUES (RANDOM_UUID(), 'Italy','IT',0);
INSERT INTO country (id, name, code, version) VALUES (RANDOM_UUID(), 'Canada','CA',0);
INSERT INTO country (id, name, code, version) VALUES (RANDOM_UUID(), 'Greece','GR',0);
INSERT INTO country (id, name, code, version) VALUES (RANDOM_UUID(), 'Cyprus','CY',0);

insert into account ( id, account_number, account_type, current_balance, available_balance, opening_date, version )
values (random_uuid(), '357022123456', 'CUR', 100, 100, '2023-01-01', 0);
insert into account ( id, account_number, account_type, current_balance, available_balance, opening_date, version )
values (random_uuid(), '357022123457', 'CUR', 0, 0, current_date(), 0);
insert into account ( id, account_number, account_type, current_balance, available_balance, opening_date, version )
values (random_uuid(), '357022123458', 'CUR', 0, 200, '2021-06-01', 0);
insert into account ( id, account_number, account_type, current_balance, available_balance, opening_date, version )
values (random_uuid(), '357022123459', 'CUR', 300, 300, '2022-03-31', 0);
insert into account ( id, account_number, account_type, current_balance, available_balance, opening_date, version )
values (random_uuid(), '357022123450', 'CUR', 0, -50, '2022-12-01', 0);

insert into customer ( id, country_id, user_id, email, id_number, first_name, middle_name, last_name, birth_date, sex, mobile_number, segmentation, version)
values (RANDOM_UUID(), (select id from country where code = 'CY'), '80808080', null, 8989899, 'Christos', null, 'Nicolaou', '2000-10-10', 'MALE', '99999999', 'RETAIL', 0);
insert into customer ( id, country_id, user_id, email, id_number, first_name, middle_name, last_name, birth_date, sex, mobile_number, segmentation, version)
values (RANDOM_UUID(), (select id from country where code = 'CY'), '90909090', null, 123456, 'Andreas', null, 'Georgiou', '1071-11-01', 'MALE', '99123456', 'RETAIL', 0);
insert into customer ( id, country_id, user_id, email, id_number, first_name, middle_name, last_name, birth_date, sex, mobile_number, segmentation, version)
values (RANDOM_UUID(), (select id from country where code = 'GR'), '89808089', null, 1234567, 'Tzeni', null, 'Papadopoullos', '1981-01-01', 'FEMALE', '0030546123456', 'RETAIL', 0);
insert into customer ( id, country_id, user_id, email, id_number, first_name, middle_name, last_name, birth_date, sex, mobile_number, segmentation, version)
values (RANDOM_UUID(), (select id from country where code = 'US'), '80801111', null, 123450, 'Jenifer', null, 'Smith', '1991-06-10', 'FEMALE', '0015781231234', 'RETAIL', 0);
insert into customer ( id, country_id, user_id, email, id_number, first_name, middle_name, last_name, birth_date, sex, mobile_number, segmentation, version)
values (RANDOM_UUID(), (select id from country where code = 'BR'), '80809876', null, 123459, 'Carlos', null, 'Zoao Da-Silva', '1988-07-19', 'MALE', '0057890123456', 'RETAIL', 0);