package TDAArbol;
/**
 * Clase de error cuando la operaci�n es invalida.
 * @author Aitor, Ortu�o Rossetto.
 */
public class InvalidOperationException extends Exception {
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public InvalidOperationException(String message) {
		super(message);
	}
}