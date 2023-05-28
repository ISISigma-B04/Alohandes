SELECT a_usuario.*, a_cliente.*, a_oferta.*
FROM a_usuario
JOIN a_cliente ON a_usuario.login = a_cliente.id
JOIN a_reserva ON a_cliente.id = a_reserva.cliente
JOIN a_oferta ON a_reserva.propiedad = a_oferta.id
WHERE a_reserva.fecha_inicio BETWEEN <fecha_inicio> AND <fecha_fin>
    AND a_oferta.id = <id_oferta>
ORDER BY 
    CASE <criterio>
        WHEN 'cliente' THEN a_cliente.id
        WHEN 'oferta' THEN a_oferta.id
        WHEN 'tipo' THEN a_oferta.tipo
        ELSE a_usuario.login
    END;
