package Auxiliares;
/**
 * Clase de error cuando la clave es inv�lida.
 * @author Aitor, Ortu�o Rossetto.
 */
public class InvalidKeyException extends Exception {
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public InvalidKeyException(String message) {
		super(message);
	}
}
