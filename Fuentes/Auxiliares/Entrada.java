package Auxiliares;
/**
 * Clase gen�rica Entrada. Hace referencia a una Entry.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class Entrada<K, V> implements Entry<K,V>{
	private K key;
	private V value;
	
	/**
	 * Crea una nueva con clave y valor pasados por par�metro.
	 * @param k Clave a asignar.
	 * @param v Valor a asignar.
	 */
	public Entrada(K k, V v) {
		key = k;
		value = v;
	}
	
	@Override
	public K getKey() {
		return key;
	}
	
	@Override
	public V getValue() {
		return value;
	}
	/**
	 * Setea el valor a almacenar en la Entry.
	 * @param k Clave a asignar.
	 */
	public void setKey(K k) {
		key = k;
	}
	/**
	 * Setea el valor a almacenar en la Entry.
	 * @param v Valor a asignar.
	 */
	public void setValue(V v) {
		value = v;
	}
	/**
	 * Devuelve el valor y la clave almacenados en la Entry.
	 * @return clave y valor almacenados en la Entry.
	 */
	public String toString() {
		return "("+getKey()+","+getValue()+")";
	}
}
