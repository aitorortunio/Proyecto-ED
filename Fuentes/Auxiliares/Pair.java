package Auxiliares;
/**
 * Clase gen�rica Pair.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class Pair<K, V> {
	private K key;
	private V value;
	
	/**
	 * Constructor crea un nuevo par con sus elementos.
	 * @param k clave del par.
	 * @param v valor del par.
	 */
	public Pair(K k, V v) {
		key = k;
		value = v;
	}
	
	/**
	 * Devuelve la clave almacenada en el par.
	 * @return clave almacenada en el par.
	 */
	public K getKey() {
		return key;
	}
	
	/**
	 * Devuelve el valor almacenado en el par.
	 * @return valor almacenado en el par.
	 */
	public V getValue() {
		return value;
	}
	/**
	 * Setea la clave a almacenar en el par.
	 * @param k Clave a asignar.
	 */
	public void setKey(K k) {
		key = k;
	}
	
	/**
	 * Setea el valor a almacenar en el par.
	 * @param v Valor e a asignar.
	 */
	public void setValue(V v) {
		value = v;
	}
	
	/**
	 * Devuelve el valor y la clave almacenados en el par.
	 * @return clave y valor almacenados en el par.
	 */
	public String toString() {
		return "("+getKey()+","+getValue()+")";
	}
}
