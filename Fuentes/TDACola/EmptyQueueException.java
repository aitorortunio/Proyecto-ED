package TDACola;
/**
 * Clase de error cuando la cola est� vac�a.
 * @author Aitor, Ortu�o Rossetto.
 */
public class EmptyQueueException extends Exception {
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public EmptyQueueException(String message) {
		super(message);
	}
}