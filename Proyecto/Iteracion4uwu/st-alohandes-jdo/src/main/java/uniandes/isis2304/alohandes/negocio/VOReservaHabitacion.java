package uniandes.isis2304.alohandes.negocio;

public interface VOReservaHabitacion {

    /**
     * @return El id de la reserva
     */
    long getIdReserva();

    /**
     * @return El tipo de la habitacion
     */
    long getIdHabitacion();


    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos.
     */ String toString();


}
