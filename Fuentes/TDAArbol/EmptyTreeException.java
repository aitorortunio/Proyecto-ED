package TDAArbol;
/**
 * Clase de error cuando el arbol est� vac�o.
 * @author Aitor, Ortu�o Rossetto.
 */
public class EmptyTreeException extends Exception {
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public EmptyTreeException(String message) {
		super(message);
	}
}