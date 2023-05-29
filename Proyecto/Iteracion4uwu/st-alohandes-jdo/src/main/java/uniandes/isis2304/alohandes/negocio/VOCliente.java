package uniandes.isis2304.alohandes.negocio;

/**
 * Interfaz para los métodos get de cliente.
 */
public interface VOCliente {

    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * @return medio de pago del cliente.
     */
    String getMedio_Pago();

    /**
     * @return numero de reservas del cliente.
     */
    int getReservas();


    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos del cliente.
     */ String toString();

}
