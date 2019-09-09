package TDALista;

import java.util.Iterator;
import java.util.NoSuchElementException;

import Auxiliares.InvalidPositionException;
import Auxiliares.Position;

/**
 * Clase ElementIterator gen�rica que implementa un iterador.
 * @author Aitor, Ortu�o Rossetto.
 * @param E tipo del dato almacenado en el iterador.
 */

public class ElementIterator<E> implements Iterator<E> {

	protected PositionList<E> list;
	protected Position<E> cursor;

	/**
	 * Constructor del iterador de una lista dada. 
	 * @param l Lista a iterar.
	 */
	public ElementIterator(PositionList<E> l) {
		try {
			list = l;
			if (list.isEmpty())
				cursor = null;
			else
				cursor = list.first();
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Consulta si tiene elemento siguiente.
	 * @return Verdadero si tiene siguiente, falso en caso contrario.
	 */
	public boolean hasNext() {
		return cursor != null;
	}

	/**
	 * Devuelve el elemento siguiente.
	 * @return Elemento siguiente.
	 * @throws NoSuchElementException si no tiene elemento siguiente.
	 */
	public E next() throws NoSuchElementException {
		E toReturn = null;
		try {
			if (cursor == null)
				throw new NoSuchElementException("No tiene siguiente");
			toReturn = cursor.element();
			cursor = (cursor == list.last()) ? null : list.next(cursor);
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
}
