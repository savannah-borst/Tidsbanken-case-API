INSERT INTO employee (first_name, last_name, email_address, is_admin, profile_pic) VALUES ('Savannah', 'Borst', 'savannah@gmail.com', TRUE, 'smile');
INSERT INTO employee (first_name, last_name, email_address, is_admin, profile_pic) VALUES ('Dianto', 'Bosman', 'Dianto@gmail.com', FALSE, 'neutral');
INSERT INTO employee (first_name, last_name, email_address, is_admin, profile_pic) VALUES ( 'Iljaas', 'Dhonre', 'Iljaas@gmail.com', FALSE, 'smile');

INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, moderator_id) VALUES ('request2', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 1);
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, moderator_id) VALUES ('request1', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 1);
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, moderator_id) VALUES ('request3', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 1);
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, moderator_id) VALUES ('request4', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 1);
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, moderator_id) VALUES ('request5', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 1);
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, moderator_id) VALUES ('request6', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 1);

INSERT INTO employee_vacationrequest SELECT 1, 1;
INSERT INTO employee_vacationrequest SELECT 1, 2;
INSERT INTO employee_vacationrequest SELECT 1, 3;
INSERT INTO employee_vacationrequest SELECT 2, 4;
INSERT INTO employee_vacationrequest SELECT 2, 5;
INSERT INTO employee_vacationrequest SELECT 3, 6;

INSERT INTO comment (date_created, date_updated, message) VALUES ('2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'vacation request for savannah');
INSERT INTO comment (date_created, date_updated, message) VALUES ('2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'vacation request for dianto');
INSERT INTO comment (date_created, date_updated, message) VALUES ('2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'vacation request for iljaas');

INSERT INTO vacationrequest_comment SELECT 1, 1;
INSERT INTO vacationrequest_comment SELECT 4, 2;
INSERT INTO vacationrequest_comment SELECT 5, 3;