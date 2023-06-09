package uniandes.isis2304.alohandes.persistencia;

import uniandes.isis2304.alohandes.negocio.Operador;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.List;

public class SQLOperador {


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
    public SQLOperador(PersistenciaAlohandes pp) {
        this.pp = pp;
    }

    /**
     * Crea y ejecuta la sentencia SQL para adicionar un OPERADOR a la base de datos de Alohandes
     *
     * @param id                              -  id del operador.
     * @param numeroRNT                       -  numero de RNT.
     * @param vencimientoRNT                  - fecha de vencimiento de RNT.
     * @param registroSuperTurismo            -  registro del super turismo del operador.
     * @param vencimientoRegistroSuperTurismo fecha de vencimiento del registro de super turismo.
     * @param categoria                       - categoria del operador.
     * @param direccion                       -  direccion del operador.
     * @param horaApertura                    - hora de apertura.
     * @param horaCierre                      - hora de cierre.
     * @param tiempoMinimo                    -  tiempo minimo (Mayor a 0)
     * @param gananciaAnioActual              - ganacia del anio actual.
     * @param gananciAnioCorrido              - ganacia anio corrido.
     * @param habitaciones                    - habitaciones del operador.
     * @param apartamentos                    - apartamentos del operador.
     * @return EL número de tuplas insertadas
     */
    public long adicionarOperador(PersistenceManager pm, String id, int numeroRNT, String vencimientoRNT,
                                  String registroSuperTurismo, String vencimientoRegistroSuperTurismo, String categoria,
                                  String direccion,
                                  String horaApertura, String horaCierre, int tiempoMinimo, double gananciaAnioActual,
                                  double gananciAnioCorrido, ArrayList habitaciones, ArrayList apartamentos) {

        Query q = pm.newQuery(SQL,
                              "INSERT INTO A_OPERADOR(id, numero_RNT,vencimiento_RNT,registro_Super_Turismo,vencimiento_Registro_Super_Turismo,categoria,direccion,hora_Apertura,hora_Cierre,tiempo_Minimo,ganancia_Anio_Actual,ganancia_Anio_Corrido,habitaciones,apartamentos) values (?, ?, TO_DATE(?), ?,TO_DATE(?),?,?,TO_DATE(?),TO_DATE(?),?,?,?,?,?)");
        q.setParameters(id, numeroRNT, vencimientoRNT, registroSuperTurismo, vencimientoRegistroSuperTurismo, categoria,
                        direccion,
                        horaApertura, horaCierre, tiempoMinimo, gananciaAnioActual, gananciAnioCorrido, habitaciones,
                        apartamentos);
        return (long) q.executeUnique();

    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar OPERADORES de la base de datos de Alohandes, por su id
     *
     * @param pm         - El manejador de persistencia
     * @param idOperador - id del operador
     * @return EL número de tuplas eliminadas
     */
    public long eliminarOperadorPorId(PersistenceManager pm, long idOperador) {
        String sql = "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE ";
        sql += " BEGIN TRAN ";
        sql += " DELETE FROM A_OPERADOR WHERE id = ? ";
        sql += " COMMIT TRAN";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(idOperador);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN OPERADOR de la
     * base de datos de Alohandes, por su identificador
     *
     * @param pm         - El manejador de persistencia
     * @param idOperador - El identificador del operador
     * @return El objeto OPERADOR que tiene el identificador dado
     */
    public Operador darOperadorPorId(PersistenceManager pm, long idOperador) {
        String sql = " SET TRANSACTION ISOLATION LEVEL SERIALIZABLE ";
        sql += " BEGIN TRAN ";
        sql += " SELECT * FROM A_OPERADOR WHERE id = ?";
        sql += " COMMIT TRAN ";
        Query q = pm.newQuery(SQL, sql);
        q.setResultClass(Operador.class);
        q.setParameters(idOperador);
        return (Operador) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar OPERADORES de la base de datos de Alohandes, por su login
     *
     * @param pm            - El manejador de persistencia
     * @param loginOperador - id del operador
     * @return EL número de tuplas eliminadas
     */
    public long eliminarOperadorPorLogin(PersistenceManager pm, long loginOperador) {
        String sql = "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE ";
        sql += " BEGIN TRAN";
        sql += " DELETE FROM A_OPERADOR WHERE login = ? ";
        sql += " COMMIT TRAN";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(loginOperador);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN OPERADOR de la
     * base de datos de Alohandes, por su login
     *
     * @param pm            - El manejador de persistencia
     * @param loginOperador - El identificador del operador
     * @return El objeto OPERADOR que tiene el identificador dado
     */
    public Operador darOperadorPorLogin(PersistenceManager pm, String loginOperador) {
        String sql = "SET TRANSACTION ISOLATION LEVEL SERIALIZABLE ";
        sql += " BEGIN TRAN";
        sql += " SELECT * FROM A_OPERADOR WHERE login = ?";
        sql += " COMMIT TRAN ";
        Query q = pm.newQuery(SQL, sql);
        q.setResultClass(Operador.class);
        q.setParameters(loginOperador);
        return (Operador) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS OPERADORES de la
     * base de datos de Parranderos
     *
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos OPERADOR
     */
    public List<Operador> darOperadores(PersistenceManager pm) {
        String sql = "SELECT * FROM A_OPERADOR";
        Query<Operador> q = pm.newQuery(SQL, sql);
        return q.executeResultList(Operador.class);
    }

    /* *****************************************************
     *                REQUERIMIENTO: RFC1
     ******************************************************* */

    /**
     * Mostrar el dinero recibido por cada operador de alojamiento durante el anio actual.
     *
     * @param pm - El manejador de persistencia
     * @return Una lista de arreglos de objetos, de tamaño 2. Los elementos del arreglo corresponden a los datos del operador:
     * (id, gananciaActual)
     */
    public List<Object[]> darDineroAnioActual(PersistenceManager pm) {
        String sql = "with anio as(";
        sql += "select EXTRACT(YEAR FROM fecha_fin) AS fecha, res.monto_total AS monto, op.id as id ";
        sql += " FROM " + pp.darTablaOperador() + " op ";
        sql += " INNER JOIN " + pp.darTablaHabitacion() + " hab ON(op.habitacion = hab.id)";
        sql += " INNER JOIN " + pp.darTablaApartamento() + " ap ON(op.apartamento = ap.id)";
        sql += " INNER JOIN " + pp.darTablaReservaHabitacion() + " resha ON(hab.id = resha.id_Habitacion)";
        sql += " INNER JOIN " + pp.darTablaReservaApartamento() + " resap ON(ap.id = resap.id_Apartamento)";
        sql += " INNER JOIN " + pp.darTablaReserva() + " res ON(res.id = resha.id_Reserva AND res.id = resap.id_Reserva)";
        sql += " )";
        sql += " SELECT id,  count(monto) AS gananciaActual";
        sql += " FROM anio";
        sql += " WHERE EXTRACT(YEAR FROM CURRENT_TIMESTAMP) = fecha";
        sql += " GROUP BY id, fecha";

        Query<Object[]> q = pm.newQuery(SQL, sql);
        return q.executeResultList(Object[].class);
    }


    /**
     * Mostrar el dinero recibido por cada operador de alojamiento durante el anio de corrido.
     *
     * @param pm - El manejador de persistencia
     * @return Una lista de arreglos de objetos, de tamaño 2. Los elementos del arreglo corresponden a los datos del operador:
     * (id, gananciaDeCorrido)
     */
    public List<Object[]> darDineroAnioCorrido(PersistenceManager pm) {
        String sql = "select op.id , count( res.monto_total )";
        sql += " FROM " + pp.darTablaOperador() + " op";
        sql += " INNER JOIN" + pp.darTablaHabitacion() + " hab ON(op.habitacion = hab.id)";
        sql += " INNER JOIN" + pp.darTablaApartamento() + " ap ON(op.apartamento = ap.id)";
        sql += " INNER JOIN" + pp.darTablaReservaHabitacion() + " resha ON(hab.id = resha.id_Habitacion)";
        sql += " INNER JOIN" + pp.darTablaReservaApartamento() + " resap ON(ap.id = resap.id_Apartamento)";
        sql += " INNER JOIN" + pp.darTablaReserva() + " res ON(res.id = resha.id_Reserva AND res.id = resap.id_Reserva)";
        sql += " WHERE  fecha_fin > DATE_SUB(CURRENT_TIMESTRAP, INTERVAL 1 YEAR)  AND fecha_fin <= CURRENT_TIMESTRAP";
        sql += " GROUP BY op.id";
        Query<Object[]> q = pm.newQuery(SQL, sql);
        return q.executeResultList(Object[].class);
    }


    public List<Object[]> consumoSI(PersistenceManager pm, String orden, String FI, String FF, String id) {
        String sql = "SELECT U.LOGIN AS Usuario, U.TIPOID AS Tipo_Identificacion, U.RELACIONU AS Relacion_Usuario, O.ID AS Oferta_ID, ";
        sql += "O.CAPACIDAD AS Capacidad, O.PRECIO AS Precio, P.CATEGORIA AS Tipo_Alojamiento, C.ID AS Cliente_ID, C.MEDIOPAGO AS Medio_Pago ";
        sql += "FROM A_USUARIO U ";
        sql += "JOIN A_OFERTA O ON U.LOGIN = O.OPERADOR ";
        sql += "JOIN A_RESERVA R ON O.ID = R.PROPIEDAD ";
        sql += "JOIN A_CLIENTE C ON U.LOGIN = C.ID ";
        sql += "JOIN A_OPERADOR P ON U.LOGIN = P.ID ";
        sql += "WHERE R.FECHA_INICIO BETWEEN TO_DATE('" + FI + "', 'YYYY-MM-DD') AND TO_DATE ('" + FF + "', 'YYYY-MM-DD')";
        sql += "GROUP BY U.LOGIN, U.TIPOID, U.RELACIONU, O.ID, O.CAPACIDAD, O.PRECIO, P.CATEGORIA, C.ID, C.MEDIOPAGO ";
        sql += "HAVING COUNT(R.ID) > 0 ";
        sql += "ORDER BY O.ID, P.CATEGORIA, U.LOGIN";
        Query<Object[]> q = pm.newQuery(SQL, sql);
        return q.executeResultList(Object[].class);
    }


    public List<Object[]> consumoNO(PersistenceManager pm, String orden, String FI, String FF, String id) {
        String sql = "SELECT U.LOGIN AS Usuario, U.TIPOID AS Tipo_Identificacion, U.RELACIONU AS Relacion_Usuario, O.ID AS Oferta_ID, ";
        sql += "O.CAPACIDAD AS Capacidad, O.PRECIO AS Precio, P.CATEGORIA AS Tipo_Alojamiento, C.ID AS Cliente_ID, C.MEDIOPAGO AS Medio_Pago ";
        sql += "FROM A_USUARIO U ";
        sql += "JOIN A_OFERTA O ON U.LOGIN = O.OPERADOR ";
        sql += "JOIN A_RESERVA R ON O.ID = R.PROPIEDAD ";
        sql += "JOIN A_CLIENTE C ON U.LOGIN = C.ID ";
        sql += "JOIN A_OPERADOR P ON U.LOGIN = P.ID ";
        sql += "WHERE R.FECHA_INICIO BETWEEN TO_DATE('" + FI + "', 'YYYY-MM-DD') AND TO_DATE ('" + FF + "', 'YYYY-MM-DD')";
        sql += "GROUP BY U.LOGIN, U.TIPOID, U.RELACIONU, O.ID, O.CAPACIDAD, O.PRECIO, P.CATEGORIA, C.ID, C.MEDIOPAGO ";
        sql += "HAVING COUNT(R.ID) = 0 ";
        sql += "ORDER BY O.ID, P.CATEGORIA, U.LOGIN";
        Query<Object[]> q = pm.newQuery(SQL, sql);
        return q.executeResultList(Object[].class);
    }


}
