SELECT
    U.LOGIN AS Usuario,
    U.TIPOID AS Tipo_Identificacion,
    U.RELACIONU AS Relacion_Usuario,
    O.ID AS Oferta_ID,
    O.CAPACIDAD AS Capacidad,
    O.PRECIO AS Precio,
    P.CATEGORIA AS Tipo_Alojamiento,
    C.ID AS Cliente_ID,
    C.MEDIOPAGO AS Medio_Pago
FROM
    A_USUARIO U
    JOIN A_OFERTA O ON U.LOGIN = O.OPERADOR
    JOIN A_CLIENTE C ON U.LOGIN = C.ID
    JOIN A_OPERADOR P ON U.LOGIN = P.ID
WHERE
    U.TIPOID = :tipo_de_Id
    AND U.RELACIONU = :relacion
    AND P.CATEGORIA = :categoria
    AND NOT EXISTS (
        SELECT 1
        FROM A_RESERVA R
        WHERE O.ID = R.PROPIEDAD
        AND R.FECHA_INICIO BETWEEN :fecha_inicio AND :fecha_fin
    )
ORDER BY
    O.ID, -- (por oferta de alojamiento)
    P.CATEGORIA, -- (por tipo de alojamiento)
    U.LOGIN;
