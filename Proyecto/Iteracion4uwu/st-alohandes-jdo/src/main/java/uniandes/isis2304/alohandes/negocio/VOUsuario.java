package uniandes.isis2304.alohandes.negocio;

/**
 * Interfaz para los métodos get de usuario.
 */
public interface VOUsuario {


    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * @return log in
     */
    String getLogIn();


    /**
     * @return tipo de id
     */
    String getTipoId();

    /**
     * @return numero de id.
     */
    long getNumeroId();


    /**
     * @return relacionU.
     */
    String getRelacioU();


}
