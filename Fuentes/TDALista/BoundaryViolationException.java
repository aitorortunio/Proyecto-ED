package TDALista;
/**
 * Clase de error cuando se exceden los l�mites.
 * @author Aitor, Ortu�o Rossetto.
 */
public class BoundaryViolationException extends Exception {
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public BoundaryViolationException(String message) {
		super(message);
	}
}
