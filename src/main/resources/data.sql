INSERT INTO employee (employee_id, first_name, last_name, email_address, is_admin, profile_pic) VALUES ('e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4','Savannah', 'Borst', 'testadmin@tidsbanken.com', TRUE, 'smile');
INSERT INTO employee (employee_id, first_name, last_name, email_address, is_admin, profile_pic) VALUES ('ba953f00-a505-4504-9449-b17480e04d8b', 'Dianto', 'Bosman', 'testuser@tidsbanken.com', FALSE, 'neutral');
INSERT INTO employee (employee_id, first_name, last_name, email_address, is_admin, profile_pic) VALUES ('il-1', 'Iljaas', 'Dhonre', 'Iljaas@gmail.com', FALSE, 'smile');

INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start) VALUES ('request1', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z');
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start) VALUES ('request2', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z');
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start) VALUES ('request3', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z');
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start) VALUES ('request4', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z');
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start) VALUES ('request5', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z');
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start) VALUES ('request6', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z');

INSERT INTO employee_vacationrequest SELECT 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4', 1;
INSERT INTO employee_vacationrequest SELECT 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4', 2;
INSERT INTO employee_vacationrequest SELECT 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4', 3;
INSERT INTO employee_vacationrequest SELECT 'ba953f00-a505-4504-9449-b17480e04d8b', 4;
INSERT INTO employee_vacationrequest SELECT 'il-1', 5;
INSERT INTO employee_vacationrequest SELECT 'ba953f00-a505-4504-9449-b17480e04d8b', 6;

INSERT INTO comment (date_created, date_updated, message) VALUES ('2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'vacation request for savannah');
INSERT INTO comment (date_created, date_updated, message) VALUES ('2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'vacation request for dianto');
INSERT INTO comment (date_created, date_updated, message) VALUES ('2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'vacation request for iljaas');

INSERT INTO vacationrequest_comment SELECT 1, 1;
INSERT INTO vacationrequest_comment SELECT 4, 2;
INSERT INTO vacationrequest_comment SELECT 5, 3;
