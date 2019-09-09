package Auxiliares;
/**
 * Clase de error cuando la posicion es inv�lida.
 * @author Aitor, Ortu�o Rossetto.
 */
public class InvalidPositionException extends Exception{
	/**
	 * Crea un error que lanza el mensaje correspondiente
	 * @param message Mensaje a lanzar por el error
	 */
	public InvalidPositionException(String message){
		super(message);
	}
}