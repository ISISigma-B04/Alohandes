package uniandes.isis2304.alohandes.persistencia;

import uniandes.isis2304.alohandes.negocio.Cliente;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

public class SQLCliente {

    /* ****************************************************************
     * 			Constantes
     *****************************************************************/

    private final static String SQL = PersistenciaAlohandes.SQL;

    /* ****************************************************************
     * 			Atributos
     *****************************************************************/
    /**
     * El manejador de persistencia general de la aplicación
     */
    private final PersistenciaAlohandes pp;

    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * Constructor
     *
     * @param pp - El Manejador de persistencia de la aplicación
     */
    public SQLCliente(PersistenciaAlohandes pp) {
        this.pp = pp;
    }

    /**
     * Crea y ejecuta la sentencia SQL para adicionar un CLIENTE a la base de datos de Alohandes
     *
     * @param pm        - El manejador de persistencia
     * @param id        - id del cliente.
     * @param medioPago - medio de pago del cliente.
     * @param reservas  - reservas del cliente.
     * @return EL número de tuplas insertadas
     */
    public long adicionarCliente(PersistenceManager pm, String id, String medioPago, int reservas) {

        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCliente() + "(id, medio_Pago) values (?, ?)");
        q.setParameters(id, medioPago, reservas);
        return (long) q.executeUnique();

    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar CLIENTES de la base de datos de Alohandes, por su id
     *
     * @param pm        - El manejador de persistencia
     * @param idCliente - id del cliente
     * @return EL número de tuplas eliminadas
     */
    public long eliminarClientePorId(PersistenceManager pm, long idCliente) {
        String sql = "DELETE FROM " + pp.darTablaCliente() + " WHERE id = " + idCliente;
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idCliente);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN CLIENTE de la
     * base de datos de Alohandes, por su identificador
     *
     * @param pm        - El manejador de persistencia
     * @param idCliente - El identificador del cliente
     * @return El objeto CLIENTE que tiene el identificador dado
     */
    public Cliente darClientePorId(PersistenceManager pm, long idCliente) {
        String sql = "SELECT * FROM " + pp.darTablaCliente() + " WHERE id = " + idCliente;
        Query q = pm.newQuery(SQL, sql);
        q.setResultClass(Cliente.class);
        q.setParameters(idCliente);
        return (Cliente) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CLIENTES de la
     * base de datos de Parranderos
     *
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos CLIENTE
     */
    public List<Cliente> darClientes(PersistenceManager pm) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente());
        q.setResultClass(Cliente.class);
        return (List<Cliente>) q.executeList();
    }

    /* *****************************************************
     *                REQUERIMIENTO: RFC8
     ******************************************************* */

    /**
     * Mostrar clientes frecuentes.
     *
     * @param pm - El manejador de persistencia
     * @return Una lista de arreglos de objetos, de tamaño 2. Los elementos del arreglo corresponden a los datos del cliente:
     * (id, medio de pago, reservas)
     */
    public List<Object> darDineroAnioActual(PersistenceManager pm) {
        String sql = "with tiempo as(";
        sql += " SELECT count( cli.reservas )as num, (res.fecha_fin - res.fecha_inicio ) as noches";
        sql += " FROM " + pp.darCliente() + "cl";
        sql += "INNER JOIN" + pp.darTablaReserva() + "res ON(cl.reservas = res.id)";
        sql += "group by (res.fecha_fin - res.fecha_inicio) ";
        sql += ")";
        sql += "SELECT cl.*";
        sql += "FROM tiempo t, " + pp.darCliente() + "cl";
        sql += "WHERE t.num >= 3 OR t.noches >= 15";
        Query q = pm.newQuery(SQL, sql);
        return q.executeList();
    }

    /* *****************************************************
     *                REQUERIMIENTO: RFC13
     ******************************************************* */


    /**
     * Mostrar clientes buenos.
     *
     * @param pm - El manejador de persistencia
     * @return Una lista de arreglos de objetos,con los datos del cliente y de por que es un buen cliente.
     */
    public List<Object> darBuenosClientes(PersistenceManager pm) {

        String sql = "SELECT c.*,p.precio,h.tipo,re.mes";
        sql += " FROM a_cliente c";
        sql += " INNER JOIN a_reservacolectiva rcc ON rcc.cliente = c.id";
        sql += " INNER JOIN a_reserva r ON rcc.id = r.colectiva";
        sql += " INNER JOIN a_oferta p ON p.id = r.propiedad";
        sql += " INNER JOIN a_habitacion h ON p.id = h.id";
        sql += " INNER JOIN a_apartamento ap ON p.id = ap.id";
        sql += " INNER JOIN (";
        sql += " SELECT COUNT(*) AS mes,c.id AS id,COUNT(rcc1.cliente) AS reservas FROM a_cliente c";
        sql += " INNER JOIN a_reservacolectiva rcc1 ON rcc1.cliente = c.id";
        sql += " INNER JOIN a_reserva r1 ON rcc1.id = r1.colectiva";
        sql += " INNER JOIN a_oferta p ON p.id = r1.propiedad";
        sql += " WHERE TO_CHAR(r1.fecha_inicio, 'MM') - TO_CHAR((";
        sql += " SELECT r2.fecha_inicio";
        sql += " FROM a_cliente c2";
        sql += " INNER JOIN a_reservacolectiva rcc2 ON rcc2.cliente = c2.id";
        sql += " INNER JOIN a_reserva r2 ON rcc2.id = r2.colectiva";
        sql += " INNER JOIN a_oferta p2 ON p2.id = r2.propiedad";
        sql += " WHERE c.id = c2.id)) = - 1";
        sql += " GROUP BY c.id) re ON re.id = c.id";
        sql += " WHERE p.precio > 150 OR h.tipo = 2 OR re.mes = re.reservas";
        Query q = pm.newQuery(SQL, sql);
        return q.executeList();
    }

}
