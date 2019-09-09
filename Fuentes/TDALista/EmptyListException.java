package TDALista;
/**
 * Clase de error cuando la lista est� vac�a.
 * @author Aitor, Ortu�o Rossetto.
 */
public class EmptyListException extends Exception {
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public EmptyListException(String message) {
		super(message);
	}
}
