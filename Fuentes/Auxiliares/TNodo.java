package Auxiliares;

import Auxiliares.Position;
import TDALista.DoubleLinkedList;
import TDALista.PositionList;
/**
 * Clase gen�rica TNodo. Hace referencia a una posici�n.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class TNodo<E> implements Position<E> {
	
	private E elem;
	private TNodo<E> padre;
	private PositionList<TNodo<E>> hijos;
	
	/**
	 * Constructor crea un nuevo nodo con su elemento e indica su padre.
	 * 
	 * @param e Elemento del nodo.
	 * @param p Padre del nodo
	 */
	public TNodo(E e,TNodo<E> p) {
		elem = e;
		padre = p;
		hijos= new DoubleLinkedList<TNodo<E>>();
	}
	
	/**
	 * Constructor crea un nuevo nodo con su elemento.
	 * 
	 * @param e Elemento del nodo.
	 */
	public TNodo(E e) {
		this(e,null);
	}
	
	@Override
	public E element() {
		return elem;
	}

	/**
	 * Devuelve una lista de los hijos del nodo actual.
	 * @return Lista de hijos del nodo actual.
	 */
	public PositionList<TNodo<E>> getHijos() {
		return hijos;
	}

	/**
	 * Devuelve una lista de los hijos del nodo actual.
	 * @param h Lista de hijos del nodo actual.
	 */
	public void setHijos(PositionList<TNodo<E>> h) {
		hijos = h;
	}

	/**
	 * Setea elemento del nodo actual.
	 * @param e elemento del nodo actual.
	 */
	public void setElem(E e) {
		elem = e;
	}

	/**
	 * Devuelve el padre del nodo actual.
	 * @return nodo padre del nodo actual.
	 */
	public TNodo<E> getPadre() {
		return padre;
	}

	/**
	 * Setea padre del nodo actual.
	 * @param p Padre del nodo actual.
	 */
	public void setPadre(TNodo<E> p) {
		padre = p;
	}
}
