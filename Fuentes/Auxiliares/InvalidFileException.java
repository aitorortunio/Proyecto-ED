package Auxiliares;
/**
 * Clase de error cuando el archivo es inv�lido.
 * @author Aitor, Ortu�o Rossetto.
 */
public class InvalidFileException extends Exception {
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public InvalidFileException(String message) {
		super(message);
	}
}
