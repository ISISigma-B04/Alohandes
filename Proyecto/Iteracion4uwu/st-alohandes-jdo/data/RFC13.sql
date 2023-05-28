SELECT
    c.id AS id_cliente,
    c.tipoid AS tipo_identificacion,
    c.login AS usuario,
    CASE
        WHEN COUNT(DISTINCT DATE_TRUNC('month', r.fecha_inicio)) >= 1 THEN 'Reserva mensual'
        ELSE NULL
    END AS justificacion_reserva_mensual,
    CASE
        WHEN MIN(o.precio) > 150 THEN 'Reserva alojamientos costosos'
        ELSE NULL
    END AS justificacion_alojamientos_costosos,
    CASE
        WHEN MIN(p.tipo) = 'Suite' THEN 'Reserva siempre suites'
        ELSE NULL
    END AS justificacion_suites
FROM
    a_cliente c
    JOIN a_reserva r ON c.id = r.cliente
    JOIN a_oferta o ON r.propiedad = o.id
    JOIN a_habitacion h ON o.id = h.id
    JOIN a_propiedad p ON h.id = p.id
GROUP BY
    c.id,
    c.tipoid,
    c.login
HAVING
    COUNT(DISTINCT DATE_TRUNC('month', r.fecha_inicio)) >= 1
    AND MIN(o.precio) > 150
    AND MIN(p.tipo) = 'Suite';
