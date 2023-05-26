ALTER SESSION SET current_schema = parranderos;

SELECT
    b.id,
    b.nombre,
    COUNT(b.id) AS num_apariciones
FROM
         bebedores b
    INNER JOIN frecuentan f ON b.id = f.id_bebedor
    INNER JOIN gustan     g ON b.id = g.id_bebedor
GROUP BY
    b.nombre,
    b.id
ORDER BY
    num_apariciones DESC
FETCH FIRST 10 ROWS ONLY;