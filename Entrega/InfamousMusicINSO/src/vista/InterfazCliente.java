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
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import Interfaces.InterfaceGUI;
import controlador.ControladorAlmacen;
import controlador.ControladorCliente;
import util.TiendaMain;

import javax.swing.JMenu;

public class InterfazCliente extends JFrame implements InterfaceGUI {

	private JTextField textoEmail;
	private JTextField textoNombre;
	private JTextField textoDNI;
	private JTextField textoTelefono;
	private int idSeleccionado = -1;

	private static InterfazCliente gui;
	private ArrayList<HashMap<String, Object>> listaCompleta;

	private JTextField textoApellidos;
	private final String AYUDA = "En esta ventana estan implementadas las funcionalidades referentes \n"
			+ "a la gestion de clientes.\n" + "A la izquierda se muestra una lista con los clientes registrados \n"
			+ "en el sistema. A la derecha estan dispuestos verticalmente unos \n"
			+ "botones que permiten realizar operaciones sobre el cliente \n"
			+ "reflejado por sus atributos en el centro de la pantalla. \n"
			+ "Estas opciones son: [Mostrar, Añadir, Eliminar, Modificar]. \n"
			+ "Para regresar al menu principal debe presionar el boton situado\n"
			+ "a la derecha llamado 'Menu principal' \n\n" + "\n¿Desea ver la ayuda general del programa? \n"
			+ "Presione el boton 'Si' en tal caso\n\n";
	private JList<String> list;
	private JButton botonModificar;

	public static InterfazCliente getInstance() { // iniciacion perezosa
		if (gui == null)
			gui = new InterfazCliente();
		return gui;

	}

	private InterfazCliente() {
		setTitle("Gestión clientes");
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
		panel_derecha.setPreferredSize(new Dimension(200, 100));
		getContentPane().add(panel_derecha, BorderLayout.EAST);
		panel_derecha.setLayout(new BoxLayout(panel_derecha, BoxLayout.Y_AXIS));

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_3);

		JButton botonMostrar = new JButton("Mostrar");
		botonMostrar.setHorizontalAlignment(SwingConstants.LEFT);
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

		JPanel panel_5 = new JPanel();
		panel_derecha.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 100));
		panel_5.add(rigidArea_2, BorderLayout.NORTH);

		JButton botonMenu = new JButton("Menu principal");
		botonMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetear();
				setVisible(false);
				InterfazPrincipal.getInstance().setVisible(true);
			}
		});
		panel_5.add(botonMenu, BorderLayout.CENTER);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_5.add(horizontalStrut, BorderLayout.EAST);
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
		textoNombre.setColumns(20);

		Panel panel_4 = new Panel();
		panel_central.add(panel_4);

		JLabel ts = new JLabel("Apellidos:");
		ts.setVerticalAlignment(SwingConstants.TOP);
		panel_4.add(ts);

		textoApellidos = new JTextField();
		textoApellidos.setColumns(20);
		panel_4.add(textoApellidos);

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

		Panel panel_3 = new Panel();
		panel_central.add(panel_3);

		JLabel lblFechaContrato = new JLabel("Telefono:");
		panel_3.add(lblFechaContrato);

		textoTelefono = new JTextField();
		panel_3.add(textoTelefono);
		textoTelefono.setColumns(15);

		Panel panel_izquierda = new Panel();
		getContentPane().add(panel_izquierda, BorderLayout.WEST);
		panel_izquierda.setLayout(new BorderLayout(10, 10));
		panel_izquierda.setPreferredSize(new Dimension(200, 100));
		list = new JList();
		list.setValueIsAdjusting(true);
		list.setToolTipText("Esta lista contiene los clientees creados");
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "cliente1", "cliente2", "cliente3" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setVisibleRowCount(10);
		panel_izquierda.add(list);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

			}
		});

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel_izquierda.add(rigidArea_1, BorderLayout.WEST);

		JPanel panel_abajo = new JPanel();
		getContentPane().add(panel_abajo, BorderLayout.SOUTH);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panel_abajo.add(rigidArea);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(700, 500);

		botonAñadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (estaTodoRelleno()) {
					HashMap<String, Object> atributos = new HashMap<String, Object>();
					atributos.put("nombre", textoNombre.getText());
					atributos.put("apellidos", textoApellidos.getText());
					atributos.put("email", textoEmail.getText());
					atributos.put("idDNICliente", Integer.parseInt(textoDNI.getText()));
					atributos.put("telefono", Integer.parseInt(textoTelefono.getText()));
					String resultadoOperacion = ControladorCliente.getInstance().añadirCliente(atributos);
					JOptionPane.showMessageDialog(botonAñadir, resultadoOperacion, "Información de operación",
							JOptionPane.INFORMATION_MESSAGE);
					resetear();
				} else {
					JOptionPane.showMessageDialog(botonAñadir,
							"Debes rellenar todos los campos antes de añadir un nuevo cliente",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		botonEliminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (textoNombre.getText() == "") {
					JOptionPane.showMessageDialog(botonAñadir, "Debes rellenar el nombre del cliente a eliminar",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				} else {
					int idCLiente = -1;
					for (int i = 0; i < listaCompleta.size(); i++) {
						if (((String) listaCompleta.get(i).get("nombre")).equals(textoNombre.getText())) {
							idCLiente = (int) listaCompleta.get(i).get("idDNICliente");
							i = listaCompleta.size();
						}
					}
					if (idCLiente == -1) {
						JOptionPane.showMessageDialog(botonEliminar,
								"El nombre del producto no se corresponde con ninguno de la lista!",
								"Información de operación", JOptionPane.ERROR_MESSAGE);
					} else {
						String resultadoOperacion = ControladorCliente.getInstance().eliminarCliente(idCLiente);
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
							idSeleccionado = (int) listaCompleta.get(i).get("idDNICliente");
							i = listaCompleta.size();
						}
					}
					if (idSeleccionado == -1) {
						JOptionPane.showMessageDialog(botonModificar,
								"El id del cliente no se corresponde con ninguno de la lista!",
								"Información de operación", JOptionPane.ERROR_MESSAGE);
					} else {
						atributos.put("nombre", textoNombre.getText());
						atributos.put("apellidos", textoApellidos.getText());
						atributos.put("email", textoEmail.getText());
						atributos.put("idDNICliente", idSeleccionado);
						atributos.put("telefono", Integer.parseInt(textoTelefono.getText()));
						String resultadoOperacion = ControladorCliente.getInstance().modificarCliente(atributos);
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
				String clienteSeleccionado = list.getSelectedValue();
				HashMap<String, Object> cliente = null;
				for (int i = 0; i < listaCompleta.size(); i++) {
					if (((String) listaCompleta.get(i).get("nombre")).equals(clienteSeleccionado)) {
						cliente = listaCompleta.get(i);
						i = listaCompleta.size();
					}
				}
				if (cliente != null) {
					rellenar(cliente);
				} else {
					JOptionPane.showMessageDialog(botonMostrar,
							"Ha ocurrido un error al obtener información del cliente", "Información de operación",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	@Override
	public void arrancar() {
		setVisible(true);
		resetear();
	}

	@Override
	public void resetear() {
		textoApellidos.setText("");
		textoDNI.setText("");
		textoEmail.setText("");
		textoNombre.setText("");
		textoTelefono.setText("");
		idSeleccionado = -1;

		listaCompleta = ControladorCliente.getInstance().consultarClientes();
		rellenarLista();
	}

	private boolean estaTodoRelleno() {
		boolean resultado = false;
		if (textoEmail.getText() != "" && textoNombre.getText() != "" && textoDNI.getText() != ""
				&& textoTelefono.getText() != "" && textoNombre.getText() != "" && textoApellidos.getText() != "") {
			resultado = true;
		}
		return resultado;
	}

	public void rellenar(HashMap<String, Object> cliente) {
		textoNombre.setText((String) cliente.get("nombre"));
		textoApellidos.setText((String) cliente.get("apellidos"));
		textoDNI.setText(((Integer) cliente.get("idDNICliente")).toString());
		textoEmail.setText((String) cliente.get("email"));
		textoTelefono.setText(((Integer) cliente.get("telefono")).toString());

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
}
