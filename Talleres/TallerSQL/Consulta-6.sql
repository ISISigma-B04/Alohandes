SELECT
    b.nombre,
    b.ciudad,
    COUNT(DISTINCT s.id_bebida)
FROM
         bares b
    INNER JOIN sirven  s ON b.id = s.id_bar
    INNER JOIN bebidas d ON s.id_bebida = d.id
WHERE
        b.presupuesto = 'Alto'
    AND d.grado_alcohol > 10
GROUP BY
    b.nombre,
    b.ciudad;