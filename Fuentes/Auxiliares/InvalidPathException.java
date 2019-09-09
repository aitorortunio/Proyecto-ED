package Auxiliares;
/**
 * Clase de error cuando el camino es inv�lido.
 * @author Aitor, Ortu�o Rossetto.
 */
public class InvalidPathException extends Exception {
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public InvalidPathException(String message) {
		super(message);
	}
}
