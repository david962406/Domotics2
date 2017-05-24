INSERT INTO users  (id, password, admin, user_name, last_configuration) VALUES (0, 'asd123', 1, 'admin', NULL);
INSERT INTO users  (id, password, admin, user_name, last_configuration) VALUES (1, 'asd234', 0, 'david', NULL);

INSERT INTO user_data  (user_id, first_name, last_name) VALUES (0, 'N/A', 'N/A');
INSERT INTO user_data  (user_id, first_name, last_name) VALUES (1, 'N/A', 'N/A');

INSERT INTO device (id, description) VALUES (0, 'Iluminación exterior');
INSERT INTO device (id, description) VALUES (1, 'Iluminación de la piscina');
INSERT INTO device (id, description) VALUES (2, 'Ventilador cuarto 1');
INSERT INTO device (id, description) VALUES (3, 'Ventilador cuarto 2');
INSERT INTO device (id, description) VALUES (4, 'Sensor de temperatura 1');
INSERT INTO device (id, description) VALUES (5, 'Sensor de temperatura 2');
INSERT INTO device (id, description) VALUES (6, 'RGB cuarto 1');
INSERT INTO device (id, description) VALUES (7, 'RGB cuarto 2');
INSERT INTO device (id, description) VALUES (8, 'Sensor de iluminación 1');
INSERT INTO device (id, description) VALUES (9, 'Sensor de movimiento');
INSERT INTO device (id, description) VALUES (10, 'Puerta 1');
INSERT INTO device (id, description) VALUES (11, 'Puerta 2');
INSERT INTO device (id, description) VALUES (12, 'Ventana 1');
INSERT INTO device (id, description) VALUES (13, 'Ventana 2');
INSERT INTO device (id, description) VALUES (14, 'Ventana 3');
INSERT INTO device (id, description) VALUES (15, 'Alarma');


INSERT INTO configuration (id, user_id, description) VALUES (0, 0, 'Por defecto');

INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (0, 1, 255, 1, 0);
INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (1, 1, 120, 1, 0);
INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (2, 1, NULL, 1, 0);
INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (3, 0, NULL, 0, 0);
INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (5, 0, 200-100-200, 1, 0);
INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (6, 0, 255-100-200, 1, 0);
INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (9, 1, NULL, 0, 0);
INSERT INTO device_configuration (device_id, sensor_active, data, device_active, id_configuration) VALUES (10, 0, NULL, 0, 0);
