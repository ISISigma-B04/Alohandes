package edu.uniandes.___data;

import edu.uniandes.annotations.core.Data;

/** Clase para modelar el concepto BAR del negocio de los Parranderos */
@Data("tipobebida") class TipoBebida {
    /** El identificador del tipo de bebida */
    long id;
    /** El nombre del tipo de bebida */
    String nombre;
}
