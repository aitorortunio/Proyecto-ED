package Controlador;

import java.util.regex.Pattern;

import Auxiliares.InvalidDirectoryException;
import Auxiliares.InvalidFileException;
import Auxiliares.InvalidKeyException;
import Auxiliares.InvalidPositionException;
import Auxiliares.Pair;
import Auxiliares.Position;
import TDAArbol.Arbol;
import TDAArbol.EmptyTreeException;
import TDAArbol.InvalidOperationException;
import TDAArbol.Tree;
import TDACola.ArrayQueue;
import TDACola.EmptyQueueException;
import TDACola.Queue;
import TDADiccionario.DiccionarioConHashAbierto;
import TDADiccionario.Dictionary;
import TDALista.BoundaryViolationException;
import TDALista.DoubleLinkedList;
import TDALista.PositionList;
import TDAMapeo.Map;
import TDAMapeo.mapeoConHashCerrado;
import TDAPila.EmptyStackException;
import TDAPila.PilaArreglo;
import TDAPila.Stack;

import java.io.*;
import java.util.Iterator;

/**
 * Clase L�gica Modela un sistema de archivos virtual y operaciones para
 * manejarlo.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class Logica {
	protected Tree<Pair<String,PositionList<String>>> arbol;
	protected ArrayQueue<String> ParaGenerar;
	protected ArrayQueue<String> toValidate;
	
	/**
	 * Constructor de la clase Logica. 
	 */
	public Logica() {
		arbol = new Arbol<Pair<String,PositionList<String>>>();
		toValidate = new ArrayQueue<String>();
		ParaGenerar = new ArrayQueue<String>();
	}

	/**
	 * Valida y parsea el contenido de un archivo creando una jerarquia de tipo arbol del sistema de archivos virtual.
	 * @param ruta Ruta a un archivo de texto con extension ".ed19".
	 * @throws InvalidFileException Si la ruta al archivo pasada por parametro no existe, o no puede ser le�do el archivo, o el formato del archivo no es valido.
	 */
	public void generarJerarquia(String ruta) throws InvalidFileException {
		try {
			
			FileReader archivo = new FileReader(ruta);
			BufferedReader toRead = new BufferedReader(archivo);
			String s;
			String todo = "";
			while ((s = toRead.readLine()) != null) {
				todo = todo + s.replaceAll("\\s", "").replaceAll("<", "#<").replaceAll(">", ">#");
			}
			todo = todo.replaceAll("##", "#");
			String separador = Pattern.quote("#");
			String [] linea = todo.split(separador);
			
			for (int i = 1; i < linea.length; i++) {
				toValidate.enqueue(linea[i]);
				if (!linea[i].equals("</carpeta>") && !linea[i].equals("</nombre>") && !linea[i].equals("</archivo>")) {
					ParaGenerar.enqueue(linea[i]);
				}
			}			
			toRead.close();	
		} catch (FileNotFoundException e) {
			throw new InvalidFileException("El archivo no existe");
		} catch (IOException e) {
			throw new InvalidFileException("Un error ha ocurrido en la lectura del archivo");
		}
		if (validarJerarquia()) {
			generarArbol();
		} else {
			throw new InvalidFileException("El archivo no cumple con el formato esperado");
		}
		
	}
	
	/**
	 * Determina si el formato del archivo es el correcto.
	 * @return Verdadero si el formato es valido. Falso en caso contrario.
	 */
	private boolean validarJerarquia() {
		Stack<String> pila = new PilaArreglo<String>();
		boolean cumple = false;	
		try {
			String indice = (!toValidate.isEmpty()) ? toValidate.dequeue() : null;
			cumple = !toValidate.isEmpty() && indice.equals("<carpeta>");			
			while (cumple && indice != null) {
				switch (indice) {
				case ("<carpeta>") :
					pila.push(indice);
					cumple = toValidate.size()>3 && (toValidate.dequeue().equals("<nombre>") && (toValidate.dequeue().charAt(0) != '<') && (toValidate.dequeue().equals("</nombre>")));
					if(cumple) {
						indice = toValidate.dequeue();
					}
					cumple = cumple && indice.equals("</carpeta>") || indice.equals("<lista_sub_carpetas>") || indice.equals("<archivo>");
					break;			
				case ("</carpeta>") :
					if (pila.pop().equals("<carpeta>")) {
						indice = (!toValidate.isEmpty()) ? toValidate.dequeue() : null;						
						if (indice != null) {
							cumple = cumple && indice.equals("<carpeta>") || indice.equals("</lista_sub_carpetas>");
						}
					} else {
						cumple = false;
					}
					break;			
				case ("<lista_sub_carpetas>") :
					pila.push(indice);
					if(!toValidate.isEmpty()) {
						indice = toValidate.dequeue();
					} else {
						cumple = false;
					}
					break;					
				case ("</lista_sub_carpetas>") :
					if(pila.pop().equals("<lista_sub_carpetas>") && !toValidate.isEmpty() && cumple) {
						indice = toValidate.dequeue();
						cumple= indice.equals("</carpeta>");
					} else {
						cumple = false;
					}
					break;				
				case ("<archivo>") :
					if(toValidate.size()>2 && indice.equals("<archivo>") && (toValidate.dequeue().charAt(0) != '<') && toValidate.dequeue().equals("</archivo>")) {
						indice = toValidate.dequeue();
						cumple = cumple && indice.equals("</carpeta>") || indice.equals("<lista_sub_carpetas>") || indice.equals("<archivo>");
					} else {
						cumple = false;
					}
					break;		
				default: 
					cumple = false;
				}
			}
			if(cumple && pila.isEmpty()) {
				cumple = true;
			} else {
				cumple = false;
			}			
		} catch (EmptyQueueException | EmptyStackException e) {
		}
		return cumple;
	}
	
	/**
	 * Inicializa y construye el arbol, con la cola ParaGenerar.
	 */
	private void generarArbol() {
		try {
			ParaGenerar.dequeue();//Tag Carpeta
			ParaGenerar.dequeue();//Tag Nombre
			
			//Carpeta Principal (root del arbol)
			String nombreCarpetaPrincipal = ParaGenerar.dequeue();//Nombre de la carpeta principal
			PositionList<String> archivosCarpetaPrincipal = new DoubleLinkedList<String>(); 
			Pair<String, PositionList<String>> carpetaPrincipal = new Pair<String, PositionList<String>>(nombreCarpetaPrincipal, archivosCarpetaPrincipal) ;
			arbol.createRoot(carpetaPrincipal);
				
			while (!ParaGenerar.isEmpty() && ParaGenerar.front().equals("<archivo>")) {
				ParaGenerar.dequeue();
				arbol.root().element().getValue().addLast(ParaGenerar.dequeue());
			}		
			if (!ParaGenerar.isEmpty() && ParaGenerar.front().equals("<lista_sub_carpetas>")) {
				ParaGenerar.dequeue();
				insertarSubCarpetas(arbol.root());
			}
			
		} catch (EmptyQueueException | InvalidOperationException | EmptyTreeException e) {
		}	
	}
	
	/**
	 * Inserta a la posicion pasada por parametros los hijos correspondientes (basandose en la cola toGenerate).
	 * @param padre Posicion del arbol (directorio) a la que se le insertaran hijos (sub directorios)
	 */
	private void insertarSubCarpetas(Position<Pair<String, PositionList<String>>> padre) {
		try {	
			
			while (!ParaGenerar.isEmpty() && !ParaGenerar.front().equals("</lista_sub_carpetas>")) {
				ParaGenerar.dequeue();//Tag Carpeta
				ParaGenerar.dequeue();//Tag Nombre
				
				Position<Pair<String,PositionList<String>>> posHijo;
				String nombreHijo = ParaGenerar.dequeue();
				Pair<String, PositionList<String>> hijo = new Pair<String, PositionList<String>>(nombreHijo, new DoubleLinkedList<String>());
				posHijo = arbol.addLastChild(padre, hijo);

				while (!ParaGenerar.isEmpty() && ParaGenerar.front().equals("<archivo>")) {
					ParaGenerar.dequeue();
					posHijo.element().getValue().addLast(ParaGenerar.dequeue());
				}
				
				if (!ParaGenerar.isEmpty() && ParaGenerar.front().equals("<lista_sub_carpetas>")) {
					ParaGenerar.dequeue();
					insertarSubCarpetas(posHijo);
				}
			}
			ParaGenerar.dequeue();		
		} catch (EmptyQueueException | InvalidPositionException e) {
		}	 
	}

	/**
	 * Agrega el archivo al directorio pasado por parametro. En caso de que el
	 * directorio no exista, la jerarquia no se modifica.
	 * 
	 * @param ruta La ruta completa dentro del sistema virtual de archivos de un directorio.
	 * @param nombre El nombre del archivo.
	 * @throws InvalidFileException si la ruta al directorio destino no es valida.
	 */
	public void agregarArchivo(String ruta, String nombre) throws InvalidFileException {
		Position<Pair<String, PositionList<String>>> p = checkRuta(ruta);
		if (p == null || arbol.isEmpty()) {
			throw new InvalidFileException("Error");
		} else {
			p.element().getValue().addLast(nombre);
		}
	}

	/**
	 * Elimina el archivo indicado por la ruta pasada por parametro. Si el archivo no existe, la jerarquia no se modifica.
	 * 
	 * @param ruta La ruta completa dentro del sistema virtual de archivos de un archivo a eliminar.
	 * @throws InvalidFileException si la ruta completa al archivo no es valida.
	 */
	public void eliminarArchivo(String ruta) throws InvalidFileException {
		String aux = "";
		String separador = Pattern.quote("\\");
		String[] partes = ruta.split(separador);
		if (partes.length < 2) 
			throw new InvalidFileException("error");
		String ArchivoA = partes[partes.length - 1];
		for (int i = 0; i < partes.length - 1; i++) {
			aux = aux + partes[i] + "\\";
		}
		Position<Pair<String, PositionList<String>>> p = checkRuta(aux);
		try {
			if (p != null) {
				PositionList<String> CarpetaA = p.element().getValue();
				Iterable<Position<String>> it2 = CarpetaA.positions();
				for (Position<String> b : it2) {
					if (b.element().equals(ArchivoA)) {
						CarpetaA.remove(b);
					}
				}
			} else {
				throw new InvalidFileException("error");
			}
		} catch (InvalidPositionException e1) {
		}
	}

	/**
	 * Genera un nuevo directorio, insertado dentro de la ruta pasada por parametro.
	 * En caso de que el directorio pasado por parametro no exista, la jerarquia no
	 * se modifica
	 * 
	 * @param ruta La ruta completa dentro del sistema virtual de archivos de un directorio.
	 * @param nombre El nombre de un nuevo directorio a generar.
	 * @throws InvalidDirectoryException si la ruta al directorio destino no es valida.
	 */
	public void agregarDirectorio(String ruta, String nombre) throws InvalidDirectoryException {
		try {
			Position<Pair<String, PositionList<String>>> p = checkRuta(ruta);
			if (p != null) {
				arbol.addLastChild(p, new Pair<String, PositionList<String>>(nombre, new DoubleLinkedList<String>()));

			} else {
				throw new InvalidDirectoryException("error");
			}
		} catch (InvalidPositionException e) {
		}
	}

	/**
	 * Elimina el directorio, pasado por parametro, junto con todo archivo y
	 * sub-directorio con o sin archivos que contenga
	 *
	 * @param ruta La ruta completa dentro del sistema virtual de archivos de un
	 *             directorio a eliminar
	 * @throws InvalidDirectoryException si la ruta del directorio a borrar no es
	 *                                    valida.
	 */
	public void eliminarDirectorio(String ruta) throws InvalidDirectoryException {
		Position<Pair<String, PositionList<String>>> p = checkRuta(ruta);
		if(p==null)
			throw new InvalidDirectoryException("Dej� algunos campos vac�os.");
			try {
				if (p !=arbol.root()) {
					eliminarCarpeta(arbol.children(p));
					arbol.removeNode(p);	
		} else {
			throw new InvalidDirectoryException("No se puede eliminar el directorio principal.");
		}
			}catch (InvalidPositionException | EmptyTreeException  e1) {
		}
	}

	private void eliminarCarpeta(Iterable<Position<Pair<String, PositionList<String>>>> hijos)
			throws InvalidPositionException {
		for (Position<Pair<String, PositionList<String>>> b : hijos) {
			eliminarCarpeta(arbol.children(b));
			arbol.removeNode(b);
		}
	}

	public void moverDirectorio(String ruta1, String ruta2) throws InvalidDirectoryException{
		Position<Pair<String, PositionList<String>>> aMover = checkRuta(ruta1);
		Position<Pair<String, PositionList<String>>> Destino = checkRuta(ruta2);
		try {
			if (aMover != null && Destino != null && aMover != arbol.root() && !esAncestro(aMover,Destino)) {
				Position<Pair<String, PositionList<String>>> daux = aMover;
				Position<Pair<String, PositionList<String>>> pos = arbol.addLastChild(Destino, daux.element());
				arbol.removeNode(aMover);
				eliminarYCrear(aMover, pos);
			} else {
				throw new InvalidDirectoryException("error");
			}
		} catch (InvalidPositionException | EmptyTreeException e) {
			
		}
	}
	
	/**
	 * Devuelve un String que permite visualizar el sistema de archivos.
	 * @return String con saltos de linea y tabulaciones en el que muestra la jerarquia del sistema de archivos.
	 */
	public String mostrarSistema() {
		String stringArbol = "";
		if (!arbol.isEmpty()) {
			try {
				Position<Pair<String, PositionList<String>>> padre;		
				padre = arbol.root();
				String espacios = "";
				stringArbol += ("Carpeta Principal: " + padre.element().getKey() + "\n");
				for (Position<String> a : padre.element().getValue().positions()) {
					stringArbol += ("    Archivo: " + a.element() + "\n");
				}
				stringArbol += mostrarArbol(arbol, padre, espacios);
			} catch (EmptyTreeException e) {}
		}
		return stringArbol;
	}
	
	/**
	 * Devuelve parte del string necesaria para visualizar el sistema de archivos.
	 * @return parte del string String en el que muestra la jerarquia del sistema de archivos (concretamente toda la jerarquia excepto el directorio principal).
	 */
	private static String mostrarArbol(Tree<Pair<String, PositionList<String>>> arbol, Position<Pair<String, PositionList<String>>> padre, String espacios) {
		String stringArbol = "";
		try {
			for (Position<Pair<String, PositionList<String>>> p : arbol.children(padre)) {
				stringArbol += (espacios + "    Carpeta: " + p.element().getKey() + "\n");
				for (Position<String> a : p.element().getValue().positions()) {
					stringArbol += (espacios + "        Archivo: " + a.element() + "\n");
				}
				if (arbol.isInternal(p)) {
					String espaciosAux = espacios + "    ";
					stringArbol += mostrarArbol(arbol, p, espaciosAux);
				}
			}		} catch (InvalidPositionException e) {}
		return stringArbol;
	}
	
	/**
	 * Verifica si el primer directorio pasado por parametro es ancestro del segundo.
	 * @param aMover Posicion del directorio a mover en el arbol.
	 * @param Destino Posicion del directorio destino en el arbol.
	 * @return Verdadero si la posicion aMover es ancestro de Destino. Falso en caso contrario
	 */
	private boolean esAncestro(Position<Pair<String, PositionList<String>>> aMover, Position<Pair<String, PositionList<String>>> Destino) {
		boolean salida = false;
		Position<Pair<String, PositionList<String>>> pAux;
		pAux = Destino;
		try {
			
			while (!arbol.isRoot(pAux) && !salida) {
				pAux = arbol.parent(pAux);
				if (pAux == aMover) {
					salida = true;
				}
			}
		
		} catch (InvalidPositionException | BoundaryViolationException e) {
		}
		
		return salida;
	}

	private void eliminarYCrear(Position<Pair<String, PositionList<String>>> par1,
			Position<Pair<String, PositionList<String>>> par2) {
		try {
			for (Position<Pair<String, PositionList<String>>> ph : arbol.children(par1)) {
				Position<Pair<String, PositionList<String>>> pn = ph;
				Position<Pair<String, PositionList<String>>> pos = arbol.addLastChild(par2, pn.element());
				eliminarYCrear(ph, pos);
				if (arbol.isExternal(ph)) {
					arbol.removeNode(ph);
				}
			}
		} catch (InvalidPositionException e) {
		}
	}

	/**
	 * Retorna la cantidad de archivos y carpetas dentro de la jerarqu�a del sistema
	 * de archivos virtual.
	 * 
	 * @return Un objeto de tipo Pair con las cantidades de archivos y carpetas en el sistema virtual respectivamente.
	 */
	public Pair<Integer, Integer> cantidadDeDirectoriosYArchivos() {
		Pair<Integer, Integer> salida = new Pair<Integer, Integer>(0, 0);
		try {
			cantAux(salida, arbol.root());
		} catch (EmptyTreeException e) {
		}
		return salida;
	}

	/*
	 * M�todo auxiliar recursivo que recorre en preorden el �rbol para contar cantidad de archivos y directorios.
	 */
	private void cantAux(Pair<Integer, Integer> salida, Position<Pair<String, PositionList<String>>> raiz) {
		int aux = salida.getKey() + 1;
		salida.setKey(aux);
		salida.setValue(salida.getValue() + raiz.element().getValue().size());
		try {
			for (Position<Pair<String, PositionList<String>>> i : arbol.children(raiz)) {
				cantAux(salida, i);
			}
		} catch (InvalidPositionException e) {
		}
	}

	/**
	 * Genera una PositionList de strings donde lista los directorios y archivos del
	 * sistema de archivos por niveles
	 * 
	 * @return Una PositionList de Strings con los directorios y archivos
	 *         organizados por niveles
	 */
	public PositionList<String> listadoPorNiveles() {
		PositionList<String> toReturn=new DoubleLinkedList<String>();
		Queue<Position<Pair<String,PositionList<String>>>> Cola= new ArrayQueue<Position<Pair<String,PositionList<String>>>>();
		Queue<String> colaArchivos = new ArrayQueue<String>();
		
		try {
			Cola.enqueue(arbol.root());
			Cola.enqueue(null);
			Position<Pair<String,PositionList<String>>> PosAnterior=null;
			while(!Cola.isEmpty()) {
				Position<Pair<String,PositionList<String>>> pos=Cola.dequeue();
				if(PosAnterior != null) {
					for(Position<String> s : PosAnterior.element().getValue().positions()) {
						colaArchivos.enqueue(s.element() + ", ");
					}
				}
				PosAnterior=pos;
					if(pos != null) {
						toReturn.addLast(pos.element().getKey()+", ");
						for(Position<Pair<String,PositionList<String>>> p : arbol.children(pos)) {
							Cola.enqueue(p);
						}	
					} else {
						toReturn.addLast("/");
						while (!colaArchivos.isEmpty()) {
							toReturn.addLast(colaArchivos.dequeue());
						}
						if(!Cola.isEmpty()) {
							Cola.enqueue(null);
						}
					}
			}	
		} catch (EmptyTreeException | EmptyQueueException | InvalidPositionException e) {
		}
		return toReturn;
	}

	public Dictionary<String, String> listadoPorExtension() {
		Dictionary<String, String> dic = new DiccionarioConHashAbierto<String, String>();
		Position<Pair<String, PositionList<String>>> p = null;
		try {
			p = arbol.root();	
		} catch (EmptyTreeException e) {
		}
		listadoPorExtensionAux(dic, p);
		return dic;
	}
	
	private void listadoPorExtensionAux(Dictionary<String, String> dic,
			Position<Pair<String, PositionList<String>>> p) {
		PositionList<String> archivos = p.element().getValue();
		Iterator<String> it = archivos.iterator();
		try {
			while (it.hasNext()) {
				String separador = Pattern.quote(".");
				String[] partes = it.next().split(separador);
				if(partes.length==2)
					dic.insert(partes[1], partes[0]);
				else
					dic.insert("    ", partes[0]);
			}
			for (Position<Pair<String, PositionList<String>>> i : arbol.children(p)) {
				listadoPorExtensionAux(dic, i);
			}
		} catch (InvalidPositionException | InvalidKeyException e) {
		}
	}

	public Map<String, Integer> listadoPorProfundidad() {
		Map<String, Integer> map = new mapeoConHashCerrado<String, Integer>();
		try {
			listadoPorProfundidadAux(map, arbol.root(), -1);
		} catch (EmptyTreeException e) {
		}
		return map;
	}

	private void listadoPorProfundidadAux(Map<String, Integer> map, Position<Pair<String, PositionList<String>>> p,
			Integer profP) {
		try {
			map.put(rutaCompleta(p), profP + 1);
			for (Position<Pair<String, PositionList<String>>> hijos : arbol.children(p)) {
				listadoPorProfundidadAux(map, hijos, profP + 1);
			}
		} catch (InvalidPositionException | InvalidKeyException e) {
		}
	}

	private String rutaCompleta(Position<Pair<String, PositionList<String>>> directorio) {
		return rutaCompletaAux(directorio);
	}

	private String rutaCompletaAux(Position<Pair<String, PositionList<String>>> directorio) {
		String salida = directorio.element().getKey();
		try {
			if (directorio != arbol.root()) {

				salida = rutaCompletaAux(arbol.parent(directorio)) + "\\" + salida;

			}
		} catch (EmptyTreeException | InvalidPositionException | BoundaryViolationException e) {
		}

		return salida;

	}

	private Position<Pair<String, PositionList<String>>> checkRuta(String r) {
		Position<Pair<String, PositionList<String>>> salida = null;
		String separador = Pattern.quote("\\");
		String[] partes = r.split(separador);
		try {
			if (arbol.root().element().getKey().equals(partes[0])) {
				salida = arbol.root();
				if (partes.length > 1) {
					Iterable<Position<Pair<String, PositionList<String>>>> hijos = arbol.children(arbol.root());
					salida = rutaValida(1, partes, hijos);
				}
			}
		} catch (EmptyTreeException | InvalidPositionException e) {
		}

		return salida;
	}

	private Position<Pair<String, PositionList<String>>> rutaValida(int i, String[] partes,
			Iterable<Position<Pair<String, PositionList<String>>>> hijos) {
		boolean encontre = false;
		String aux = null;
		Position<Pair<String, PositionList<String>>> salida = null;
		Position<Pair<String, PositionList<String>>> auxP = null;
		Iterator<Position<Pair<String, PositionList<String>>>> it = hijos.iterator();
		if (i < partes.length) {
			while (it.hasNext() && !encontre) {
				auxP = it.next();
				aux = partes[i];
				if (aux.equals(auxP.element().getKey())) {
					encontre = true;
				}
			}
			if (i == partes.length - 1 && encontre) {
				salida = auxP;
			} else {
				if (i < partes.length - 1 && encontre) {
					try {
						salida = rutaValida(i + 1, partes, arbol.children(auxP));

					} catch (InvalidPositionException e) {
					}
				}

			}
		}
		return salida;
	}

}