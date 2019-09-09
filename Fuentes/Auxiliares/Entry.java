package Auxiliares;
/**
 * Interface gen�rica Entry.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public interface Entry<K, V> {
	/**
	 * Devuelve la clave almacenada en la Entry.
	 * @return clave almacenada en la Entry.
	 */
	public K getKey();
	/**
	 * Devuelve el valor almacenado en la Entry.
	 * @return valor almacenado en la Entry.
	 */
	public V getValue();
}
