SELECT a_usuario.*, a_cliente.*
FROM a_usuario
JOIN a_cliente ON a_usuario.login = a_cliente.id
WHERE a_usuario.login NOT IN (
    SELECT DISTINCT a_reserva.cliente
    FROM a_reserva
    JOIN a_oferta ON a_reserva.propiedad = a_oferta.id
    WHERE a_reserva.fecha_inicio BETWEEN <fecha_inicio> AND <fecha_fin>
        AND a_oferta.id = <id_oferta>
)
ORDER BY 
    CASE <criterio>
        WHEN 'cliente' THEN a_cliente.id
        WHEN 'oferta' THEN NULL
        WHEN 'tipo' THEN NULL
        ELSE a_usuario.login
    END;
