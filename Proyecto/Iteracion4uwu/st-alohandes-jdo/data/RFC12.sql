WITH ocupacion_semanal AS (
SELECT TO_CHAR(r.fecha_inicio, 'IW') AS semana,
      r.propiedad                   AS operador,
      COUNT(*)                      AS cantidad_reservas
FROM a_reserva r
GROUP BY TO_CHAR(r.fecha_inicio, 'IW'), r.propiedad),
     extremos_ocupacion AS (
SELECT semana,
       operador,
       MAX(cantidad_reservas) AS max_ocupacion,
       MIN(cantidad_reservas) AS min_ocupacion
FROM ocupacion_semanal
GROUP BY semana, operador),
     solicitados AS (
SELECT semana, operador, cantidad_reservas
FROM ocupacion_semanal
HAVING (semana, cantidad_reservas) IN (
SELECT semana, MAX(cantidad_reservas)
FROM ocupacion_semanal
GROUP BY semana
UNION ALL
SELECT semana, MIN(cantidad_reservas)
FROM ocupacion_semanal
GROUP BY semana))
SELECT e.semana,
       e.operador          AS oferta_max_ocupacion,
       e.max_ocupacion     AS max_ocupacion,
       e.operador          AS oferta_min_ocupacion,
       e.min_ocupacion     AS min_ocupacion,
       s.operador          AS operador_max_solicitado,
       s.cantidad_reservas AS max_solicitudes,
       s.operador          AS operador_min_solicitado,
       s.cantidad_reservas AS min_solicitudes
FROM extremos_ocupacion e
         JOIN solicitados s ON e.semana = s.semana;