package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import Auxiliares.InvalidDirectoryException;
import Auxiliares.InvalidFileException;
import Auxiliares.Pair;
import Controlador.*;
import Auxiliares.Entry;
import Auxiliares.Position;
import java.awt.SystemColor;

/**
 * Crea una interfaz gr�fica que le permite al usuario hacer uso de las
 * operaciones de la clase l�gica.
 * 
 * @author Aitor, Ortu�o Rossetto.
 */
public class InterfazGrafica {

	private JFrame frm_EDDrive;
	private Logica controlador;

	private JPanel Panel_GenerarSistema;
	private JPanel Panel_Operacional;
	private JPanel Panel_Listados;

	private JTextField textField_DirArchivo;

	private JScrollPane Panel_Arbol;
	private JScrollPane scroll_Listados;

	private JTextPane txtp_MostrarListados;
	private JTextPane txtp_MostrarArbol;

	private JLabel lbl_Instruccion1;
	private JLabel lbl_Instruccion2;

	private JButton btn_GenerarSistemaDe;
	private JButton btn_AgregarDirectorio;
	private JButton btn_EliminarDirectorio;
	private JButton btn_AgregarArchivo;
	private JButton btn_EliminarArchivo;
	private JButton btn_MoverDirectorio;
	private JButton btn_ListadoNiveles;
	private JButton btn_ListadoExtension;
	private JButton btn_ListadoProfundidad;
	private JButton btn_Cantidades;

	/**
	 * Crea la ventana y ejecuta la aplicaci�n.
	 * @param args argumento del main.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazGrafica window = new InterfazGrafica();
					window.frm_EDDrive.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Inicializa la interfaz grafica.
	 */
	public InterfazGrafica() {
		controlador = new Logica();
		inicializarFrame();
		inicializarPaneles();
		inicializarBotones();
	}

	/**
	 * Inicializa el frame principal de la interfaz.
	 */
	private void inicializarFrame() {
		frm_EDDrive = new JFrame();
		frm_EDDrive.setResizable(false);
		frm_EDDrive.setForeground(Color.GRAY);
		frm_EDDrive.setBackground(Color.WHITE);
		frm_EDDrive.getContentPane().setBackground(SystemColor.inactiveCaption);
		frm_EDDrive.getContentPane().setForeground(new Color(0, 0, 0));
		frm_EDDrive.setTitle("ED-Drive");
		frm_EDDrive.setBounds(115, 150, 825, 493);
		frm_EDDrive.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm_EDDrive.getContentPane().setLayout(null);
	}

	/**
	 * Inicializa los paneles, labels, cuadros de texto, paneles de texto y paneles
	 * scroll.
	 */
	private void inicializarPaneles() {

		Panel_GenerarSistema = new JPanel();
		Panel_GenerarSistema.setBackground(Color.WHITE);
		Panel_GenerarSistema.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.GRAY));
		Panel_GenerarSistema.setBounds(10, 11, 365, 171);
		frm_EDDrive.getContentPane().add(Panel_GenerarSistema);
		Panel_GenerarSistema.setLayout(null);

		Panel_Operacional = new JPanel();
		Panel_Operacional.setBackground(Color.WHITE);
		Panel_Operacional.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.GRAY));
		Panel_Operacional.setBounds(387, 11, 422, 171);
		frm_EDDrive.getContentPane().add(Panel_Operacional);
		Panel_Operacional.setLayout(null);

		textField_DirArchivo = new JTextField();
		textField_DirArchivo.setBounds(27, 66, 272, 32);
		Panel_GenerarSistema.add(textField_DirArchivo);
		textField_DirArchivo.setColumns(10);

		lbl_Instruccion1 = new JLabel("Ingrese la direcci\u00F3n del archivo para");
		lbl_Instruccion1.setForeground(Color.BLACK);
		lbl_Instruccion1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Instruccion1.setBounds(12, 0, 242, 32);
		Panel_GenerarSistema.add(lbl_Instruccion1);

		lbl_Instruccion2 = new JLabel("generar el sistema de archivos virtual:");
		lbl_Instruccion2.setForeground(Color.BLACK);
		lbl_Instruccion2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Instruccion2.setBounds(22, 28, 232, 14);
		Panel_GenerarSistema.add(lbl_Instruccion2);

		Panel_Arbol = new JScrollPane();
		Panel_Arbol.setBounds(10, 191, 367, 264);
		frm_EDDrive.getContentPane().add(Panel_Arbol);

		txtp_MostrarArbol = new JTextPane();
		txtp_MostrarArbol.setEditable(false);
		txtp_MostrarArbol.setForeground(Color.BLACK);
		txtp_MostrarArbol.setBackground(Color.WHITE);
		Panel_Arbol.setViewportView(txtp_MostrarArbol);

		Panel_Listados = new JPanel();
		Panel_Listados.setBackground(Color.WHITE);
		Panel_Listados.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
		Panel_Listados.setBounds(387, 191, 422, 37);
		frm_EDDrive.getContentPane().add(Panel_Listados);

		scroll_Listados = new JScrollPane();
		scroll_Listados.setBounds(387, 228, 422, 227);
		frm_EDDrive.getContentPane().add(scroll_Listados);

		txtp_MostrarListados = new JTextPane();
		txtp_MostrarListados.setEditable(false);
		txtp_MostrarListados.setForeground(Color.BLACK);
		txtp_MostrarListados.setBackground(Color.WHITE);
		scroll_Listados.setViewportView(txtp_MostrarListados);

	}

	/**
	 * Inicializa los botones de la interfaz.
	 */
	private void inicializarBotones() {

		btn_GenerarSistemaDe = new JButton("Generar Sistema de Archivos Virtual");
		btn_GenerarSistemaDe.setBackground(Color.LIGHT_GRAY);
		btn_GenerarSistemaDe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btn_GenerarSistemaDe.setForeground(Color.BLACK);
		btn_GenerarSistemaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					controlador.generarJerarquia(textField_DirArchivo.getText());
					txtp_MostrarArbol.setText(controlador.mostrarSistema());
					habilitarBotones();
					txtp_MostrarListados.setText("");
					btn_GenerarSistemaDe.setEnabled(false);
				} catch (InvalidFileException e1) {
					txtp_MostrarArbol.setText("");
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "ERROR",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		btn_GenerarSistemaDe.setBounds(27, 117, 272, 39);
		Panel_GenerarSistema.add(btn_GenerarSistemaDe);

		btn_AgregarDirectorio = new JButton("Agregar Directorio");
		btn_AgregarDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String rutaDirectorio = JOptionPane.showInputDialog("Ingresar ruta del directorio a agregar:", "");
				if (rutaDirectorio != null) {
					String nombreDirectorio = JOptionPane.showInputDialog("Ingresar el nombre del nuevo directorio:",
							"");
					if (nombreDirectorio != null && !nombreDirectorio.equals("")) {
						try {
							controlador.agregarDirectorio(rutaDirectorio, nombreDirectorio);
						} catch (InvalidDirectoryException e) {
							JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR",
									JOptionPane.INFORMATION_MESSAGE);
						}
						txtp_MostrarArbol.setText(controlador.mostrarSistema());
						txtp_MostrarListados.setText("");
					} else {
						if (nombreDirectorio.equals("")) {
							JOptionPane.showMessageDialog(new JFrame(),
									"Ha dejado alguno de los \ncampos anteriores vac�os", "ERROR",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		btn_AgregarDirectorio.setEnabled(false);
		btn_AgregarDirectorio.setForeground(Color.BLACK);
		btn_AgregarDirectorio.setBackground(Color.LIGHT_GRAY);
		btn_AgregarDirectorio.setBounds(25, 17, 167, 33);
		Panel_Operacional.add(btn_AgregarDirectorio);

		btn_EliminarDirectorio = new JButton("Eliminar Directorio");
		btn_EliminarDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rutaDirectorio = JOptionPane.showInputDialog("Ingresar ruta del directorio a eliminar:", "");
				if (rutaDirectorio != null) {
					try {
						controlador.eliminarDirectorio(rutaDirectorio);
					} catch (InvalidDirectoryException e1) {
						JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "ERROR",
								JOptionPane.INFORMATION_MESSAGE);
					}
					txtp_MostrarListados.setText("");
					txtp_MostrarArbol.setText(controlador.mostrarSistema());
				}
			}
		});
		btn_EliminarDirectorio.setEnabled(false);
		btn_EliminarDirectorio.setForeground(Color.BLACK);
		btn_EliminarDirectorio.setBackground(Color.LIGHT_GRAY);
		btn_EliminarDirectorio.setBounds(25, 70, 167, 33);
		Panel_Operacional.add(btn_EliminarDirectorio);

		btn_AgregarArchivo = new JButton("Agregar Archivo");
		btn_AgregarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rutaDirectorio = JOptionPane
						.showInputDialog("Ingresar ruta del directorio en que se agregar�a el archivo:", "");
				if (rutaDirectorio != null) {
					String nombreArchivo = JOptionPane.showInputDialog("Ingresar el nombre del nuevo archivo:", "");
					if (nombreArchivo != null && !nombreArchivo.equals("")) {
						try {
							controlador.agregarArchivo(rutaDirectorio, nombreArchivo);
						} catch (InvalidFileException e1) {
							JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "ERROR",
									JOptionPane.INFORMATION_MESSAGE);
						}
						txtp_MostrarArbol.setText(controlador.mostrarSistema());
						txtp_MostrarListados.setText("");
					} else {
						if (nombreArchivo.equals("")) {
							JOptionPane.showMessageDialog(new JFrame(),
									"Ha dejado alguno de los \ncampos anteriores vac�os", "ERROR",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}

			}
		});
		btn_AgregarArchivo.setEnabled(false);
		btn_AgregarArchivo.setForeground(Color.BLACK);
		btn_AgregarArchivo.setBackground(Color.LIGHT_GRAY);
		btn_AgregarArchivo.setBounds(230, 17, 167, 33);
		Panel_Operacional.add(btn_AgregarArchivo);
		
		
		btn_Cantidades = new JButton("Cantidades");
		btn_Cantidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pair<Integer, Integer> aux = controlador.cantidadDeDirectoriosYArchivos();
				JOptionPane.showMessageDialog(null,
						"Cantidad de directorios: " + aux.getKey() + " Cantidad de Archivos: " + aux.getValue());
			}
		});
		btn_Cantidades.setEnabled(false);
		btn_Cantidades.setForeground(Color.BLACK);
		btn_Cantidades.setBackground(Color.LIGHT_GRAY);
		btn_Cantidades.setBounds(230, 121, 167, 33);
		Panel_Operacional.add(btn_Cantidades);

		btn_EliminarArchivo = new JButton("Eliminar Archivo");
		btn_EliminarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rutaArchivo = JOptionPane.showInputDialog("Ingresar ruta del archivo a eliminar:", "");
				if (rutaArchivo != null) {
					try {
						controlador.eliminarArchivo(rutaArchivo);
					} catch (InvalidFileException e1) {
						JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "ERROR",
								JOptionPane.INFORMATION_MESSAGE);
					}
					txtp_MostrarArbol.setText(controlador.mostrarSistema());
					txtp_MostrarListados.setText("");
				}
			}
		});
		btn_EliminarArchivo.setEnabled(false);
		btn_EliminarArchivo.setForeground(Color.BLACK);
		btn_EliminarArchivo.setBackground(Color.LIGHT_GRAY);
		btn_EliminarArchivo.setBounds(230, 70, 167, 33);
		Panel_Operacional.add(btn_EliminarArchivo);

		btn_MoverDirectorio = new JButton("Mover Directorio");
		btn_MoverDirectorio.setEnabled(false);
		btn_MoverDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rutaDirectorioToMove = JOptionPane
						.showInputDialog("Ingresar ruta del directorio que quiere mover:", "");
				if (rutaDirectorioToMove != null && !rutaDirectorioToMove.equals("")) {
					String rutaDirectorioDestino = JOptionPane.showInputDialog("Ingresar ruta del directorio destino:",
							"");
					if (rutaDirectorioDestino != null && !rutaDirectorioDestino.equals("")) {
						try {
							controlador.moverDirectorio(rutaDirectorioToMove, rutaDirectorioDestino);
						} catch (InvalidDirectoryException e1) {
							System.out.println(" excepcion");
							JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "ERROR",
									JOptionPane.INFORMATION_MESSAGE);
						}
						txtp_MostrarArbol.setText(controlador.mostrarSistema());
						txtp_MostrarListados.setText("");
					} else {
						JOptionPane.showMessageDialog(new JFrame(),
								"Ha dejado alguno de los \ncampos anteriores vac�os", "ERROR",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Ha dejado alguno de los \ncampos anteriores vac�os",
							"ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btn_MoverDirectorio.setForeground(Color.BLACK);
		btn_MoverDirectorio.setBackground(Color.LIGHT_GRAY);
		btn_MoverDirectorio.setBounds(25, 121, 167, 33);
		Panel_Operacional.add(btn_MoverDirectorio);

		
		

		btn_ListadoNiveles = new JButton("Listado Niveles");
		btn_ListadoNiveles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 1;

				String ListadoMostrar = "Nivel - Directorios y Archivos \n \n   0    -    ";
				for (Position<String> pos : controlador.listadoPorNiveles().positions()) {
					if (pos.element().equals("/")) {
						ListadoMostrar += "\n   " + i + "    -    ";
						i++;
					} else {
						ListadoMostrar += pos.element();
					}
				}
				txtp_MostrarListados.setText(ListadoMostrar);
			}
		});
		btn_ListadoNiveles.setBounds(0, 0, 127, 30);
		Panel_Listados.add(btn_ListadoNiveles);
		btn_ListadoNiveles.setEnabled(false);
		btn_ListadoNiveles.setForeground(Color.BLACK);
		btn_ListadoNiveles.setBackground(Color.LIGHT_GRAY);

		btn_ListadoExtension = new JButton("Listado Extensi�n");
		btn_ListadoExtension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ListadoMostrar = ("Extensi�n - Nombre \n \n");
				for (Entry<String, String> pos : controlador.listadoPorExtension().entries()) {
					ListadoMostrar += pos.getKey() + ("    -    ") + pos.getValue();
					ListadoMostrar += ("\n");
				}
				txtp_MostrarListados.setText(ListadoMostrar);
			}
		});
		btn_ListadoExtension.setEnabled(false);
		btn_ListadoExtension.setForeground(Color.BLACK);
		btn_ListadoExtension.setBackground(Color.LIGHT_GRAY);
		btn_ListadoExtension.setBounds(126, 0, 146, 30);
		Panel_Listados.add(btn_ListadoExtension);

		btn_ListadoProfundidad = new JButton("Listado Profundidad");
		btn_ListadoProfundidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ListadoMostrar = ("Profundidad - Direccion \n \n");
				for (Entry<String, Integer> pos : controlador.listadoPorProfundidad().entries()) {
					ListadoMostrar += ("           ") + pos.getValue() + ("            ") + pos.getKey();
					ListadoMostrar += ("\n");
				}
				txtp_MostrarListados.setText(ListadoMostrar);
			}
		});
		btn_ListadoProfundidad.setEnabled(false);
		btn_ListadoProfundidad.setForeground(Color.BLACK);
		btn_ListadoProfundidad.setBackground(Color.LIGHT_GRAY);
		btn_ListadoProfundidad.setBounds(270, 0, 152, 30);
		Panel_Listados.add(btn_ListadoProfundidad);

	}

	/**
	 * Hablilita los botones de la interfaz.
	 */
	private void habilitarBotones() {
		btn_GenerarSistemaDe.setEnabled(true);
		btn_AgregarDirectorio.setEnabled(true);
		btn_EliminarDirectorio.setEnabled(true);
		btn_AgregarArchivo.setEnabled(true);
		btn_EliminarArchivo.setEnabled(true);
		btn_MoverDirectorio.setEnabled(true);
		btn_ListadoNiveles.setEnabled(true);
		btn_ListadoExtension.setEnabled(true);
		btn_ListadoProfundidad.setEnabled(true);
		btn_Cantidades.setEnabled(true);
	}
}
