package edu.uniandes.___data;

import edu.uniandes.annotations.core.Data;
import edu.uniandes.annotations.core.Query;

import java.util.List;

/** Clase para modelar el concepto BEBEDOR del negocio de los Parranderos */
@Data("bebedor")
@Query(k="cambiar ciudad bebedor", v="UPDATE bebedor SET ciudad = ? WHERE id = ?")
class Bebedor {
    /** El identificador ÚNICO del bebedor */
    long id;
    /** El nombre del bebedor */
    String nombre;
    /** La ciudad del bebedor */
    String ciudad;
    /** El presupuesto del bebedor (ALTO, MEDIO, BAJO) */
    String presupuesto;
    /**
     * Las visitas realizadas por el bebedor.
     * Cada visita es una tripleta de objetos [Bar, Timestamp, String], que representan el bar, la fecha
     * y el horario en que el bebedor realizó la visita
     */
    List<Object[]> visitasRealizadas;
    /**
     * Las bebidas que le gustan el bebedor.
     * Cada visita es una pareja de objetos [Bebida, String], que representan la bebida y el nombre del
     * tipo de bebida que le gustan al bebedor
     */
    List<Object[]> bebidasQueLeGustan;
}
