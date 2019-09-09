package TDACola;
/**
 * Clase ArrayQueue.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class ArrayQueue<E> implements Queue<E>{
	private E [] q;
	private int b,e; //beginning,end
	private static final int longitud = 10;

	/**
	 * Crea una nueva cola vac�a.
	 */
	public ArrayQueue() {
		q = (E[]) new Object[longitud];
		b = 0;
		e = 0; 
	}
	
	@Override
	public int size() {
		return (q.length-b+e) % q.length;
	}
	
	@Override
	public boolean isEmpty() {
		return b == e;
	}

	@Override
	public E front() throws EmptyQueueException{
		if(b==e)
			throw new EmptyQueueException("Empty queue.");
		return q[b];
	}

	@Override
	public void enqueue(E elem) {
		if(q.length-1==size()) {
			E[] aux = copy(b);
			e = size();
			b = 0;
			q = aux;
		}
		q[e] = elem;
		e = (e+1)%q.length;
	}


	/*
	 * Crea un nuevo arreglo con el doble del tama�o del anterior
	 * y copia todo el contenido del mismo.
	 */
	private E[] copy(int n) {
		E[] aux = (E[]) new Object[2*q.length];
		for(int i=0;i<size();i++) {
			aux[i] = q[n];
			n = (n+1) % q.length;
		}
		return aux;
	}

	@Override
	public E dequeue() throws EmptyQueueException{
		if(isEmpty())
			throw new EmptyQueueException("Empty queue.");
		E aux = q[b];
		q[b] = null;
		b = (b+1) % q.length;
		return aux;
	}
}
