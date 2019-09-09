package TDADiccionario;

import java.util.Iterator;

import Auxiliares.Entry;
import Auxiliares.InvalidKeyException;
import Auxiliares.Entrada;
import Auxiliares.InvalidPositionException;
import Auxiliares.Position;
import TDALista.BoundaryViolationException;
import TDALista.DoubleLinkedList;
import TDALista.EmptyListException;
import TDALista.PositionList;

/**
 * Clase DiccionarioConHashAbierto.
 *
 * @author Aitor, Ortu�o Rossetto.
 */
public class DiccionarioConHashAbierto<K, V> implements Dictionary<K, V> {
	protected PositionList<Entrada<K, V>>[] D;
	protected int size;
	protected int N;
	protected final float factor = 0.9F;

	/**
	 * Crea un nuevo diccionario vac�o.
	 */
	public DiccionarioConHashAbierto() {
		N=11;
		D = (PositionList<Entrada<K, V>>[]) new DoubleLinkedList[N];
		for (int i = 0; i < D.length; i++)
			D[i] = new DoubleLinkedList<Entrada<K, V>>();
		size = 0;
	}

	/*
	 * Devuelve un valor entre 0 y la longitud del arreglo como posici�n
	 * a partir de la clave pasada por par�metro.
	 */
	private int funcionHash(K key) {
		return (Math.abs(key.hashCode())) % N;
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
	public Entry<K, V> find(K k) throws InvalidKeyException{
		if(k==null)
			throw new InvalidKeyException("");
		Entry<K,V> toret=null;
		if(size>0) {
			PositionList<Entrada<K,V>> l=D[funcionHash(k)];
			Iterator<Entrada<K,V>> it=l.iterator();
			Entrada<K,V> cur=null;
			while(it.hasNext() && toret==null) {
				cur=it.next();
				if(cur.getKey().equals(k))
					toret=cur;			
			}
		}
		return toret;
	}
	
	@Override
	public Entry<K,V> insert(K k,V v) throws InvalidKeyException{
		if(k==null)
			throw new InvalidKeyException("La clave es inv�lida.");
		if((size/D.length) >= factor)
			rehashing();
		int clave= funcionHash(k);
		Entrada<K,V> toret=new Entrada<K,V>(k,v);
		D[clave].addLast(toret);
		size++;
		return toret;
	}
	
	/*
	 * Crea una nuevo arreglo con el doble+1 del tama�o del arreglo anterior.
	 */
	private void rehashing() {
		N= N*2 +1;
		PositionList<Entrada<K,V>> [] A = D;
		D =  (PositionList<Entrada<K,V>>[]) new PositionList[N];
		for(int i=0; i<N ; i++)
			D[i]= new DoubleLinkedList<Entrada<K,V>>();
		
		for(int i=0 ; i<A.length ; i++)
			for(Entrada<K,V> e : A[i]) {
				int p = funcionHash(e.getKey());
				D[p].addLast(e);
			}
	}	
	
	@Override
	public Entry<K,V> remove(Entry<K,V> e)throws InvalidEntryException{
		if(e==null)
			throw new InvalidEntryException("La entrada es Inv�lida.");
		int p = funcionHash(e.getKey());
		Entrada<K,V> toReturn = null;
		try {
		Position<Entrada<K,V>> pos = !D[p].isEmpty()?	D[p].first():	null;
		boolean found = false;
		while(pos!=null && !found) {
			if(pos.element()==e) {
				found = true;
				toReturn = pos.element();
				D[p].remove(pos);
				size--;
			}else {
				pos = (pos!=D[p].last())?	D[p].next(pos):		null;
			}
		}
		}catch(EmptyListException | InvalidPositionException | BoundaryViolationException ex) {
			System.out.println("");
		}
		if(toReturn==null)
			throw new InvalidEntryException("La entrada es inv�lida.");
		return toReturn;		
	}
	
	@Override
	public Iterable<Entry<K,V>> findAll(K k)throws InvalidKeyException{
		if(k==null)
			throw new InvalidKeyException("");
		PositionList<Entry<K,V>> toret=new DoubleLinkedList<Entry<K,V>>();
		for(Entrada<K,V> p:D[funcionHash(k)]) {
			if(p.getKey().equals(k))
				toret.addLast(p);
		}
		
		return toret;	
	}
	
	@Override
	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>> toret=new DoubleLinkedList<Entry<K,V>>();
		for(int i=0; i<D.length;i++){
			for(Entrada<K,V> e:D[i]) {
				toret.addLast(e);
			}		
		}
		return toret;
	}
}
		