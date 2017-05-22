INSERT INTO users  (id, password, admin, user_name, last_configuration) VALUES (0, 'asd123', 1, 'admin', NULL);
INSERT INTO users  (id, password, admin, user_name, last_configuration) VALUES (1, 'asd234', 0, 'david', NULL);

INSERT INTO user_data  (user_id, first_name, last_name) VALUES (0, 'N/A', 'N/A');
INSERT INTO user_data  (user_id, first_name, last_name) VALUES (1, 'N/A', 'N/A');

INSERT INTO device (id, description) VALUES (0, 'Sensor de temperatura LM35');

INSERT INTO device_configuration (device_id, sensor_active, data, device_active) VALUES (0, NULL, -1, 1);