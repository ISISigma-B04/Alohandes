package edu.uniandes.___data;

import edu.uniandes.annotations.core.Data;
import edu.uniandes.annotations.core.Query;

import java.sql.Timestamp;

/**
 * Clase para modelar la relación VISITAN del negocio de los Parranderos:
 * Cada objeto de esta clase representa el hecho que un bebedor visitó un bar y viceversa.
 * Se modela mediante los identificadores del bebedor y del bar respectivamente
 * Debe existir un bebedor con el identificador dado
 * Debe existir un bar con el identificador dado
 * Adicionalmente contiene la fecha y el horario (DIURNO, NOCTURNO, TODOS) en el cual el bebedor visitó el bar
 */
@Data("visitan")
@Query(k="visitan v2",v="SELECT idBebedor, idBar, fechaVisita, horario FROM VISITAN")
class Visitan {
    /** El identificador del bebedor que realiza la visita */
    long idBebedor;
    /** El identificador del bar visitado */
    long idBar;
    /** La fecha de la visita */
    Timestamp fechaVisita;
    /** El horario en que se realizó la visita (DIURNO, NOCTURNO, TODOS) */
    String horario;
}
