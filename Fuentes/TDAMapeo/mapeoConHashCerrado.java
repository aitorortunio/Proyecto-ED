package TDAMapeo;

import Auxiliares.*;
import TDALista.DoubleLinkedList;
import TDALista.PositionList;
/**
 * Clase mapeoConHashCerrado.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class mapeoConHashCerrado<K, V> implements Map<K, V> {

	private int size,N;
	private Entrada<K,V> []A;
	private Entrada<K,V> disponible;
	
	/**
	 * Inicializa un mapeo vac�o.
	 */
	@SuppressWarnings("unchecked")
	public mapeoConHashCerrado() {
		N = 17;
		size = 0;
		A = (Entrada<K,V>[]) new Entrada[N];
		disponible = new Entrada<K,V>(null,null);
		}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		int p = findEntry(key);
		V mValue = null;
		if(p>=0) 
		mValue = A[p].getValue();
		return mValue;	
	}
	
	/*
	 * Busca la entrada con clave igual a la pasada por par�metro.
	 */
	private int findEntry(K key) {
		int p = funcionHash(key);
		boolean found = false;
		int toReturn;
		int cont=1;
		while(A[p]!=null && !found && cont<=A.length){
			if(A[p]!=disponible && A[p].getKey().equals(key)) 
					found = true;
			else {
				p = (p+1) % N;
				cont++;
			}
		}
		toReturn = (A[p]!=null)?p:-1;
		return toReturn;
	}
	
	/*
	 * Controla que la clave no sea nula, en caso de serlo
	 * arroja una excepci�n de tipo InvalidKeyException.
	 */
	private void checkKey(K key)throws InvalidKeyException{
		if(key==null)
			throw new InvalidKeyException("La clave es nula.");
	}

	/*
	 * Devuelve un valor entre 0 y la longitud del arreglo como posici�n
	 * a partir de la clave pasada por par�metro.
	 */
	private int funcionHash(K k) {
		int i = Math.abs(k.hashCode());
		return (i % N);
	}
	
	/*
	 * Controla que el arreglo tenga menos del 90% de su capacidad ocupada.
	 * Si esto sucede devuelve verdadero, en caso contrario, falso.
	 */
	private boolean factorCarga() {
		float i = size/N;
		return i<0.9;
	}
	
	/*
	 * Crea una nuevo arreglo con el doble+1 del tama�o del arreglo anterior.
	 */
	@SuppressWarnings("unchecked")
	private void rehash() {
		N=N*2+1;
		Entrada<K,V> [] T = A;
		A =  (Entrada<K,V>[]) new Entrada[N];		
		for(int i=0 ; i<T.length ; i++) {
			if(T[i]!=null && T[i]!=disponible) {
				int p = findAvailableEntry(T[i].getKey());
				A[p]=T[i];
			}
		}
	}
	
	/*
	 * Busca una posici�n disponible en el arreglo donde insertar una nueva entrada con una clave pasada por par�metro.
	 */
	private int findAvailableEntry(K k) {
		int p = funcionHash(k);
		while(A[p]!=null && A[p]!=disponible && (!A[p].getKey().equals(k)) ) {
			p= (p+1) % N;
		}
		return p;
	}
	
	@Override
	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		if(!factorCarga())
			rehash();
		int p = findAvailableEntry(key);
		V mValue = null;
		if(p>=0) {
			if(A[p]!=null && A[p]!=disponible) {
				mValue = A[p].getValue();
				A[p].setValue(value);
			}
		else {
			size++;
			A[p] = new Entrada<K,V>(key,value);
			}
		}
		return mValue;
	}
	
	@Override
	public V remove(K key) throws InvalidKeyException{
		checkKey(key);
		V mValue=null;
		int p=funcionHash(key);
		int cont=1;
		while(cont<=A.length && A[p]!=null && mValue==null) {
			if(A[p]!=disponible && A[p].getKey().equals(key)) {
				mValue=A[p].getValue();
				A[p]=disponible;
				size--;
			}else {
				p=(p+1)%A.length;
			}	
			cont++;
		}
		return mValue;		
	}

	@Override
	public Iterable<K> keys(){
		PositionList<K> l=new DoubleLinkedList<K>();
		for(Entrada<K,V> e:A)
			if(e!=null && e!=disponible)
			    l.addLast(e.getKey());
		return l;
	}
	
	@Override
	public Iterable<V> values(){
		PositionList<V> l=new DoubleLinkedList<V>();
		for(Entrada<K,V> e:A)
			if(e!=null && e!=disponible)
			    l.addLast(e.getValue());
		return l;
	}
	
	@Override
	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>> l=new DoubleLinkedList<Entry<K,V>>();
		for(Entrada<K,V> e:A)
			if(e!=null && e!=disponible)
			    l.addLast(e);
		return l;
	}
}