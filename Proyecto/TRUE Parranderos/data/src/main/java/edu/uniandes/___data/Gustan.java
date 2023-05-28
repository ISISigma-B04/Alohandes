package edu.uniandes.___data;

import edu.uniandes.annotations.core.Data;

/**
 * Clase para modelar la relación GUSTAN del negocio de los Parranderos:
 * Cada objeto de esta clase representa el hecho que un bebedor gusta de una bebida y viceversa.
 * Se modela mediante los identificadores del bebedor y de la bebida respectivamente.
 * Debe existir un bebedor con el identificador dado
 * Debe existir una bebida con el identificador dado
 */
@Data("gustan") class Gustan {
    /** El identificador del bebedor que gusta de la bebida */
    long idBebedor;
    /** El identificador de la bebida que gusta al bebedor */
    long idBebida;
}
