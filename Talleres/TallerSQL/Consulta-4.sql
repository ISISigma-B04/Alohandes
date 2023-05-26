SELECT
    b.ciudad,
    COUNT(DISTINCT s.id_bar) AS bares
FROM
         bares b
    INNER JOIN sirven  s ON b.id = s.id_bar
    INNER JOIN bebidas be ON s.id_bebida = be.id
WHERE
    b.ciudad IS NOT NULL
    AND be.tipo = '1'
    AND be.grado_alcohol BETWEEN 8 AND 12
GROUP BY
    b.ciudad
ORDER BY
    bares DESC
FETCH FIRST 1 ROWS ONLY;