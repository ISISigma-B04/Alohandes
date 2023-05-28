WITH ocupacion_semanal AS (
    SELECT
        WEEKOFYEAR(r.fecha_inicio) AS semana,
        r.propiedad,
        COUNT(*) AS cantidad_reservas
    FROM
        a_reserva r
    GROUP BY
        WEEKOFYEAR(r.fecha_inicio),
        r.propiedad
),
max_ocupacion AS (
    SELECT
        semana,
        MAX(cantidad_reservas) AS max_ocupacion
    FROM
        ocupacion_semanal
    GROUP BY
        semana
),
min_ocupacion AS (
    SELECT
        semana,
        MIN(cantidad_reservas) AS min_ocupacion
    FROM
        ocupacion_semanal
    GROUP BY
        semana
)
SELECT
    m.semana,
    m.propiedad AS oferta_max_ocupacion,
    m.max_ocupacion AS max_ocupacion,
    n.propiedad AS oferta_min_ocupacion,
    n.min_ocupacion AS min_ocupacion,
    os.operador AS operador_max_solicitado,
    os.cantidad_reservas AS max_solicitudes,
    ls.operador AS operador_min_solicitado,
    ls.cantidad_reservas AS min_solicitudes
FROM
    max_ocupacion m
    JOIN ocupacion_semanal os ON m.semana = os.semana AND m.max_ocupacion = os.cantidad_reservas
    JOIN min_ocupacion n ON m.semana = n.semana
    JOIN ocupacion_semanal ls ON n.semana = ls.semana AND n.min_ocupacion = ls.cantidad_reservas;
