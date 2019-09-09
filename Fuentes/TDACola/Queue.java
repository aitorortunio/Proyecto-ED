package TDACola;

/**
 * Interface Queue.
 * @author Cátedra de Estructuras de Datos, Departamento de Cs. e Ing. de la Computación, UNS.
 */

public interface Queue<T> {
	
	/**
	 * Consulta la cantidad de elementos de la cola.
	 * @return Cantidad de elementos de la cola.
	 */
	public int size();
	
	/**
	 * Consulta si la cola está vacía.
	 * @return Verdadero si la cola está vacía, falso en caso contrario.
	 */
	public boolean isEmpty();
	
	/**
	 * Examina el elemento que se encuentra en el frente de la cola.
	 * @return Elemento que se encuentra en el frente de la cola.
	 * @throws EmptyQueueException si la cola está vacía. 
	 */
	public T front() throws EmptyQueueException;
	
	/**
	 * Inserta un elemento en el fondo de la cola.
	 * @param e Elemento a insertar.
	 */
	public void enqueue(T e);
	
	/**
	 * Remueve el elemento que se encuentra en el frente de la cola.
	 * @return Elemento removido.
	 * @throws EmptyQueueException si la cola está vacía.
	 */
	public T dequeue() throws EmptyQueueException;
}
