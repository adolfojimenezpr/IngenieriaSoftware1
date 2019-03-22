package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractListModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Interfaces.InterfaceGUI;
import controlador.ControladorAlmacen;
import util.TiendaMain;

public class InterfazAlmacen extends JFrame implements InterfaceGUI {
	private JTextField textoNombre;
	private JTextField textoArtista;
	private JTextField textoEstilo;
	private JTextField textoFechaInicio;
	private JTextField textoCantidad;
	private JTextField textoPrecio;
	private String productoSeleccionado = "";

	private int idSeleccionado = -1;
	private static InterfazAlmacen gui;
	private ArrayList<HashMap<String, Object>> listaCompleta;

	private JList<String> list;
	private final String AYUDA = "En esta ventana estan implementadas las funcionalidades referentes \n"
			+ "a la gestion del almacen.\n" + " A la izquierda se muestra una lista con los productos registrados \n"
			+ "en el sistema. A la derecha estan dispuestos verticalmente unos botones \n"
			+ "que permiten realizar operaciones sobre el producto reflejado por \n"
			+ "sus atributos en el centro de la pantalla. Estas opciones son: \n"
			+ "[Mostrar, Añadir, Eliminar, Modificar]. \n"
			+ "Para regresar al menu principal debe presionar el boton situado \n"
			+ "a la derecha llamado 'Menu principal' \n"
			+ "\n¿Desea ver la ayuda general del programa? Presione el boton 'Si' en tal caso";
	private JButton botonMostrar;
	private JRadioButton radioAlmacenLocal;
	private JButton botonModificar;
	private JTextPane textoInfoEstado;

	public static InterfazAlmacen getInstance() {
		if (gui == null)
			gui = new InterfazAlmacen();
		return gui;
	}

	private InterfazAlmacen() {
		setLocation(500, 500);
		// setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(10, 10));
		setTitle("Gestión almacen");
		JMenuBar menuBar = new JMenuBar();

		getContentPane().add(menuBar, BorderLayout.NORTH);
		JMenu botonMenuOpciones = new JMenu("Opciones");
		menuBar.add(botonMenuOpciones);

		JMenuItem botonAyuda = new JMenuItem("Ayuda");
		botonAyuda.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				botonAyuda.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int respuesta = JOptionPane.showConfirmDialog(botonAyuda, AYUDA, "Ayuda de ventana",
								JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE); // Se puede añadir icono
						if (respuesta == 0)
							JOptionPane.showMessageDialog(botonAyuda, TiendaMain.getInstance().getAyuda(),
									"Ayuda Infamous Music", JOptionPane.OK_OPTION);

					}
				});

			}
		});
		botonMenuOpciones.add(botonAyuda);

		Panel panel_derecha = new Panel();
		panel_derecha.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(panel_derecha, BorderLayout.EAST);
		panel_derecha.setLayout(new BoxLayout(panel_derecha, BoxLayout.Y_AXIS));

		radioAlmacenLocal = new JRadioButton("Local");
		panel_derecha.add(radioAlmacenLocal);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_3);

		botonMostrar = new JButton("Mostrar ");
		panel_derecha.add(botonMostrar);
		botonMostrar.setEnabled(false);

		Component verticalStrut = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut);

		JButton botonAñadir = new JButton(" Añadir   ");
		panel_derecha.add(botonAñadir);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_1);

		JButton botonEliminar = new JButton("Eliminar ");
		panel_derecha.add(botonEliminar);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_2);

		botonModificar = new JButton("Modificar");
		panel_derecha.add(botonModificar);
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_4);

		JPanel panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(50, 50));
		panel_derecha.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 300));
		panel_7.add(rigidArea_2, BorderLayout.NORTH);

		JButton botonMenu = new JButton("Menu principal");
		botonMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetear();
				setVisible(false);
				InterfazPrincipal.getInstance().setVisible(true);
			}
		});
		panel_7.add(botonMenu);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_7.add(horizontalStrut, BorderLayout.EAST);
		// panel_derecha.setFocusTraversalPolicy(new FocusTraversalOnArray(new
		// Component[]{botonMostrar}));

		Panel panel_central = new Panel();
		panel_central.setPreferredSize(new Dimension(150, 100));
		getContentPane().add(panel_central, BorderLayout.CENTER);
		panel_central.setLayout(new GridLayout(0, 1, 0, 0));

		Panel panel = new Panel();
		panel_central.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 4));

		JLabel lblNombre = new JLabel("Nombre:");
		panel.add(lblNombre);
		lblNombre.setVerticalAlignment(SwingConstants.TOP);

		textoNombre = new JTextField();
		panel.add(textoNombre);
		textoNombre.setColumns(25);

		Panel panel_1 = new Panel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(4);
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_central.add(panel_1);

		JLabel lblNewLabel = new JLabel("Artista");
		panel_1.add(lblNewLabel);

		textoArtista = new JTextField();
		panel_1.add(textoArtista);
		textoArtista.setColumns(20);

		Panel panel_2 = new Panel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setVgap(4);
		flowLayout_1.setHgap(10);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_central.add(panel_2);

		JLabel lblDuracion = new JLabel("Estilo");
		panel_2.add(lblDuracion);

		textoEstilo = new JTextField();
		panel_2.add(textoEstilo);
		textoEstilo.setColumns(20);

		Panel panel_3 = new Panel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setHgap(10);
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		flowLayout_2.setVgap(4);
		panel_central.add(panel_3);

		JLabel lblFechaInicio = new JLabel("Fecha lanzamiento");
		panel_3.add(lblFechaInicio);

		textoFechaInicio = new JTextField();
		panel_3.add(textoFechaInicio);
		textoFechaInicio.setColumns(20);

		Panel panel_4 = new Panel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_4.getLayout();
		flowLayout_3.setVgap(4);
		flowLayout_3.setHgap(10);
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		panel_central.add(panel_4);

		JLabel lblCantidad = new JLabel("Cantidad");
		panel_4.add(lblCantidad);

		textoCantidad = new JTextField();
		textoCantidad.setColumns(10);
		panel_4.add(textoCantidad);

		Panel panel_5 = new Panel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_5.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		flowLayout_4.setHgap(10);
		flowLayout_4.setVgap(4);
		panel_central.add(panel_5);

		JLabel lblPrecio = new JLabel("Precio");
		panel_5.add(lblPrecio);

		textoPrecio = new JTextField();
		textoPrecio.setColumns(10);
		panel_5.add(textoPrecio);

		Panel panel_izquierda = new Panel();
		getContentPane().add(panel_izquierda, BorderLayout.WEST);
		panel_izquierda.setLayout(new BorderLayout(10, 10));
		panel_izquierda.setPreferredSize(new Dimension(300, 100));

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel_izquierda.add(rigidArea_1, BorderLayout.WEST);

		JPanel panel_6 = new JPanel();
		panel_6.setPreferredSize(new Dimension(150, 100));
		panel_izquierda.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BorderLayout(0, 0));
		list = new JList<String>();

		list.setSize(150, 100);
		panel_6.add(new JScrollPane(list));
		list.setValueIsAdjusting(true);
		list.setToolTipText("Esta lista contiene los productos registrados");
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {};

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setVisibleRowCount(10);

		textoInfoEstado = new JTextPane();
		textoInfoEstado.setText("Vendidos: \nReservados:\nDisponibles:");
		panel_6.add(textoInfoEstado, BorderLayout.SOUTH);

		JPanel panel_abajo = new JPanel();
		getContentPane().add(panel_abajo, BorderLayout.SOUTH);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panel_abajo.add(rigidArea);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				botonMostrar.setEnabled(true);
			}
		});
		botonAñadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (estaTodoRelleno()) {
					HashMap<String, Object> atributos = new HashMap<String, Object>();
					int idDetalles = -1;
					for (int i = 0; i < listaCompleta.size(); i++) {
						if (((String) listaCompleta.get(i).get("album")).equals(textoNombre.getText())) {
							idDetalles = (int) listaCompleta.get(i).get("idDetalles");
							i = listaCompleta.size();
						}
					}
					int idProducto = -1;
					for (int i = 0; i < listaCompleta.size(); i++) {
						if (((String) listaCompleta.get(i).get("album")).equals(textoNombre.getText())) {
							idProducto = (int) listaCompleta.get(i).get("idProducto");
							i = listaCompleta.size();
						}
					}
					atributos.put("album", textoNombre.getText());
					atributos.put("artista", textoArtista.getText());
					atributos.put("estiloMusical", textoEstilo.getText());
					atributos.put("precio", Float.parseFloat(textoPrecio.getText()));
					atributos.put("idDetalles", idDetalles);
					atributos.put("idProducto", idProducto);
					atributos.put("idAlmacen", TiendaMain.getInstance().getIdAlmacen());

					atributos.put("fechaLanzamiento", java.sql.Date.valueOf(textoFechaInicio.getText()));
					String resultadoOperacion = ControladorAlmacen.getInstance().añadirProductos(atributos,
							Integer.parseInt(textoCantidad.getText()));
					JOptionPane.showMessageDialog(botonAñadir, resultadoOperacion, "Información de operación",
							JOptionPane.INFORMATION_MESSAGE);
					resetear();
				} else {
					JOptionPane.showMessageDialog(botonAñadir,
							"Debes rellenar todos los campos antes de añadir un nuevo producto",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		resetear();
		botonEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textoNombre.getText() == "") {
					JOptionPane.showMessageDialog(botonAñadir, "Debes rellenar el nombre del producto a eliminar",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				} else {
					int idProducto = -1;
					for (int i = 0; i < listaCompleta.size(); i++) {
						if (((String) listaCompleta.get(i).get("album")).equals(textoNombre.getText())) {
							idProducto = (int) listaCompleta.get(i).get("idProducto");
							i = listaCompleta.size();
						}
					}
					if (idProducto == -1) {
						JOptionPane.showMessageDialog(botonEliminar,
								"El nombre del producto no se corresponde con ninguno de la lista!",
								"Información de operación", JOptionPane.ERROR_MESSAGE);
					} else {
						String resultadoOperacion = ControladorAlmacen.getInstance().eliminarProductos(idProducto,
								Integer.parseInt(textoCantidad.getText()));
						JOptionPane.showMessageDialog(botonEliminar, resultadoOperacion, "Información de operación",
								JOptionPane.INFORMATION_MESSAGE);
						resetear();
					}
				}

			}
		});

		botonModificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (estaTodoRelleno()) {
					HashMap<String, Object> atributos = new HashMap<String, Object>();
					if (idSeleccionado == -1) {
						JOptionPane.showMessageDialog(botonModificar,
								"El nombre del producto no se corresponde con ninguno de la lista!",
								"Información de operación", JOptionPane.ERROR_MESSAGE);
					} else {
						atributos.put("album", textoNombre.getText());
						atributos.put("artista", textoArtista.getText());
						atributos.put("estiloMusical", textoEstilo.getText());
						atributos.put("precio", Float.parseFloat(textoPrecio.getText()));
						atributos.put("idDetalles", idSeleccionado);
						atributos.put("fechaLanzamiento", java.sql.Date.valueOf(textoFechaInicio.getText()));
						String resultadoOperacion = ControladorAlmacen.getInstance()
								.modificarDetallesProducto(atributos);
						JOptionPane.showMessageDialog(botonModificar, resultadoOperacion, "Información de operación",
								JOptionPane.INFORMATION_MESSAGE);
						resetear();
					}

				}
			}
		});

		botonMostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				productoSeleccionado = list.getSelectedValue();

				HashMap<String, Object> producto = null;
				for (int i = 0; i < listaCompleta.size(); i++) {
					if (((String) listaCompleta.get(i).get("album")).equals(productoSeleccionado)) {
						producto = listaCompleta.get(i);
						i = listaCompleta.size();
					}
				}
				if (producto != null) {
					rellenar(producto);
					idSeleccionado = (int) producto.get("idDetalles");
				} else {
					JOptionPane.showMessageDialog(botonMostrar,
							"Ha ocurrido un error al obtener información del producto", "Información de operación",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		radioAlmacenLocal.setSelected(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 800);
	}

	@Override
	public void arrancar() {
		setVisible(true);
		resetear();
	}

	public void resetear() {

		textoArtista.setText("");
		textoCantidad.setText("");
		botonModificar.setEnabled(true);
		textoEstilo.setText("");
		textoFechaInicio.setText("");
		textoNombre.setText("");
		textoPrecio.setText("");
		textoInfoEstado.setText("Disponibles: \nReservados: \nVendidos: ");
		int almacen = TiendaMain.getInstance().getIdAlmacen();
		if (!radioAlmacenLocal.isSelected())
			almacen = 0;
		listaCompleta = ControladorAlmacen.getInstance().consultarProductos(almacen);
		rellenarLista();
	}

	public void rellenar(HashMap<String, Object> producto) {
		textoNombre.setText((String) producto.get("album"));
		textoArtista.setText((String) producto.get("artista"));
		textoEstilo.setText((String) producto.get("estiloMusical"));
		textoFechaInicio.setText(((Date) producto.get("fechaLanzamiento")).toString());
		textoCantidad.setText(((Integer) producto.get("cantidadDisponible")).toString());
		textoPrecio.setText(((Float) producto.get("precio")).toString());
		String cantidadVendida, cantidadDisponible, cantidadReservada;
		cantidadVendida = ((Integer) producto.get("cantidadVendida")).toString();
		cantidadReservada = ((Integer) producto.get("cantidadReservada")).toString();
		cantidadDisponible = ((Integer) producto.get("cantidadDisponible")).toString();
		textoInfoEstado.setText("Disponibles: " + cantidadDisponible + "\nReservados: " + cantidadReservada
				+ "\nVendidos: " + cantidadVendida);
	}

	public void rellenarLista() {

		StringBuilder auxiliar = new StringBuilder();

		for (int i = 0; i < listaCompleta.size(); i++) {
			auxiliar.append(listaCompleta.get(i).get("album") + ":");
		}
		AbstractListModel model = new AbstractListModel() {
			String[] values = auxiliar.toString().split(":");

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		};
		list.setModel(model);

	}

	private boolean estaTodoRelleno() {
		boolean resultado = false;
		if (textoArtista.getText() != "" && textoCantidad.getText() != "" && textoEstilo.getText() != ""
				&& textoFechaInicio.getText() != "" && textoNombre.getText() != "" && textoPrecio.getText() != "") {
			resultado = true;
		}
		return resultado;
	}
}
