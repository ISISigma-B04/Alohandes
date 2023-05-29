package uniandes.isis2304.alohandes.negocio;

/**
 * Interfaz para los métodos get de reserva.
 */
public interface VOReserva {

    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * @return id
     */
    long getId();


    /**
     * @return fechaInicio
     */
    String getFechaInicio();

    /**
     * @return fechaFin.
     */
    String getFechaFin();

    /**
     * @return personas.
     */
    int getPersonas();

    /**
     * @return finCancelacionOporuna.
     */
    String getFinCancelacionOportuna();

    /**
     * @return porcentajeAPagar.
     */
    double getPorcentajeAPagar();

    /**
     * @return montoTotal.
     */
    double getMontoTotal();

    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos de la reserva.
     */ String toString();


}
