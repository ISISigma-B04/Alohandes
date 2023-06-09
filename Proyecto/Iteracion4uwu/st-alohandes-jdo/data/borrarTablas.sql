DROP sequence alohandes_sequence;

DROP INDEX idx_login;

DROP INDEX idx_propiedad;

DROP INDEX idx_reserva;
-- Eliminar la tabla A_RESERVAAPARTAMENTO
DROP TABLE A_RESERVAAPARTAMENTO CASCADE CONSTRAINTS;

-- Eliminar la tabla A_RESERVAHABITACION
DROP TABLE A_RESERVAHABITACION CASCADE CONSTRAINTS;

-- Eliminar la tabla A_RESERVA
DROP TABLE A_RESERVA CASCADE CONSTRAINTS;

-- Eliminar la tabla A_RESERVACOLECTIVA
DROP TABLE A_RESERVACOLECTIVA CASCADE CONSTRAINTS;

-- Eliminar la tabla A_SERVICIO
DROP TABLE A_SERVICIO CASCADE CONSTRAINTS;

-- Eliminar la tabla A_HABITACION
DROP TABLE A_HABITACION CASCADE CONSTRAINTS;

-- Eliminar la tabla A_APARTAMENTO
DROP TABLE A_APARTAMENTO CASCADE CONSTRAINTS;

-- Eliminar la tabla A_PROPIEDAD
DROP TABLE A_OFERTA CASCADE CONSTRAINTS;

-- Eliminar la tabla A_OPERADOR
DROP TABLE A_OPERADOR CASCADE CONSTRAINTS;

-- Eliminar la tabla A_CLIENTE
DROP TABLE A_CLIENTE CASCADE CONSTRAINTS;

-- Eliminar la tabla A_USUARIO
DROP TABLE A_USUARIO CASCADE CONSTRAINTS;

commit;
