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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import Interfaces.InterfaceGUI;
import controlador.ControladorCupon;
import util.TiendaMain;

public class InterfazCupon extends JFrame implements InterfaceGUI {
	private JTextField textoNombre;
	private JTextField textoDescuento;
	private JTextField textoDuracion;
	private int idSeleccionado;
	private JTextField textoFechaInicio;
	private static InterfazCupon gui;
	private ArrayList<HashMap<String, Object>> listaCompleta;
	private JList<String> list;
	private final String AYUDA = "En esta ventana estan implementadas las funcionalidades referentes \n"
			+ "a la gestion de los cupones.\n" + " A la izquierda se muestra una lista con los cupones registrados \n"
			+ "en el sistema. A la derecha estan dispuestos verticalmente unos botones \n"
			+ "que permiten realizar operaciones sobre el cupón reflejado por \n"
			+ "sus atributos en el centro de la pantalla. Estas opciones son: \n"
			+ "[Mostrar, Añadir, Eliminar, Modificar]. \n"
			+ "Para regresar al menu principal debe presionar el boton situado \n"
			+ "a la derecha llamado 'Menu principal' \n"
			+ "\n¿Desea ver la ayuda general del programa? Presione el boton 'Si' en tal caso";
	private JButton botonModificar;

	public static InterfazCupon getInstance() {
		if (gui == null)
			gui = new InterfazCupon();
		return gui;

	}

	private InterfazCupon() {
		setLocation(500, 500);
		setTitle("Gestión cupones");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(10, 10));

		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnNewMenu = new JMenu("Opciones");
		menuBar.add(mnNewMenu);

		JMenuItem botonAyuda = new JMenuItem("Ayuda");
		mnNewMenu.add(botonAyuda);
		botonAyuda.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(botonAyuda, AYUDA, "Ayuda de ventana",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE); // Se puede añadir icono
				if (respuesta == 0)
					JOptionPane.showMessageDialog(botonAyuda, TiendaMain.getInstance().getAyuda(), "Ayuda Infamous Music",
							JOptionPane.OK_OPTION);

			}
		});

		Panel panel_derecha = new Panel();
		panel_derecha.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(panel_derecha, BorderLayout.EAST);
		panel_derecha.setLayout(new BoxLayout(panel_derecha, BoxLayout.Y_AXIS));

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_3);

		JButton botonMostrar = new JButton("Mostrar");
		panel_derecha.add(botonMostrar);

		Component verticalStrut = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut);

		JButton botonAñadir = new JButton("Añadir");
		panel_derecha.add(botonAñadir);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_1);

		JButton botonEliminar = new JButton("Eliminar");
		panel_derecha.add(botonEliminar);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_2);

		botonModificar = new JButton("Modificar");
		panel_derecha.add(botonModificar);

		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(50, 50));
		panel_derecha.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 100));
		panel_4.add(rigidArea_2, BorderLayout.NORTH);

		JButton botonMenu = new JButton("Menu principal");
		botonMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetear();
				setVisible(false);
				InterfazPrincipal.getInstance().setVisible(true);
			}
		});
		panel_4.add(botonMenu, BorderLayout.CENTER);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_4.add(horizontalStrut, BorderLayout.EAST);
		panel_derecha.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { botonMostrar }));

		Panel panel_central = new Panel();
		getContentPane().add(panel_central, BorderLayout.CENTER);
		panel_central.setLayout(new GridLayout(0, 1, 0, 0));

		Panel panel = new Panel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		panel_central.add(panel);

		JLabel lblNombre = new JLabel("Nombre:");
		panel.add(lblNombre);
		lblNombre.setVerticalAlignment(SwingConstants.TOP);

		textoNombre = new JTextField();
		panel.add(textoNombre);
		textoNombre.setColumns(10);

		Panel panel_1 = new Panel();
		panel_central.add(panel_1);

		JLabel lblNewLabel = new JLabel("Descuento");
		panel_1.add(lblNewLabel);

		textoDescuento = new JTextField();
		panel_1.add(textoDescuento);
		textoDescuento.setColumns(10);

		Panel panel_2 = new Panel();
		panel_central.add(panel_2);

		JLabel lblDuracion = new JLabel("Duracion");
		panel_2.add(lblDuracion);

		textoDuracion = new JTextField();
		panel_2.add(textoDuracion);
		textoDuracion.setColumns(10);

		Panel panel_3 = new Panel();
		panel_central.add(panel_3);

		JLabel lblFechaInicio = new JLabel("Fecha inicio");
		panel_3.add(lblFechaInicio);

		textoFechaInicio = new JTextField();
		textoFechaInicio.setEditable(false);
		panel_3.add(textoFechaInicio);
		textoFechaInicio.setColumns(10);

		Panel panel_izquierda = new Panel();
		getContentPane().add(panel_izquierda, BorderLayout.WEST);
		panel_izquierda.setLayout(new BorderLayout(10, 10));
		panel_izquierda.setPreferredSize(new Dimension(200, 100));
		list = new JList<String>();
		list.setToolTipText("Selecciona el cupon que deseas mostrar / modificar");

		list.setValueIsAdjusting(true);
		list.setToolTipText("Esta lista contiene los cupones creados");
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
		panel_izquierda.add(new JScrollPane(list));

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel_izquierda.add(rigidArea_1, BorderLayout.WEST);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// botonMostrar.setEnabled(true);

			}
		});

		botonMostrar.setEnabled(true);
		JPanel panel_abajo = new JPanel();
		getContentPane().add(panel_abajo, BorderLayout.SOUTH);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panel_abajo.add(rigidArea);

		botonModificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (estaTodoRelleno()) {
					for (int i = 0; i < listaCompleta.size(); i++) {
						if (((String) listaCompleta.get(i).get("nombre")).equals(list.getSelectedValue())) {
							idSeleccionado = (int) listaCompleta.get(i).get("idCupon");
							i = listaCompleta.size();
						}
					}
					HashMap<String, Object> atributos = new HashMap<String, Object>();
					atributos.put("nombre", textoNombre.getText());
					atributos.put("descuento", Integer.parseInt(textoDescuento.getText()));
					atributos.put("duracion", Integer.parseInt(textoDuracion.getText()));
					atributos.put("fechaInicio", java.sql.Date.valueOf(textoFechaInicio.getText()));
					atributos.put("idCupon", idSeleccionado);
					String resultadoOperacion = ControladorCupon.getInstance().modificarCupon(atributos);
					JOptionPane.showMessageDialog(botonModificar, resultadoOperacion, "Información de operación",
							JOptionPane.INFORMATION_MESSAGE);
					resetear();

				}

			}
		});
		botonEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textoNombre.getText() == "") {
					JOptionPane.showMessageDialog(botonAñadir, "Debes rellenar el nombre del cupon a eliminar",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				} else {
					int idCupon = -1;
					for (int i = 0; i < listaCompleta.size(); i++) {
						if (((String) listaCompleta.get(i).get("nombre")).equals(textoNombre.getText())) {
							idCupon = (int) listaCompleta.get(i).get("idCupon");
							i = listaCompleta.size();
						}
					}
					if (idCupon == -1) {
						JOptionPane.showMessageDialog(botonModificar,
								"El nombre del cupon no se corresponde con ninguno de la lista!",
								"Información de operación", JOptionPane.ERROR_MESSAGE);
					} else {
						String resultadoOperacion = ControladorCupon.getInstance().eliminarCupon(idCupon);
						JOptionPane.showMessageDialog(botonModificar, resultadoOperacion, "Información de operación",
								JOptionPane.INFORMATION_MESSAGE);
						resetear();
					}
				}
			}
		});
		botonAñadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (estaTodoRelleno()) {
					HashMap<String, Object> atributos = new HashMap<String, Object>();
					atributos.put("nombre", textoNombre.getText());
					atributos.put("descuento", Integer.parseInt(textoDescuento.getText()));
					atributos.put("duracion", Integer.parseInt(textoDuracion.getText()));
					String resultadoOperacion = ControladorCupon.getInstance().añadirCupon(atributos);
					JOptionPane.showMessageDialog(botonAñadir, resultadoOperacion, "Información de operación",
							JOptionPane.INFORMATION_MESSAGE);
					resetear();
				} else {
					JOptionPane.showMessageDialog(botonAñadir,
							"Debes rellenar todos los campos antes de añadir un nuevo cupon",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		botonMostrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String cuponSeleccionado = list.getSelectedValue();
				HashMap<String, Object> cupon = null;
				for (int i = 0; i < listaCompleta.size(); i++) {
					if (((String) listaCompleta.get(i).get("nombre")).equals(cuponSeleccionado)) {
						cupon = listaCompleta.get(i);
						i = listaCompleta.size();
					}
				}
				if (cupon != null) {
					rellenar(cupon);
				} else {
					JOptionPane.showMessageDialog(botonMostrar, "Ha ocurrido un error al obtener información del cupón",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(800, 500);
	}

	@Override
	public void arrancar() {
		setVisible(true);
		resetear();
	}

	public void resetear() {

		textoDescuento.setText("");
		textoDuracion.setText("");
		textoFechaInicio.setText("");
		textoNombre.setText("");
		idSeleccionado = -1;
		listaCompleta = ControladorCupon.getInstance().consultarCupones();
		rellenarLista();
	}

	public void rellenarLista() {

		StringBuilder auxiliar = new StringBuilder();

		for (int i = 0; i < listaCompleta.size(); i++) {
			auxiliar.append(listaCompleta.get(i).get("nombre") + ":");
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

	public void rellenar(HashMap<String, Object> cupon) {
		textoDescuento.setText(((Integer) cupon.get("descuento")).toString());
		textoDuracion.setText(((Integer) cupon.get("duracion")).toString());
		textoFechaInicio.setText(((Date) cupon.get("fechaInicio")).toString());
		textoNombre.setText((String) cupon.get("nombre"));
	}

	private boolean estaTodoRelleno() {
		boolean resultado = false;
		if (textoDescuento.getText() != "" && textoDuracion.getText() != "" && textoFechaInicio.getText() != ""
				&& textoNombre.getText() != "") {
			resultado = true;
		}

		return resultado;
	}

}
