SELECT c.*,p.precio,h.tipo,re.mes
FROM a_cliente c
INNER JOIN a_reservacolectiva rcc ON rcc.cliente = c.id
INNER JOIN a_reserva r ON rcc.id = r.colectiva
INNER JOIN a_oferta p ON p.id = r.propiedad
INNER JOIN a_habitacion h ON p.id = h.id
INNER JOIN a_apartamento ap ON p.id = ap.id
INNER JOIN (SELECT COUNT(*) AS mes,c.id AS id,COUNT(rcc1.cliente) AS reservas
         FROM a_cliente c
         INNER JOIN a_reservacolectiva rcc1 ON rcc1.cliente = c.id
         INNER JOIN a_reserva r1 ON rcc1.id = r1.colectiva
         INNER JOIN a_oferta p ON p.id = r1.propiedad
         WHERE TO_CHAR(r1.fecha_inicio, 'MM') - TO_CHAR((SELECT r2.fecha_inicio
                                                         FROM a_cliente c2
                                                         INNER JOIN a_reservacolectiva rcc2 ON rcc2.cliente = c2.id
                                                         INNER JOIN a_reserva r2 ON rcc2.id = r2.colectiva
                                                         INNER JOIN a_oferta p2 ON p2.id = r2.propiedad
                                                         WHERE c.id = c2.id)) = - 1
         GROUP BY c.id) re ON re.id = c.id
WHERE p.precio > 150 OR h.tipo = 2 OR re.mes = re.reservas;