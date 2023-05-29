package uniandes.isis2304.alohandes.negocio;


/**
 * Interfaz para los métodos get de BAR.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz
 *
 * @author Angela Vargas
 */
public interface VOServicio {
    /////////////////////////////////////////
    ///////////// Métodos ///////////////////
    /////////////////////////////////////////

    /**
     * @return El id del servicio
     */
    long getId();

    /**
     * @return El tipo de servicio
     */
    String getTipo();

    /**
     * @return El precio del servicio
     */
    double getPrecio();

    /**
     * @return El tintervalo de pago del servicio
     */
    int getIntervalo_Pago();

    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos del servicio
     */ String toString();

}

