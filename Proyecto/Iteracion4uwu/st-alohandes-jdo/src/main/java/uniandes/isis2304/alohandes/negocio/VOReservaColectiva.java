package uniandes.isis2304.alohandes.negocio;

import java.util.ArrayList;

public interface VOReservaColectiva {
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
    int getDuracion();

    /**
     * @return cantidad de propiedades a resrvar.
     */
    int getCantidad();

    /**
     * @return reservas individuales.
     */
    ArrayList<Reserva> getReservas();

    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos de la reserva.
     */ String toString();

}
