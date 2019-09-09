package TDALista;

import java.util.Iterator;

import Auxiliares.DNodo;
import Auxiliares.InvalidPositionException;
import Auxiliares.Position;
/**
 * Clase DoubleLinkedList 
 * @author Aitor, Ortu�o Rossetto.
 */
public class DoubleLinkedList<E> implements PositionList<E> {

	protected int size;
	protected DNodo<E> header, trailer;
	
	/**
	 * Inicializa una lista vac�a.
	 */
	public DoubleLinkedList() {
		size = 0;
		header = new DNodo<E>(null, null, null);
		trailer = new DNodo<E>(header, null, null);
		header.setNext(trailer);
	}

	/*
	 * Controla que no se pase una posici�n nula, inv�lida o de otro tipo. 
	 */
	private DNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		if (p == null || p == header || p == trailer)
			throw new InvalidPositionException("Se ha pasado una posicion nula");
		try {
			DNodo<E> temp = (DNodo<E>) p;
			if ((temp.getPrev() == null) || (temp.getNext() == null))
				throw new InvalidPositionException("La posicion no pertenece a una lista valida");
			return temp;
		} catch (ClassCastException e) {
			throw new InvalidPositionException("La posicion es de un tipo incorrecto para esta lista");
		}
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public Position<E> first() throws EmptyListException {
		if (isEmpty())
			throw new EmptyListException("La lista esta vacia");
		return header.getNext();
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> v = checkPosition(p);
		DNodo<E> prev = v.getPrev();
		if (prev == header)
			throw new BoundaryViolationException("No se puede retornar el elemento anterior al primero");
		return prev;
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		DNodo<E> v = checkPosition(p);
		size++;
		DNodo<E> newNode = new DNodo<E>(v.getPrev(), v, element);
		v.getPrev().setNext(newNode);
		v.setPrev(newNode);
	}

	@Override
	public void addFirst(E element) {
		size++;
		DNodo<E> newNode = new DNodo<E>(header, header.getNext(), element);
		header.getNext().setPrev(newNode);
		header.setNext(newNode);
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		DNodo<E> v = checkPosition(p);
		size--;
		DNodo<E> vPrev = v.getPrev();
		DNodo<E> vNext = v.getNext();
		vPrev.setNext(vNext);
		vNext.setPrev(vPrev);
		E vElem = v.element();
		v.setNext(null);
		v.setPrev(null);
		v.setElemento(null);
		return vElem;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		DNodo<E> v = checkPosition(p);
		E oldElt = v.element();
		v.setElemento(element);

		return oldElt;
	}

	@Override
	public void addLast(E e) {
		DNodo<E> newNode = new DNodo<E>(trailer.getPrev(), trailer, e);
		size++;
		trailer.getPrev().setNext(newNode);
		trailer.setPrev(newNode);

	}

	@Override
	public Position<E> last() throws EmptyListException {
		if (isEmpty())
			throw new EmptyListException("La lista esta vacia");
		return trailer.getPrev();
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> v = checkPosition(p);
		DNodo<E> next = v.getNext();
		if (next == trailer)
			throw new BoundaryViolationException("No se puede retornar la posicion siguiente a la ultima");

		return next;
	}

	@Override
	public void addAfter(Position<E> p, E e) throws InvalidPositionException {
		DNodo<E> v = checkPosition(p);
		size++;
		DNodo<E> newNode = new DNodo<E>(v, v.getNext(), e);
		v.getNext().setPrev(newNode);
		v.setNext(newNode);
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> LP = new DoubleLinkedList<Position<E>>();
		if (!isEmpty()) {
			try {
				Position<E> pos = first();
				boolean seguir = true;
				while (seguir) {
					LP.addLast(pos);
					if (pos == last())
						seguir = false;
					else
						pos = next(pos);
				}
			} catch (EmptyListException | BoundaryViolationException | InvalidPositionException e) {
			}
		}
		return LP;
	}

}
