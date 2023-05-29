select count(*) from a_operador;
select count(*) from a_usuario;
select count(*) from a_oferta;
select count(*) from a_reservacolectiva;
select count(*) from a_reserva;
select count(*) from a_cliente;

SELECT SUM(num_rows) FROM all_tables
    where owner='ISIS2304B28202310';
