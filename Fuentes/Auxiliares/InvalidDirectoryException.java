package Auxiliares;
/**
 * Clase de error cuando la ruta del directorio es inv�lida.
 * @author Aitor, Ortu�o Rossetto.
 */
public class InvalidDirectoryException extends Exception {
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param msg Mensaje a lanzar por el error
	 */
	public InvalidDirectoryException(String msg) {
		super (msg);
	}

}
