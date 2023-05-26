ALTER SESSION SET current_schema = parranderos;

SELECT
    b.ciudad,
    SUM(
        CASE
            WHEN b.presupuesto = 'Alto' THEN
                1
            ELSE
                0
        END
    ) AS bares_alto,
    SUM(
        CASE
            WHEN b.presupuesto = 'Bajo' THEN
                1
            ELSE
                0
        END
    ) AS bares_bajo
FROM
    bares b
GROUP BY
    b.ciudad;