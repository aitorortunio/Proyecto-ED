package Auxiliares;
/**
 * Clase gen�rica nodo. Hace referencia a una posici�n.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class Nodo<E> implements Position<E>{
	private E elemento;
	private Nodo<E> siguiente;
	
	/**
	 * Constructor crea un nuevo nodo vac�o.
	 */
	public Nodo() {
		elemento = null;
		siguiente = null;
	}
	
	/**
	 * Constructor crea un nuevo nodo con su elemento.
	 * @param e Elemento del nodo.
	 */
	public Nodo(E e) {
		elemento = e;
		siguiente = null;
	}
	
	/**
	 * Constructor crea un nuevo nodo con su elemento e indica el nodo siguiente. 
	 * @param e Elemento del nodo.
	 * @param sig Nodo siguiente.
	 */
	public Nodo(E e, Nodo<E> sig) {
		elemento = e;
		siguiente = sig;
	}
	
	
	/**
	 * Asigna el nodo siguiente al actual.
	 * @param sig Nodo siguiente.
	 */
	public void setSiguiente(Nodo<E> sig) {
		siguiente = sig;
	}

	/**
	 * Asigna un elemento al nodo.
	 * @param e Elemento a asignar.
	 */
	public void setElemento(E e) {
		elemento = e;
	}

	/**
	 * Devuelve el nodo siguiente al actual. 
	 * @return nodo siguiente al actual.
	 */
	public Nodo<E> getSiguiente() {
		return siguiente;
	}
	
	@Override
	public E element() {
		return elemento;
	}
}
