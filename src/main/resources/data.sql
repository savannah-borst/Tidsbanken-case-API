INSERT INTO employee (employee_id, first_name, last_name, email_address, is_admin, profile_pic) VALUES ('e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4','Savannah', 'Borst', 'testadmin@tidsbanken.com', TRUE, 'smile');
INSERT INTO employee (employee_id, first_name, last_name, email_address, is_admin, profile_pic) VALUES ('ba953f00-a505-4504-9449-b17480e04d8b', 'Dianto', 'Bosman', 'testuser@tidsbanken.com', FALSE, 'neutral');
INSERT INTO employee (employee_id, first_name, last_name, email_address, is_admin, profile_pic) VALUES ('il-1', 'Iljaas', 'Dhonre', 'Iljaas@gmail.com', FALSE, 'smile');

INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, owner_id) VALUES ('request1', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4');
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, owner_id) VALUES ('request2', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4');
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, owner_id) VALUES ('request3', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4');
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, owner_id, moderator_id ) VALUES ('request4', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 'ba953f00-a505-4504-9449-b17480e04d8b', 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4');
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, owner_id) VALUES ('request5', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 'ba953f00-a505-4504-9449-b17480e04d8b');
INSERT INTO vacation_request (title, date_created, date_updated, period_end, period_start, status, owner_id, moderator_id ) VALUES ('request6', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'PENDING', 'il-1', 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4');

INSERT INTO comment (date_created, date_updated, message) VALUES ('2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'vacation request for savannah');
INSERT INTO comment (date_created, date_updated, message) VALUES ('2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'vacation request for dianto');
INSERT INTO comment (date_created, date_updated, message) VALUES ('2022-04-11T09:23:46.188Z', '2022-04-11T09:23:46.188Z', 'vacation request for iljaas');

INSERT INTO ineligible_period (period_start, period_end, creator_id) VALUES ('2022-09-11T09:23:46.188Z', '2022-08-21T09:23:46.188Z', 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4');
INSERT INTO ineligible_period (period_start, period_end, creator_id) VALUES ('2022-08-11T09:23:46.188Z', '2022-09-26T09:23:46.188Z', 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4');
INSERT INTO ineligible_period (period_start, period_end, creator_id) VALUES ('2022-10-11T09:23:46.188Z', '2022-10-15T09:23:46.188Z', 'e8307ff3-2dd5-4fbc-b0a3-44ddbb956cc4');

