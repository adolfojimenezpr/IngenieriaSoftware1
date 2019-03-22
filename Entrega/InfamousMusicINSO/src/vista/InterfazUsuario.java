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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import Interfaces.InterfaceGUI;
import controlador.ControladorSeguridad;
import controlador.ControladorUsuario;
import dataObjetAccess.UsuarioDAO;
import util.TiendaMain;

import javax.swing.JPasswordField;

public class InterfazUsuario extends JFrame implements InterfaceGUI {

	private JTextField textoEmail;
	private JTextField textoNombre;
	private JTextField textoDNI;
	private JTextField textoFechaContrato;
	private static InterfazUsuario gui;
	private JTextField textoApellidos;
	private JTextField textoTelefono;
	private JTextField textoPuesto;
	private JButton botonModificar;
	private JButton botonEliminar;
	private int idSeleccionado;

	private ArrayList<HashMap<String, Object>> listaCompleta;
	private JList<String> list;

	private final String AYUDA = "En esta ventana estan implementadas las funcionalidades referentes \n"
			+ "a la gestion de usuarios.\n" + "A la izquierda se muestra una lista con los usuarios registrados \n"
			+ "en el sistema. A la derecha estan dispuestos verticalmente unos \n"
			+ "botones que permiten realizar operaciones sobre el usuario \n"
			+ "reflejado por sus atributos en el centro de la pantalla. \n"
			+ "Estas opciones son: [Mostrar, Añadir, Eliminar, Modificar]. \n"
			+ "Las dos ultimas opciones solo estan disponibles para usuarios ADMIN\n"
			+ "Para regresar al menu principal debe presionar el boton situado\n"
			+ "a la derecha llamado 'Menu principal' \n\n" + "\n¿Desea ver la ayuda general del programa? \n"
			+ "Presione el boton 'Si' en tal caso\n\n";
	private JRadioButton radioEsAdmin;
	private JPasswordField textoContraseña;
	private JButton botonAñadir;

	public static InterfazUsuario getInstance() { // iniciacion perezosa
		if (gui == null)
			gui = new InterfazUsuario();
		return gui;

	}

	private InterfazUsuario() {
		setTitle("Gestión usuarios");
		setLocation(500, 500);
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
					JOptionPane.showMessageDialog(botonAyuda, TiendaMain.getInstance().getAyuda(),
							"Ayuda Infamous Music", JOptionPane.OK_OPTION);

			}
		});

		Panel panel_derecha = new Panel();
		panel_derecha.setPreferredSize(new Dimension(180, 100));
		getContentPane().add(panel_derecha, BorderLayout.EAST);
		panel_derecha.setLayout(new BoxLayout(panel_derecha, BoxLayout.Y_AXIS));

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_3);

		JButton botonMostrar = new JButton("Mostrar");
		panel_derecha.add(botonMostrar);

		Component verticalStrut = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut);

		botonAñadir = new JButton("Añadir");
		panel_derecha.add(botonAñadir);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_1);

		botonEliminar = new JButton("Eliminar");
		panel_derecha.add(botonEliminar);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_2);

		botonModificar = new JButton("Modificar");
		panel_derecha.add(botonModificar);

		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(50, 50));
		panel_derecha.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 300));
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
		panel_central.add(panel);

		JLabel lblNombre = new JLabel("Nombre:");
		panel.add(lblNombre);
		lblNombre.setVerticalAlignment(SwingConstants.TOP);

		textoNombre = new JTextField();
		panel.add(textoNombre);
		textoNombre.setColumns(20);

		Panel panel_5 = new Panel();
		panel_central.add(panel_5);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setVerticalAlignment(SwingConstants.TOP);
		panel_5.add(lblApellidos);

		textoApellidos = new JTextField();
		textoApellidos.setColumns(20);
		panel_5.add(textoApellidos);

		Panel panel_1 = new Panel();
		panel_central.add(panel_1);

		JLabel lblNewLabel = new JLabel("Email");
		panel_1.add(lblNewLabel);

		textoEmail = new JTextField();
		panel_1.add(textoEmail);
		textoEmail.setColumns(20);

		Panel panel_2 = new Panel();
		panel_central.add(panel_2);

		JLabel lblEmail = new JLabel("DNI");
		panel_2.add(lblEmail);

		textoDNI = new JTextField();
		panel_2.add(textoDNI);
		textoDNI.setColumns(15);

		Panel panel_6 = new Panel();
		panel_central.add(panel_6);

		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setVerticalAlignment(SwingConstants.TOP);
		panel_6.add(lblTelefono);

		textoTelefono = new JTextField();
		textoTelefono.setColumns(15);
		panel_6.add(textoTelefono);

		Panel panel_7 = new Panel();
		panel_central.add(panel_7);

		JLabel puesto = new JLabel("Puesto:");
		puesto.setVerticalAlignment(SwingConstants.TOP);
		panel_7.add(puesto);

		radioEsAdmin = new JRadioButton("es Admin");
		panel_7.add(radioEsAdmin);

		Panel panel_8 = new Panel();
		panel_central.add(panel_8);

		JLabel lblContrasea = new JLabel("Contraseña");
		panel_8.add(lblContrasea);

		textoContraseña = new JPasswordField();
		textoContraseña.setColumns(15);
		panel_8.add(textoContraseña);

		Panel panel_3 = new Panel();
		panel_central.add(panel_3);

		JLabel lblFechaContrato = new JLabel("Fecha contratacion");
		panel_3.add(lblFechaContrato);

		textoFechaContrato = new JTextField();
		textoFechaContrato.setEditable(false);
		panel_3.add(textoFechaContrato);
		textoFechaContrato.setColumns(10);

		Panel panel_izquierda = new Panel();
		getContentPane().add(panel_izquierda, BorderLayout.WEST);
		panel_izquierda.setLayout(new BorderLayout(10, 10));
		panel_izquierda.setPreferredSize(new Dimension(200, 100));
		list = new JList<String>();
		list.setValueIsAdjusting(true);
		list.setToolTipText("Esta lista contiene los usuarioes creados");
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {};

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setVisibleRowCount(10);
		panel_izquierda.add(list);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				botonMostrar.setEnabled(true);

			}
		});

		botonMostrar.setEnabled(true);
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel_izquierda.add(rigidArea_1, BorderLayout.WEST);

		JPanel panel_abajo = new JPanel();
		getContentPane().add(panel_abajo, BorderLayout.SOUTH);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panel_abajo.add(rigidArea);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 700);
		controlSeguridad();

		botonAñadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (estaTodoRelleno()) {
					HashMap<String, Object> atributos = new HashMap<String, Object>();
					atributos.put("nombre", textoNombre.getText());
					atributos.put("apellidos", textoApellidos.getText());
					atributos.put("email", textoEmail.getText());
					atributos.put("idDNIUsuario", Integer.parseInt(textoDNI.getText()));
					atributos.put("telefono", Integer.parseInt(textoTelefono.getText()));
					atributos.put("password", textoContraseña.getPassword().toString());
					if (radioEsAdmin.isSelected())
						atributos.put("esAdmin", 1);
					else
						atributos.put("esAdmin", 0);
					String resultadoOperacion = ControladorUsuario.getInstance().añadirUsuario(atributos);
					JOptionPane.showMessageDialog(botonAñadir, resultadoOperacion, "Información de operación",
							JOptionPane.INFORMATION_MESSAGE);
					resetear();
				} else {
					JOptionPane.showMessageDialog(botonAñadir,
							"Debes rellenar todos los campos antes de añadir un nuevo usuario",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		botonEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textoNombre.getText().isEmpty()) {
					JOptionPane.showMessageDialog(botonAñadir, "Debes rellenar el nombre del usuario a eliminar",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				} else {
					int idUsuario = -1;
					for (int i = 0; i < listaCompleta.size(); i++) {
						if (((String) listaCompleta.get(i).get("nombre")).equals(textoNombre.getText())) {
							idUsuario = (int) listaCompleta.get(i).get("idDNIUsuario");
							i = listaCompleta.size();
						}
					}
					if (idUsuario == -1) {
						JOptionPane.showMessageDialog(botonEliminar,
								"El nombre del usuario no se corresponde con ninguno de la lista!",
								"Información de operación", JOptionPane.ERROR_MESSAGE);
					} else {
						String resultadoOperacion = ControladorUsuario.getInstance().eliminarUsuario(idUsuario);
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
					for (int i = 0; i < listaCompleta.size(); i++) {
						if (((String) listaCompleta.get(i).get("nombre")).equals(list.getSelectedValue())) {
							idSeleccionado = (int) listaCompleta.get(i).get("idDNIUsuario");
							i = listaCompleta.size();
						}
					}
					if (idSeleccionado == -1) {
						JOptionPane.showMessageDialog(botonModificar,
								"El id del usuario no se corresponde con ninguno de la lista!",
								"Información de operación", JOptionPane.ERROR_MESSAGE);
					} else {
						atributos.put("nombre", textoNombre.getText());
						atributos.put("apellidos", textoApellidos.getText());
						atributos.put("email", textoEmail.getText());
						atributos.put("idDNIUsuario", idSeleccionado);
						atributos.put("telefono", Integer.parseInt(textoTelefono.getText()));
						atributos.put("password", textoContraseña.getPassword().toString());
						if (radioEsAdmin.isSelected())
							atributos.put("esAdmin", 1);
						else
							atributos.put("esAdmin", 0);
						atributos.put("fechaContratacion", java.sql.Date.valueOf(textoFechaContrato.getText()));
						String resultadoOperacion = ControladorUsuario.getInstance().modificarUsuario(atributos);
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
				String usuarioSeleccionado = list.getSelectedValue();
				HashMap<String, Object> usuario = null;
				for (int i = 0; i < listaCompleta.size(); i++) {
					if (((String) listaCompleta.get(i).get("nombre")).equals(usuarioSeleccionado)) {
						usuario = listaCompleta.get(i);
						i = listaCompleta.size();
					}
				}
				if (usuario != null) {
					rellenar(usuario);
				} else {
					JOptionPane.showMessageDialog(botonMostrar,
							"Ha ocurrido un error al obtener información del cliente", "Información de operación",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	@Override
	public void resetear() {
		textoApellidos.setText("");
		textoDNI.setText("");
		textoEmail.setText("");
		textoNombre.setText("");
		textoTelefono.setText("");
		textoContraseña.setText("");
		radioEsAdmin.setSelected(false);
		idSeleccionado = -1;
		listaCompleta = ControladorUsuario.getInstance().consultarUsuarios();
		rellenarLista();
	}

	public void rellenar(HashMap<String, Object> usuario) {
		textoNombre.setText((String) usuario.get("nombre"));
		textoApellidos.setText((String) usuario.get("apellidos"));
		textoDNI.setText(((Integer) usuario.get("idDNIUsuario")).toString());
		textoEmail.setText((String) usuario.get("email"));
		textoTelefono.setText(((Integer) usuario.get("telefono")).toString());
		if (((Integer) usuario.get("esAdmin")).toString().equals("1")) {
			radioEsAdmin.setSelected(true);
		}
		textoFechaContrato.setText(((Date) usuario.get("fechaContratacion")).toString());

	}

	public void rellenarLista() {

		StringBuilder auxiliar = new StringBuilder();

		for (int i = 0; i < listaCompleta.size(); i++) {
			auxiliar.append(listaCompleta.get(i).get("nombre") + ":");
		}
		AbstractListModel<String> model = new AbstractListModel<String>() {
			String[] values = auxiliar.toString().split(":");

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		};
		list.setModel(model);

	}

	public void controlSeguridad() {
		if (!ControladorSeguridad.getInstance().getAdmin()) {
			botonEliminar.setEnabled(false);
			botonModificar.setEnabled(false);
			botonAñadir.setEnabled(false);
		} else {
			botonEliminar.setEnabled(true);
			botonModificar.setEnabled(true);
			botonAñadir.setEnabled(true);
		}
	}

	private boolean estaTodoRelleno() {
		boolean resultado = false;
		if (textoEmail.getText() != "" && textoNombre.getText() != "" && textoDNI.getText() != ""
				&& textoTelefono.getText() != "" && textoNombre.getText() != "" && textoApellidos.getText() != "") {
			resultado = true;
		}
		return resultado;
	}

	@Override
	public void arrancar() {
		setVisible(true);
		resetear();
		controlSeguridad();
	}

}
