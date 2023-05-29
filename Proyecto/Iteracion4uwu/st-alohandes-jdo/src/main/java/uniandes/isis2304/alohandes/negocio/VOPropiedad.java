package uniandes.isis2304.alohandes.negocio;

public interface VOPropiedad {

    ///////////////////////////////////////
    ////////////// MÉTODOS ////////////////
    ///////////////////////////////////////

    /**
     * @return El id de la propiedad
     */
    long getId();

    /**
     * @return La capacidad de la propiedad
     */
    int getCapacidad();

    /**
     * @return El tamanio en m2 de la propiedad
     */
    double getTamanio();

    /**
     * @return El precio de la propiedad
     */
    double getPrecio();

    /**
     * @return La fecha de creación de la propiedad
     */
    String getFechaCreacion();

    /**
     * @return El número de días reservados de la propiedad
     */
    int getDiasReservados();

    /**
     * @return El piso en el que se encuentra la propiedad
     */
    int getPiso();


}
