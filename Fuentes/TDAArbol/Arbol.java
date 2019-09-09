package TDAArbol;

import java.util.Iterator;

import Auxiliares.TNodo;
import TDALista.BoundaryViolationException;
import TDALista.DoubleLinkedList;
import TDALista.EmptyListException;
import TDALista.PositionList;
import Auxiliares.InvalidPositionException;
import Auxiliares.Position;
/**
 * Clase �rbol.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class Arbol<E> implements Tree<E> {

	private TNodo<E> raiz;
	private int size;

	/**
	 * Crea un nuevo �rbol vac�o.
	 */
	public Arbol() {
		raiz = null;
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<E> iterator() {
		PositionList<E> lista = new DoubleLinkedList<E>();
		if(!isEmpty()) {
			preOrder(lista,raiz);
		}
		return lista.iterator();
	}

	/*
	 * M�todo auxiliar para recorrer el �rbol en preorden y devolver un iterador.
	 */
	private void preOrder(PositionList<E> lista, TNodo<E> r) {
		lista.addLast(r.element());
		for (TNodo<E> h : r.getHijos())
			preOrder(lista, h);
	}
	
	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> lista = new DoubleLinkedList<Position<E>>();
		if (!isEmpty())
			pre(lista, raiz);
		return lista;
	}

	/*
	 * M�todo auxiliar para recorrer el �rbol en preorden y devolver una lista con todas las posiciones del mismo.
	 */
	private void pre(PositionList<Position<E>> lista, TNodo<E> r) {
		lista.addLast(r);
		for (TNodo<E> h : r.getHijos())
			pre(lista, h);
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TNodo<E> nodo = checkposition(v);
		E aux = nodo.element();
		nodo.setElem(e);
		return aux;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if (isEmpty())
			throw new EmptyTreeException("Arbol vac�o.");
		return raiz;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TNodo<E> nodo = checkposition(v);
		TNodo<E> padre = nodo.getPadre();
		if (padre == null)
			throw new BoundaryViolationException("No tiene padre.");
		return padre;
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkposition(v);
		PositionList<Position<E>> lista = new DoubleLinkedList<Position<E>>();
		for (TNodo<E> e : nodo.getHijos())
			lista.addLast(e);
		return lista;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkposition(v);
		return !nodo.getHijos().isEmpty();
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkposition(v);
		return nodo.getHijos().isEmpty();
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkposition(v);
		return nodo.getPadre() == null;
	}

	@Override
	public void createRoot(E e) throws InvalidOperationException {
		if (isEmpty()) {
			raiz = new TNodo<E>(e);
			size = 1;
		} else
			throw new InvalidOperationException("El �rbol ya tiene ra�z.");
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		TNodo<E> padre = checkposition(p);
		TNodo<E> hijo = new TNodo<E>(e, padre);
		padre.getHijos().addFirst(hijo);
		size++;
		return hijo;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		TNodo<E> padre = checkposition(p);
		TNodo<E> hijo = new TNodo<E>(e, padre);
		padre.getHijos().addLast(hijo);
		size++;
		return hijo;
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		TNodo<E> padre = checkposition(p);
		TNodo<E> hermanoD = checkposition(rb);
		TNodo<E> nuevo = new TNodo<E>(e, padre);
		PositionList<TNodo<E>> listaHijos = padre.getHijos();
		if (listaHijos.isEmpty())
			throw new InvalidPositionException("No es el padre.");
		try {
			Position<TNodo<E>> pos = listaHijos.first();
			boolean encontre = false;
			while (pos != null && !encontre) {
				if (pos.element() != hermanoD) {
					if (pos != listaHijos.last())
						pos = listaHijos.next(pos);
					else
						pos = null;
				} else
					encontre = true;
			}
			if (!encontre)
				throw new InvalidPositionException("No es el hijo.");
			else {
				listaHijos.addBefore(pos, nuevo);
				size++;
			}
		} catch (EmptyListException | BoundaryViolationException ex) {
			System.out.println(ex.getMessage());
		}
		return nuevo;
	}

	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		TNodo<E> padre = checkposition(p);
		TNodo<E> hermanoI = checkposition(lb);
		TNodo<E> nuevo = new TNodo<E>(e, padre);
		PositionList<TNodo<E>> listaHijos = padre.getHijos();
		if (listaHijos.isEmpty())
			throw new InvalidPositionException("No es el padre.");
		try {
			Position<TNodo<E>> pos = listaHijos.first();
			boolean encontre = false;
			while (pos != null && !encontre) {
				if (pos.element() != hermanoI) {
					if (pos != listaHijos.last())
						pos = listaHijos.next(pos);
					else
						pos = null;
				} else
					encontre = true;
			}
			if (!encontre)
				throw new InvalidPositionException("No es el hijo.");
			else {
				listaHijos.addAfter(pos, nuevo);
				size++;
			}
		} catch (EmptyListException | BoundaryViolationException ex) {
			System.out.println(ex.getMessage());
		}
		return nuevo;
	}

	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		TNodo<E> n= checkposition(p);
		if(!n.getHijos().isEmpty())
			throw new InvalidPositionException("No es un nodo externo");
		removeNode(n);
		}

	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		TNodo<E> n= checkposition(p);
		if(n.getHijos().isEmpty())
			throw new InvalidPositionException("No es un nodo interno");
		removeNode(n);
	}

	@Override
	public void removeNode(Position<E> p) throws InvalidPositionException {
		TNodo<E> nEliminar = checkposition(p);
		TNodo<E> padre = nEliminar.getPadre();
		PositionList<TNodo<E>> hijos = nEliminar.getHijos();
		try {
			if (nEliminar == raiz) {
				if (hijos.size() == 0) {
					raiz = null;
				} else {
					if (hijos.size() == 1) {
						TNodo<E> hijo = hijos.remove(hijos.first());
						hijo.setPadre(null);
						raiz = hijo;
					} else
						throw new InvalidPositionException("No se puede eliminar ra�z con hijos > 1");
				}
			} else {
				PositionList<TNodo<E>> hermanos = padre.getHijos();
				Position<TNodo<E>> posListaHermanos = hermanos.isEmpty() ? null : hermanos.first();
				while (posListaHermanos != null && posListaHermanos.element() != nEliminar) {
					posListaHermanos = (hermanos.last() == posListaHermanos) ? null : hermanos.next(posListaHermanos);
				}
				if (posListaHermanos == null)
					throw new InvalidPositionException("La posici�n p no se encuentra en la lista del padre");
				while (!hijos.isEmpty()) {
					TNodo<E> hijo = hijos.remove(hijos.first());
					hijo.setPadre(padre);
					hermanos.addBefore(posListaHermanos, hijo);
				}
				hermanos.remove(posListaHermanos);
			}
			nEliminar.setPadre(null);
			nEliminar.setElem(null);
			size--;
		} catch (EmptyListException | BoundaryViolationException e) {
		}
	}

	/*
	 * Controla que la posici�n no sea nula ni el �rbol est� vacio.
	 * En caso de que esto suceda arroja una excepci�n de tipo InvalidPositionException.
	 * Si la posici�n es v�lida entonces la devuelve casteada a un TNodo. 
	 */
		private TNodo<E> checkposition(Position<E> p) throws InvalidPositionException {
			try {
				if (p == null || isEmpty())
					throw new InvalidPositionException("Posici�n nula.");
				return (TNodo<E>) p;
			} catch (ClassCastException e) {
				throw new InvalidPositionException("La posici�n es de un tipo incorrecto.");
			}
		}
	}
