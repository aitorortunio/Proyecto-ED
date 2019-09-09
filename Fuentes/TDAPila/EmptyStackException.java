package TDAPila;
/**
 * Clase de error cuando la pila est� vac�a.
 * @author Aitor, Ortu�o Rossetto.
 */
public class EmptyStackException extends Exception{
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
