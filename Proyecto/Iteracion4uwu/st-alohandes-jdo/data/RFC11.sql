SELECT
    U.LOGIN AS Usuario,
    U.TIPOID AS Tipo_Identificación,
    U.RELACIONU AS Relación_Usuario,
    O.ID AS Oferta_ID,
    O.CAPACIDAD AS Capacidad,
    O.PRECIO AS Precio,
    O.TIPO AS Tipo_Alojamiento,
    C.ID AS Cliente_ID,
    C.MEDIOPAGO AS Medio_Pago
FROM
    A_USUARIO U
    JOIN A_OFERTA O ON U.LOGIN = O.OPERADOR
    JOIN A_CLIENTE C ON U.LOGIN = C.ID
WHERE
    U.LOGIN <> :usuario_actual -- Agrega la condición para filtrar los usuarios que no sean el usuario actual
    AND NOT EXISTS (
        SELECT 1
        FROM A_RESERVA R
        WHERE R.CLIENTE = U.LOGIN
        AND R.FECHA_RESERVA BETWEEN :fecha_inicio AND :fecha_fin
    )
ORDER BY
    -- Aquí puedes especificar el criterio de clasificación deseado
    -- Puedes usar cualquier columna de la tabla USUARIO, OFERTA o CLIENTE
    -- Ejemplos:
    -- U.LOGIN (por datos del cliente)
    -- O.ID (por oferta de alojamiento)
    -- O.TIPO (por tipo de alojamiento)
    U.LOGIN;
