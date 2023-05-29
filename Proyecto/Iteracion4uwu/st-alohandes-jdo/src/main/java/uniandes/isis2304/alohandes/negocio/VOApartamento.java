package uniandes.isis2304.alohandes.negocio;

public interface VOApartamento {
    ///////////////////////////////////////
    ////////////// METODOS ////////////////
    ///////////////////////////////////////

    /**
     * @return Si el apartamento esta amoblado
     */
    boolean isAmueblado();

    /**
     * @return El numero de habitaciones en el apartamento
     */
    int getHabitaciones();

    /**
     * @return La descripcion del apartamento
     */
    String getDescripcionMenaje();

    /**
     * @return La fecha de vencimiento del seguro del apartamento
     */
    int getTiene_Seguro();

    /**
     * @return La descripcion del seguro del apartamento
     */
    String getDescripcionSeguro();

    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos del apartamento
     */ String toString();

}

