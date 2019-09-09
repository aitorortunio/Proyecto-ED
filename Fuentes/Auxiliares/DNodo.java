package Auxiliares;
/**
 * Clase gen�rica nodo. Hace referencia a una posici�n.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class DNodo<E> implements Position<E> {

	private DNodo<E> prev, next;
	private E elem;

	/**
	 * Constructor crea un nuevo nodo asignandole un elemento y lig�ndolo al nodo
	 * previo y al siguiente.
	 * 
	 * @param p Nodo previo al creado.
	 * @param n Nodo siguiente al creado.
	 * @param e Elemento del nodo.
	 */
	public DNodo(DNodo<E> p, DNodo<E> n, E e) {
		prev = p;
		next = n;
		elem = e;
	}

	/**
	 * Constructor crea un nuevo nodo asignandole un elemento 
	 * @param e Elemento del nodo
	 */
	public DNodo(E e) {
		this(null, null, e);
	}
	
	@Override
	public E element() {
		return elem;
	}

	/**
	 * Retorna nodo previo a este.
	 * @return nodo previo al actual.
	 */
	public DNodo<E> getPrev() {
		return prev;
	}

	/**
	 * Retorna el nodo siguiente a este.
	 * @return nodo siguiente al actual.
	 */
	public DNodo<E> getNext() {
		return next;
	}

	/**
	 * Asigna un elemento al nodo.
	 * @param e Elemento a asignar.
	 */
	public void setElemento(E e) {
		elem = e;
	}

	/**
	 * Asigna el nodo siguiente al actual.
	 * @param n Nodo siguiente al actual.
	 */
	public void setNext(DNodo<E> n) {
		next = n;
	}

	/**
	 * Asigna el nodo anterior al actual.
	 * @param p Nodo anterior al actual.
	 */
	public void setPrev(DNodo<E> p) {
		prev = p;
	}
}
