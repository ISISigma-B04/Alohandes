ALTER SESSION SET current_schema = parranderos;

SELECT
    g.id_bebida,
    COUNT(v.bebedor_id) AS bebedores_q
FROM
         gustan g
    JOIN (
        SELECT
            b.id         AS bebedor_id,
            COUNT(br.id) AS bares_q
        FROM
                 bebedores b
            JOIN frecuentan f ON b.id = f.id_bebedor
            JOIN bares      br ON br.id = f.id_bar
        WHERE
            b.ciudad <> br.ciudad
        GROUP BY
            b.id
    ) v ON v.bebedor_id = g.id_bebedor
GROUP BY
    g.id_bebida
ORDER BY
    bebedores_q DESC
FETCH FIRST 10 ROWS ONLY;

