package uniandes.isis2304.alohandes.negocio;

import java.util.ArrayList;

/**
 * Interfaz para los métodos get de operador.
 */
public interface VOOperador {

    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * @return numero de RNT.
     */
    int getNumero_RNT();

    /**
     * @return vencimiento del RNT
     */
    String getVencimiento_RNT();

    /**
     * @return registro del super turismo del operador.
     */
    String getRegistro_Super_Turismo();

    /**
     * @return fecha de vencimiento del super tutimo.
     */
    String getVencimiento_Registro_ST();

    /**
     * @return categoria del operador.
     */
    String getCategoria();

    /**
     * @return direccion del operador.
     */
    String getDireccion();

    /**
     * @return hora de apertura.
     */
    String getHora_Apertura();

    /**
     * @return hora de cierre.
     */
    String getHora_Cierre();

    /**
     * @return tiempo minmo de estadia.
     */
    int getTiempo_Minimo();

    /**
     * @return ganacias del anio actual.
     */
    double getGanancia_Anio_Actual();

    /**
     * @return ganacias anio de corrido.
     */
    double getGanancia_Anio_Corrido();

    /**
     * @return habitaciones del operador.
     */
    ArrayList getHabitaciones();

    /**
     * @return apartamentos del operador.
     */
    ArrayList getApartamentos();

    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos del operador.
     */ String toString();

}
