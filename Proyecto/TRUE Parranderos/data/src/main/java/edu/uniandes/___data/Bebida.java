package edu.uniandes.___data;

import edu.uniandes.annotations.core.Data;
import edu.uniandes.annotations.core.Query;

/** Clase para modelar el concepto BEBIDA del negocio de los Parranderos */
@Data("bebida")
@Query(k="eliminar no servidas", v="DELETE FROM bebida WHERE id NOT IN (SELECT idBebida FROM sirven)")
class Bebida {
    /** El identificador ÚNICO de la bebida */
    long id;
    /** El nombre de la bebida */
    String nombre;
    /** El identificador del tipo de bebida de la bebida. Debe existir en la tabla de tipoBebida */
    long idTipoBebida;
    /** El grado de alcohol de la bebida (Mayor que 0) */
    int gradoAlcohol;
}
