package edu.uniandes.___data;

import edu.uniandes.annotations.core.Data;
import edu.uniandes.annotations.core.Query;

/**
 * Clase para modelar la relación SIRVEN del negocio de los Parranderos:
 * Cada objeto de esta clase representa el hecho que un bar sirve una bebida y viceversa.
 * Se modela mediante los identificadores del bar y de la bebida respectivamente
 * Debe existir un bar con el identificador dado
 * Debe existir una bebida con el identificador dado
 * Adicionalmente contiene el horario (DIURNO, NOCTURNO, TODOS) en el cual el bar sirve la bebida
 */
@Data("sirven")
@Query(k="bares y las bebidas que sirven", v="SELECT idBar count (*) as numBebidas FROM sirven GROUP BY idBar")
class Sirven {
    /** El identificador del bar que sirve la bebida */
    long idBar;
    /** El identificador de la bebida que es servida en el bar */
    long idBebida;
    /** El horario en que sirve la bebida en el bar (DIURNO, NOCTURNO, TODOS) */
    String horario;
}
