ALTER SESSION SET current_schema = parranderos;

SELECT
    b.nombre                      AS bar_nombre,
    s.horario                     AS horario,
    COUNT(DISTINCT(g.id_bebedor)) AS bebedores_q
FROM
         bares b
    INNER JOIN sirven     s ON b.id = s.id_bar
    INNER JOIN gustan     g ON s.id_bebida = g.id_bebida
    INNER JOIN bebedores  bd ON g.id_bebedor = bd.id
    INNER JOIN bebidas    bda ON g.id_bebida = bda.id
    INNER JOIN frecuentan f ON s.id_bar = f.id_bar
WHERE
        b.ciudad <> bd.ciudad
    AND bda.nombre = 'Duff'
GROUP BY
    b.nombre,
    s.horario
ORDER BY
    b.nombre;