package TDADiccionario;
/**
 * Clase de error cuando la entrada es inv�lida.
 * @author Aitor Ortu�o
 */
public class InvalidEntryException extends Exception {
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public InvalidEntryException(String message) {
		super(message);
	}
}
