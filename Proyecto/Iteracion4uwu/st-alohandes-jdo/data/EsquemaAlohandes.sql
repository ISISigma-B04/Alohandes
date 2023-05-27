--- Sentencias SQL para la creación del esquema de alohandes
--- Las tablas tienen prefijo A_ para facilitar su acceso desde SQL Developer

-- Creación del secuenciador
CREATE SEQUENCE alohandes_sequence;

-- Creación de la tabla USUARIO y especificación de sus restricciones
CREATE TABLE a_usuario
(
    id        NUMBER,
    tipoid    VARCHAR2(255 BYTE),
    login     VARCHAR2(255 BYTE),
    relacionu VARCHAR2(255 BYTE),
    CONSTRAINT a_usuario_pk PRIMARY KEY (login)
);

ALTER TABLE a_usuario
    ADD CONSTRAINT cs_u_tipoid
        CHECK (tipoid IN ('CARNET_U', 'CEDULA', 'PASAPORTE'))
            ENABLE;

CREATE INDEX idx_login ON a_usuario (tipoid);

-- Creación de la tabla OPERADOR y especificación de sus restricciones
CREATE TABLE a_operador
(
    id                      VARCHAR(255),
    numero_rnt              NUMBER,
    vencimiento_rnt         DATE,
    registro_super_turismo  VARCHAR(255),
    vencimiento_registro_st DATE,
    categoria               VARCHAR(255),
    direccion               VARCHAR(511),
    hora_apertura           DATE,
    hora_cierre             DATE,
    tiempo_minimo           NUMBER,
    ganancia_anio_actual    NUMBER,
    ganancia_anio_corrido   NUMBER,
    CONSTRAINT a_operador_pk PRIMARY KEY (id)
);

ALTER TABLE a_operador
    ADD CONSTRAINT fk_v0_usuario
        FOREIGN KEY (id)
            REFERENCES a_usuario (login)
                ENABLE;

ALTER TABLE a_operador
    ADD CONSTRAINT un_numero_rnt
        UNIQUE (numero_rnt)
            ENABLE;

ALTER TABLE a_operador
    ADD CONSTRAINT ck_categoria
        CHECK (categoria IN ('HOTEL', 'HOSTAL', 'P_NATURAL', 'APARTAMENTO', 'VECINOS', 'VIVIENDA_U'))
            ENABLE;

ALTER TABLE a_operador
    ADD CONSTRAINT cs_tiempo_minimo
        CHECK (tiempo_minimo > 0)
            ENABLE;


-- Creación de la tabla CLIENTE y especificación de sus restricciones
CREATE TABLE a_cliente
(
    id        VARCHAR(255 BYTE),
    mediopago VARCHAR(255 BYTE),
    CONSTRAINT a_cliente_pk PRIMARY KEY (id)
);

ALTER TABLE a_cliente
    ADD CONSTRAINT fk_v1_usuario
        FOREIGN KEY (id)
            REFERENCES a_usuario (login)
                ENABLE;

-- Creación de la tabla PROPIEDAD y especificación de sus restricciones

CREATE TABLE a_oferta
(
    id              NUMBER,
    capacidad       NUMBER,
    precio          NUMBER,
    tamanio         NUMBER,
    dias_reservados NUMBER,
    fecha_creacion  DATE,
    piso            NUMBER,
    habilitada      NUMBER(1, 0),
    operador        VARCHAR(255 BYTE),
    CONSTRAINT a_propiedad_pk PRIMARY KEY (id)
);

ALTER TABLE a_oferta
    ADD CONSTRAINT fk_v2_usuario
        FOREIGN KEY (operador)
            REFERENCES a_usuario (login)
                ENABLE;

ALTER TABLE a_oferta
    ADD CONSTRAINT ck_capacidad
        CHECK (capacidad > 0)
            ENABLE;

ALTER TABLE a_oferta
    ADD CONSTRAINT ck_precio
        CHECK (precio > 0)
            ENABLE;

ALTER TABLE a_oferta
    ADD CONSTRAINT ck_tamanio
        CHECK (tamanio > 0)
            ENABLE;

CREATE INDEX idx_propiedad ON a_oferta (id);

-- Creación de la tabla HABITACION y especificación de sus restricciones
CREATE TABLE a_habitacion
(
    id         NUMBER,
    tipo       NUMBER,
    operador   VARCHAR(250 BYTE),
    individual NUMBER(1, 0),
    esquema    VARCHAR(250 BYTE),
    CONSTRAINT a_habitacion_pk PRIMARY KEY (id)
);

ALTER TABLE a_habitacion
    ADD CONSTRAINT fk_v3_usuario
        FOREIGN KEY (operador)
            REFERENCES a_usuario (login)
                ENABLE;

ALTER TABLE a_habitacion
    ADD CONSTRAINT fk_v0_propiedad
        FOREIGN KEY (id)
            REFERENCES a_oferta (id)
                ENABLE;

ALTER TABLE a_habitacion
    ADD CONSTRAINT cs_u_tipohab
        CHECK (tipo IN (1, 2, 0))
            ENABLE;

-- Creación de la tabla APARTAMENTO y especificación de sus restricciones
CREATE TABLE a_apartamento
(
    id                 NUMBER,
    operador           VARCHAR(250 BYTE),
    amueblado          NUMBER(1, 0),
    habitaciones       NUMBER,
    descripcion_menaje VARCHAR2(250 BYTE),
    descripcion_seguro VARCHAR2(250 BYTE),
    tiene_seguro       NUMBER(1, 0),
    CONSTRAINT a_apartamento_pk PRIMARY KEY (id)
);

ALTER TABLE a_apartamento
    ADD CONSTRAINT fk_v4_usuario
        FOREIGN KEY (operador)
            REFERENCES a_usuario (login)
                ENABLE;

ALTER TABLE a_apartamento
    ADD CONSTRAINT fk_v1_propiedad
        FOREIGN KEY (id)
            REFERENCES a_oferta (id)
                ENABLE;

ALTER TABLE a_apartamento
    ADD CONSTRAINT ck_habitaciones
        CHECK (habitaciones > 0)
            ENABLE;

-- Creación de la tabla SERVICIO y especificación de sus restricciones
CREATE TABLE a_servicio
(
    id             NUMBER,
    tipo           VARCHAR(250 BYTE),
    precio         NUMBER,
    intervalo_pago NUMBER,
    propiedad      NUMBER,
    CONSTRAINT a_servicio_pk PRIMARY KEY (id)
);

ALTER TABLE a_servicio
    ADD CONSTRAINT fk_v2_propiedad
        FOREIGN KEY (propiedad)
            REFERENCES a_oferta (id)
                ENABLE;

ALTER TABLE a_servicio
    ADD CONSTRAINT ck_precios
        CHECK (precio >= 0)
            ENABLE;

-- Creaación de la tabla RESERVA COLECTIVA y especificación de sus restricciones
CREATE TABLE a_reservacolectiva
(
    id           NUMBER,
    fecha_inicio DATE,
    duracion     NUMBER,
    cantidad     NUMBER,
    tipo         VARCHAR(255 BYTE),
    cliente      VARCHAR(255 BYTE),
    CONSTRAINT a_reserva_pk PRIMARY KEY (id)
);

ALTER TABLE a_reservacolectiva
    ADD CONSTRAINT ck_cantidad
        CHECK (cantidad > 0)
            ENABLE;

ALTER TABLE a_reservacolectiva
    ADD CONSTRAINT fk_v0_cliente
        FOREIGN KEY (cliente)
            REFERENCES a_cliente (id)
                ENABLE;

ALTER TABLE a_reservacolectiva
    ADD CONSTRAINT ck_tiporc
        CHECK (tipo IN ('APARTAMENTO', 'HABITACION'))
            ENABLE;
-- Creaación de la tabla RESERVA y especificación de sus restricciones
CREATE TABLE a_reserva
(
    id                       NUMBER,
    fecha_inicio             DATE,
    fecha_fin                DATE,
    personas                 NUMBER,
    fin_cancelacion_oportuna DATE,
    porcentaje_a_pagar       NUMBER,
    monto_total              NUMBER,
    propiedad                NUMBER,
    colectiva                NUMBER,
    CONSTRAINT a_reservai_pk PRIMARY KEY (id)
);

ALTER TABLE a_reserva
    ADD CONSTRAINT ck_personas
        CHECK (personas > 0)
            ENABLE;

ALTER TABLE a_reserva
    ADD CONSTRAINT fk_v3_propiedad
        FOREIGN KEY (propiedad)
            REFERENCES a_oferta (id)
                ENABLE;

ALTER TABLE a_reserva
    ADD CONSTRAINT fk_v_reservacolectiva
        FOREIGN KEY (colectiva)
            REFERENCES a_reservacolectiva (id)
                ENABLE;

CREATE INDEX idx_reserva ON a_reserva (id);


-- Creaación de la tabla RESERVAAPARTAMENTO y especificación de sus restricciones
CREATE TABLE a_reservaapartamento
(
    id_apartamento NUMBER,
    id_reserva     NUMBER,
    CONSTRAINT a_reservaa_pk PRIMARY KEY (id_apartamento, id_reserva)
);

ALTER TABLE a_reservaapartamento
    ADD CONSTRAINT fk_v_apartamento
        FOREIGN KEY (id_apartamento)
            REFERENCES a_apartamento (id)
                ENABLE;

ALTER TABLE a_reservaapartamento
    ADD CONSTRAINT fk_v0_reserva
        FOREIGN KEY (id_reserva)
            REFERENCES a_reserva (id)
                ENABLE;

-- Creación de la tabla RESERVAHABITACION y especificación de sus restricciones
CREATE TABLE a_reservahabitacion
(
    id_habitacion NUMBER,
    id_reserva    NUMBER,
    CONSTRAINT a_reservah_pk PRIMARY KEY (id_habitacion, id_reserva)
);

ALTER TABLE a_reservahabitacion
    ADD CONSTRAINT fk_v_habitacion
        FOREIGN KEY (id_habitacion)
            REFERENCES a_habitacion (id)
                ENABLE;

ALTER TABLE a_reservahabitacion
    ADD CONSTRAINT fk_v1_reserva
        FOREIGN KEY (id_reserva)
            REFERENCES a_reserva (id)
                ENABLE;




