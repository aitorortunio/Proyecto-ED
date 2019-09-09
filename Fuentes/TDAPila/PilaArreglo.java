package TDAPila;
/**
 * Clase PilaArreglo
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class PilaArreglo<E> implements Stack<E> {

	private E[] array;
	private int size;

	/**
	 * Crea una nueva pila vac�a.
	 */
	public PilaArreglo() {
		array = (E[]) new Object[4];
		size = 0;
	}

	@Override
	public void push(E e) {
		if(size==array.length) {
			E[] aux = (E[]) new Object[array.length*2];
			for(int i=0; i<array.length; i++)
				aux[i] = array[i];
			aux[size++] = e;
			array = aux;
		}
		else
			array[size++] = e;
	}

	@Override
	public E pop() throws EmptyStackException {
		if(size == 0)
			throw new EmptyStackException("Pila vac�a.");
		E aux = array[size-1];
		array[size-1] = null;
		size--;
		return aux;
	}

	@Override
	public E top() throws EmptyStackException {
		if(size == 0)
			throw new EmptyStackException("Pila vac�a.");
		return array[size-1];
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public int size() {
		return size;
	}
}
