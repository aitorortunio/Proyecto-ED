package TDACola;

/**
 * Interface Queue.
 * @author C�tedra de Estructuras de Datos, Departamento de Cs. e Ing. de la Computaci�n, UNS.
 */

public interface Queue<T> {
	
	/**
	 * Consulta la cantidad de elementos de la cola.
	 * @return Cantidad de elementos de la cola.
	 */
	public int size();
	
	/**
	 * Consulta si la cola est� vac�a.
	 * @return Verdadero si la cola est� vac�a, falso en caso contrario.
	 */
	public boolean isEmpty();
	
	/**
	 * Examina el elemento que se encuentra en el frente de la cola.
	 * @return Elemento que se encuentra en el frente de la cola.
	 * @throws EmptyQueueException si la cola est� vac�a. 
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
	 * @throws EmptyQueueException si la cola est� vac�a.
	 */
	public T dequeue() throws EmptyQueueException;
}
