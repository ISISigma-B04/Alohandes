SELECT
    U.LOGIN AS Usuario,
    U.TIPOID AS Tipo_Identificaci�n,
    U.RELACIONU AS Relaci�n_Usuario,
    O.ID AS Oferta_ID,
    O.CAPACIDAD AS Capacidad,
    O.PRECIO AS Precio,
    P.CATEGORIA AS Tipo_Alojamiento,
    C.ID AS Cliente_ID,
    C.MEDIOPAGO AS Medio_Pago
FROM
    A_USUARIO U
    JOIN A_OFERTA O ON U.LOGIN = O.OPERADOR
    JOIN A_RESERVA R ON O.ID = R.PROPIEDAD
    JOIN A_CLIENTE C ON U.LOGIN = C.ID
    JOIN A_OPERADOR P ON U.LOGIN = P.ID
WHERE
    R.FECHA_INICIO BETWEEN :fecha_inicio AND :fecha_fin
    AND P.CATEGORIA = :categoria
    AND U.TIPOID = :tipo_de_Id
    AND U.RELACIONU = :relacion
ORDER BY
    O.ID, -- (por oferta de alojamiento)
    P.CATEGORIA, -- (por tipo de alojamiento)
    U.LOGIN;
