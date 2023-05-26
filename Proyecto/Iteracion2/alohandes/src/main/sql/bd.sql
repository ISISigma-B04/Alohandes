INSERT INTO operator (nuip, name, type)
VALUES (123456789, 'Residencias Universitarias', 'Residencia estudiantil');
INSERT INTO operator (nuip, name, type)
VALUES (234567890, 'Residencias Doña Juana', 'Residencia no-estudiantil');
INSERT INTO operator (nuip, name, type)
VALUES (345678901, 'Hostal El Viajero', 'Hostal');
INSERT INTO operator (nuip, name, type)
VALUES (456789012, 'Hotel Hilton', 'Habitacion de Hotel');
INSERT INTO operator (nuip, name, type)
VALUES (567890123, 'Apartamentos Ciudad Jardín', 'Apartamento');
INSERT INTO operator (nuip, name, type)
VALUES (678901234, 'Casa de huéspedes San Francisco', 'Habitacion de casa');
INSERT INTO operator (nuip, name, type)
VALUES (789012345, 'Residencias del Rosario', 'Residencia estudiantil');
INSERT INTO operator (nuip, name, type)
VALUES (890123456, 'Apartamentos La Gran Colombia', 'Apartamento');
INSERT INTO operator (nuip, name, type)
VALUES (901234567, 'Hotel Estelar', 'Habitacion de Hotel');
INSERT INTO operator (nuip, name, type)
VALUES (123456780, 'Casa del Árbol', 'Habitacion de casa');
INSERT INTO operator (nuip, name, type)
VALUES (234567891, 'Residencias de los Andes', 'Residencia estudiantil');
INSERT INTO operator (nuip, name, type)
VALUES (345678902, 'Hostal Las Margaritas', 'Hostal');
INSERT INTO operator (nuip, name, type)
VALUES (456789013, 'Apartamentos La Pradera', 'Apartamento');
INSERT INTO operator (nuip, name, type)
VALUES (567890124, 'Hotel Marriot', 'Habitacion de Hotel');
INSERT INTO operator (nuip, name, type)
VALUES (678901235, 'Casa de huéspedes El Descanso', 'Habitacion de casa');
INSERT INTO operator (nuip, name, type)
VALUES (789012346, 'Residencias San José', 'Residencia estudiantil');
INSERT INTO operator (nuip, name, type)
VALUES (890123457, 'Apartamentos La Sabana', 'Apartamento');
INSERT INTO operator (nuip, name, type)
VALUES (901234568, 'Hotel Bogotá Plaza', 'Habitacion de Hotel');
INSERT INTO operator (nuip, name, type)
VALUES (123456781, 'Casa de huéspedes Santa Elena', 'Habitacion de casa');
INSERT INTO operator (nuip, name, type)
VALUES (234567892, 'Residencias de los Pinos', 'Residencia estudiantil');




INSERT INTO client (nuip, name, client_type)
VALUES (11111111, 'John Doe', 'Estudiante');
INSERT INTO client (nuip, name, client_type)
VALUES (22222222, 'Jane Smith', 'Empleado');
INSERT INTO client (nuip, name, client_type)
VALUES (33333333, 'Samantha Lee', 'Estudiante Graduado');
INSERT INTO client (nuip, name, client_type)
VALUES (44444444, 'David Johnson', 'Familiar de estudiante');
INSERT INTO client (nuip, name, client_type)
VALUES (55555555, 'Alex Rodriguez', 'Empleado');
INSERT INTO client (nuip, name, client_type)
VALUES (66666666, 'Emily Davis', 'Profesor');
INSERT INTO client (nuip, name, client_type)
VALUES (77777777, 'Avery Kim', 'Estudiante Graduado');
INSERT INTO client (nuip, name, client_type)
VALUES (88888888, 'Michael Brown', 'Invitado a evento');
INSERT INTO client (nuip, name, client_type)
VALUES (99999999, 'Jessica Taylor', 'Profesor invitado');
INSERT INTO client (nuip, name, client_type)
VALUES (10101010, 'Robert Lee', 'Estudiante');
INSERT INTO client (nuip, name, client_type)
VALUES (11111112, 'Mary Johnson', 'Empleado');
INSERT INTO client (nuip, name, client_type)
VALUES (13131313, 'Jacob Martin', 'Estudiante Graduado');
INSERT INTO client (nuip, name, client_type)
VALUES (14141414, 'Sophia Davis', 'Familiar de estudiante');
INSERT INTO client (nuip, name, client_type)
VALUES (15151515, 'Isabella Anderson', 'Empleado');
INSERT INTO client (nuip, name, client_type)
VALUES (16161616, 'William Hernandez', 'Profesor');
INSERT INTO client (nuip, name, client_type)
VALUES (17171717, 'Chloe Kim', 'Estudiante Graduado');
INSERT INTO client (nuip, name, client_type)
VALUES (18181818, 'Ethan Wilson', 'Invitado a evento');
INSERT INTO client (nuip, name, client_type)
VALUES (19191919, 'Amelia Garcia', 'Profesor invitado');
INSERT INTO client (nuip, name, client_type)
VALUES (20202020, 'Olivia Rodriguez', 'Estudiante');
INSERT INTO client (nuip, name, client_type)
VALUES (21212121, 'Noah Davis', 'Empleado');




INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (11111111, 1, 0, 1, 0);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (22222222, 0, 1, 1, 0);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (33333333, 1, 1, 0, 1);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (44444444, 0, 0, 1, 1);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (55555555, 1, 1, 1, 0);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (66666666, 0, 1, 0, 1);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (77777777, 1, 0, 1, 1);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (88888888, 0, 1, 1, 1);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (99999999, 1, 1, 0, 0);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (10101010, 0, 0, 1, 0);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (11111112, 1, 1, 1, 1);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (12121212, 0, 1, 0, 0);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (13131313, 1, 0, 0, 1);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (14141414, 0, 0, 0, 0);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (15151515, 1, 1, 1, 1);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (16161616, 0, 1, 0, 1);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (17171717, 1, 0, 1, 0);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (18181818, 0, 1, 1, 0);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (19191919, 1, 0, 0, 1);
INSERT INTO client_preference (nuip, furnished, shared, wifi, kitchenette)
VALUES (20202020, 0, 0, 1, 1);




INSERT INTO apartment_spec (name, included_services, included_tv, administration_fee)
VALUES ('Apartamentos Ciudad Jardín', 1, 1, 250);
INSERT INTO apartment_spec (name, included_services, included_tv, administration_fee)
VALUES ('Apartamentos La Gran Colombia', 1, 0, 100);
INSERT INTO apartment_spec (name, included_services, included_tv, administration_fee)
VALUES ('Apartamentos La Pradera', 0, 1, 200);
INSERT INTO apartment_spec (name, included_services, included_tv, administration_fee)
VALUES ('Apartamentos La Sabana', 1, 0, 150);




INSERT INTO hostel_spec (name, nit, opening_hours, closing_hours)
VALUES ('Hostal El Viajero', 345678901, TO_TIMESTAMP('08:00', 'HH24:MI'), TO_TIMESTAMP('22:00', 'HH24:MI'));
INSERT INTO hostel_spec (name, nit, opening_hours, closing_hours)
VALUES ('Hostal Las Margaritas', 345678902, TO_TIMESTAMP('06:00', 'HH24:MI'), TO_TIMESTAMP('20:00', 'HH24:MI'));




INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (1234567890, 1, 1, 0, 1, 0);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (2345678901, 0, 1, 1, 0, 1);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (3456789012, 1, 1, 1, 0, 0);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (4567890123, 0, 0, 1, 1, 1);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (5678901234, 1, 1, 0, 0, 1);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (6789012345, 0, 1, 1, 1, 0);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (7890123456, 1, 0, 1, 1, 0);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (8901234567, 0, 0, 0, 1, 1);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (9012345678, 1, 1, 0, 1, 1);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (1134567890, 0, 1, 1, 1, 0);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (2245678901, 1, 0, 1, 0, 1);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (3356789012, 0, 0, 1, 1, 1);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (4467890123, 1, 1, 0, 0, 1);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (5578901234, 0, 1, 1, 1, 0);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (6689012345, 1, 0, 0, 1, 1);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (7790123456, 0, 1, 1, 0, 1);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (8801234567, 1, 1, 0, 1, 0);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (9912345678, 0, 1, 1, 1, 0);
INSERT INTO hotel (nit, restaurant, pool, parking_lot, wifi, cable_tv) 
VALUES (1034567890, 1, 0, 1, 1, 1);




INSERT INTO hotel_room_spec (name, room_number, room_type, bathtub, jacuzzi, living_room) 
VALUES ('Hotel Hilton', 101, 'Estandar', 0, 0, 0);
INSERT INTO hotel_room_spec (name, room_number, room_type, bathtub, jacuzzi, living_room) 
VALUES ('Hotel Estelar', 501, 'Estandar', 0, 0, 0);
INSERT INTO hotel_room_spec (name, room_number, room_type, bathtub, jacuzzi, living_room) 
VALUES ('Hotel Marriot', 301, 'Estandar', 0, 0, 0);
INSERT INTO hotel_room_spec (name, room_number, room_type, bathtub, jacuzzi, living_room) 
VALUES ('Hotel Bogotá Plaza', 701, 'Estandar', 0, 0, 0);




INSERT INTO house_room_spec (name, food, private_bathroom) 
VALUES ('Casa de huéspedes San Francisco', 1, 1);
INSERT INTO house_room_spec (name, food, private_bathroom) 
VALUES ('Casa del Árbol', 0, 1);
INSERT INTO house_room_spec (name, food, private_bathroom) 
VALUES ('Casa de huéspedes El Descanso', 1, 0);
INSERT INTO house_room_spec (name, food, private_bathroom) 
VALUES ('Casa de huéspedes Santa Elena', 0, 0);




INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Residencias Universitarias', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Residencias Doña Juana', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Hostal El Viajero', 0, 1, 0);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Hotel Hilton', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Apartamentos Ciudad Jardín', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Casa de huéspedes San Francisco', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Residencias del Rosario', 1, 1, 0);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Apartamentos La Gran Colombia', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Hotel Estelar', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Casa del Árbol', 1, 1, 0);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Residencias de los Andes', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Hostal Las Margaritas', 0, 1, 0);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Apartamentos La Pradera', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Hotel Marriot', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Casa de huéspedes El Descanso', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Residencias San José', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Apartamentos La Sabana', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Hotel Bogotá Plaza', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Casa de huéspedes Santa Elena', 1, 1, 1);
INSERT INTO operator_service (name, furnished, wifi, kitchenette) 
VALUES ('Residencias de los Pinos', 1, 1, 0);




INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Residencias Universitarias', 3, 4, 'Cra. 3 #14-49', 1);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Residencias Doña Juana', 1, 2, 'Cra. 5 #12-35', 0);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Hostal El Viajero', 4, 5, 'Cra. 4 #10-26', 1);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Hotel Hilton', 6, 6, 'Cra. 7 #14-46', 0);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Apartamentos Ciudad Jardín', 2, 3, 'Cra. 2 #12-15', 1);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Casa de huéspedes San Francisco', 0, 2, 'Cra. 1 #13-20', 0);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Residencias del Rosario', 5, 6, 'Cra. 6 #11-17', 1);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Apartamentos La Gran Colombia', 3, 4, 'Cra. 5 #10-25', 0);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Hotel Estelar', 6, 6, 'Cra. 8 #13-35', 1);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Casa del Árbol', 2, 3, 'Cra. 3 #11-18', 0);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Residencias de los Andes', 1, 2, 'Cra. 2 #14-26', 1);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Hostal Las Margaritas', 3, 4, 'Cra. 4 #13-28', 0);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Apartamentos La Pradera', 0, 2, 'Cra. 1 #10-30', 1);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Hotel Marriot', 5, 5, 'Cra. 9 #14-29', 0);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Casa de huéspedes El Descanso', 1, 1, 'Cra. 3 #10-20', 1);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Residencias San José', 2, 3, 'Cra. 6 #12-18', 0);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Apartamentos La Sabana', 4, 5, 'Calle 14 # 5-23', 1);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Hotel Bogotá Plaza', 3, 4, 'Calle 14 # 5-23', 0);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Casa de huéspedes Santa Elena', 2, 3, 'Carrera 5 # 10-12', 1);
INSERT INTO operator_spec (name, capacity, "size", location, shared) 
VALUES ('Residencias de los Pinos', 1, 2, 'Calle 12 # 6-8', 0);




INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (1, 11111111, 'Residencias Universitarias', 1500, DATE'2023-05-01', DATE'2023-05-05');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (2, 22222222, 'Residencias Doña Juana', 800, DATE'2023-05-02', DATE'2023-05-03');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (3, 33333333, 'Hostal El Viajero', 1000, DATE'2023-05-06', DATE'2023-05-08');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (4, 44444444, 'Hotel Hilton', 2500, DATE'2023-05-10', DATE'2023-05-12');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (5, 55555555, 'Apartamentos Ciudad Jardín', 2000, DATE'2023-05-13', DATE'2023-05-16');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (6, 66666666, 'Casa de huéspedes San Francisco', 1200, DATE'2023-05-17', DATE'2023-05-20');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (7, 77777777, 'Residencias del Rosario', 900, DATE'2023-05-22', DATE'2023-05-24');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (8, 88888888, 'Apartamentos La Gran Colombia', 1500, DATE'2023-05-25', DATE'2023-05-28');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (9, 99999999, 'Hotel Estelar', 2800, DATE'2023-05-30', DATE'2023-06-03');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (10, 10101010, 'Casa del Árbol', 500, DATE'2023-06-05', DATE'2023-06-06');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (11, 11111112, 'Residencias de los Andes', 1100, DATE'2023-06-07', DATE'2023-06-08');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (12, 13131313, 'Hostal Las Margaritas', 600, DATE'2023-06-10', DATE'2023-06-12');
INSERT INTO booking (id, nuip, name, cost, "start", "end")
VALUES (13, 14141414, 'Apartamentos La Pradera', 1700, DATE'2023-06-13', DATE'2023-06-17');
INSERT INTO booking (id, nuip, name, cost, "start", "end") 
VALUES (14, '18181818', 'Hotel Marriot', 2800, DATE'2023-05-01', DATE'2023-05-05');
INSERT INTO booking (id, nuip, name, cost, "start", "end") 
VALUES (15, '19191919', 'Casa de huéspedes El Descanso', 1500, DATE'2023-05-02', DATE'2023-05-05');
INSERT INTO booking (id, nuip, name, cost, "start", "end") 
VALUES (16, '20202020', 'Residencias San José', 2200, DATE'2023-05-03', DATE'2023-05-07');
INSERT INTO booking (id, nuip, name, cost, "start", "end") 
VALUES (17, '21212121', 'Apartamentos La Sabana', 2000, DATE'2023-05-05', DATE'2023-05-08');
INSERT INTO booking (id, nuip, name, cost, "start", "end") 
VALUES (18, '11111111', 'Hotel Bogotá Plaza', 3000, DATE'2023-05-06', DATE'2023-05-09');
INSERT INTO booking (id, nuip, name, cost, "start", "end") 
VALUES (19, '13131313', 'Casa de huéspedes Santa Elena', 900, DATE'2023-05-07', DATE'2023-05-10');
INSERT INTO booking (id, nuip, name, cost, "start", "end") 
VALUES (20, '14141414', 'Residencias de los Pinos', 1500, DATE'2023-05-08', DATE'2023-05-11');




INSERT INTO offer (id, cost_by_night) VALUES (1, 100);
INSERT INTO offer (id, cost_by_night) VALUES (24, 250);
INSERT INTO offer (id, cost_by_night) VALUES (20, 75);
INSERT INTO offer (id, cost_by_night) VALUES (13, 150);
INSERT INTO offer (id, cost_by_night) VALUES (2, 50);
INSERT INTO offer (id, cost_by_night) VALUES (3, 120);
INSERT INTO offer (id, cost_by_night) VALUES (4, 80);
INSERT INTO offer (id, cost_by_night) VALUES (5, 90);
INSERT INTO offer (id, cost_by_night) VALUES (6, 200);
INSERT INTO offer (id, cost_by_night) VALUES (7, 110);
INSERT INTO offer (id, cost_by_night) VALUES (8, 70);
INSERT INTO offer (id, cost_by_night) VALUES (9, 140);
INSERT INTO offer (id, cost_by_night) VALUES (10, 60);
INSERT INTO offer (id, cost_by_night) VALUES (11, 170);
INSERT INTO offer (id, cost_by_night) VALUES (12, 130);
INSERT INTO offer (id, cost_by_night) VALUES (14, 180);
INSERT INTO offer (id, cost_by_night) VALUES (15, 40);
INSERT INTO offer (id, cost_by_night) VALUES (16, 280);
INSERT INTO offer (id, cost_by_night) VALUES (17, 210);
INSERT INTO offer (id, cost_by_night) VALUES (18, 95);
INSERT INTO offer (id, cost_by_night) VALUES (19, 225);




INSERT INTO residence_spec (name, bedroom_num, administration_fee, insurance_desc) 
VALUES ('Residencias Doña Juana', 101, 200, 'Seguro de inmuebles vigente por 6 meses');


INSERT INTO rooms_hotel (nit, name) VALUES (1234567890, 'Hotel Hilton');
INSERT INTO rooms_hotel (nit, name) VALUES (2345678901, 'Hotel Estelar');
INSERT INTO rooms_hotel (nit, name) VALUES (3456789012 , 'Hotel Marriot');
INSERT INTO rooms_hotel (nit, name) VALUES (4567890123 , 'Hotel Bogotá Plaza');




INSERT INTO service_scheme (name, service_name, service_cost) 
VALUES ('Casa de huéspedes San Francisco', 'Luz, Agua, Lavanderia', 300);
INSERT INTO service_scheme (name, service_name, service_cost) 
VALUES ('Casa del Árbol', 'Aseo, Luz, Internet', 400);
INSERT INTO service_scheme (name, service_name, service_cost) 
VALUES ('Casa de huéspedes El Descanso', 'Aseo, luz', 50);
INSERT INTO service_scheme (name, service_name, service_cost) 
VALUES ('Casa de huéspedes Santa Elena', 'Agua, Lavanderia, Luz', 400);

