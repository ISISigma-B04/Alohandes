package uniandes.isis2304.alohandes.negocio;


public interface VOHabitacion {
    ///////////////////////////////////////
    ////////////// METODOS ////////////////
    ///////////////////////////////////////


    /**
     * @return True si la habitación es individual false si la habitación es compartida
     */
    boolean isIndividual();

    /**
     * @return Ruta de la imagen con el esquema de la habitación
     */
    String getEsquema();

    /**
     * @return Tipo de habitación definido por las constantes
     */
    int getTipo();

    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos de la habitación
     */ String toString();

}
