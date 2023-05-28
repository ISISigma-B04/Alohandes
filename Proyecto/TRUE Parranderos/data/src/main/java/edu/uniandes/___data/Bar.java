package edu.uniandes.___data;

import edu.uniandes.annotations.core.Data;
import edu.uniandes.annotations.core.Query;

/** Clase para modelar el concepto BAR del negocio de los Parranderos */
@Data("bar")
@Query(k="bares por nombre", v="SELECT * FROm bar WHERE nombre = ?")
@Query(k="aumentar sedes bares ciudad", v="UPDATE bar SET cantsedes = cantsedes + 1 WHERE ciudad = ?")
class Bar {
    /** Identificador UNICO de los bares */
    long id;
    /** Nombre del bar */
    String nombre;
    /** Ciudad donde se encuentra el bar */
    String ciudad;
    /** Presupuesto del bar (ALTO, MEDIO, BAJO) */
    String presupuesto;
    /** Numero de sedes del bar en la ciudad */
    int cantSedes;
}
