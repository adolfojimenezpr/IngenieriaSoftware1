package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import Interfaces.InterfaceGUI;
import controlador.ControladorPostVenta;
import util.TiendaMain;

public class InterfazPostVenta extends JFrame implements InterfaceGUI {
	private JTextField textoidVenta;
	private static InterfazPostVenta gui;
	private ArrayList<HashMap<String, Object>> listaCompleta;
	private JList<String> list;
	private int idVentaActual = -1;

	private final String AYUDA = "En esta ventana estan implementadas las funcionalidades referentes \n"
			+ "a la gestion post-venta.\n" + "A la izquierda se muestra una lista con los productos involucrados\n"
			+ "en la venta indicada en el campo 'id Venta'. Si selecciona un producto podrá proceder\n"
			+ "a devolver ese producto y se abrirá una ventana para el pago. \n"
			+ "Para regresar al menu principal debe presionar el boton situado\n"
			+ "a la derecha llamado 'Menu principal' \n\n" + "\n¿Desea ver la ayuda general del programa? \n"
			+ "Presione el boton 'Si' en tal caso\n\n";
	private JButton botonBuscar;

	public static InterfazPostVenta getInstance() { // iniciacion perezosa
		if (gui == null)
			gui = new InterfazPostVenta();
		return gui;

	}

	private InterfazPostVenta() {
		setTitle("Gestión post-venta");
		setLocation(500, 500);
		setResizable(false);
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
		panel_derecha.setPreferredSize(new Dimension(180, 100));
		getContentPane().add(panel_derecha, BorderLayout.EAST);
		panel_derecha.setLayout(new BoxLayout(panel_derecha, BoxLayout.Y_AXIS));

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_3);

		JButton botonDevolver = new JButton("Devolver");
		panel_derecha.add(botonDevolver);
		botonDevolver.setEnabled(false);
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_1);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel_derecha.add(verticalStrut_2);

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
				textoidVenta.setText("");
				idVentaActual = -1;
				resetear();
				setVisible(false);
				InterfazPrincipal.getInstance().setVisible(true);
			}
		});
		panel_4.add(botonMenu, BorderLayout.CENTER);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_4.add(horizontalStrut, BorderLayout.EAST);
		panel_derecha.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { botonDevolver }));

		Panel panel_central = new Panel();
		getContentPane().add(panel_central, BorderLayout.CENTER);
		panel_central.setLayout(new GridLayout(0, 1, 0, 0));

		Panel panel = new Panel();
		panel_central.add(panel);

		JLabel lblidVenta = new JLabel("idVenta:");
		panel.add(lblidVenta);
		lblidVenta.setVerticalAlignment(SwingConstants.TOP);

		textoidVenta = new JTextField();
		panel.add(textoidVenta);
		textoidVenta.setColumns(20);

		botonBuscar = new JButton("Buscar");
		panel.add(botonBuscar);

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
				botonDevolver.setEnabled(true);
			}
		});
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel_izquierda.add(rigidArea_1, BorderLayout.WEST);

		JPanel panel_abajo = new JPanel();
		getContentPane().add(panel_abajo, BorderLayout.SOUTH);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		panel_abajo.add(rigidArea);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 700);

		botonBuscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (estaTodoRelleno()) {
					idVentaActual = Integer.valueOf(textoidVenta.getText());
					resetear();
				} else {
					JOptionPane.showMessageDialog(botonBuscar, "Debes introducir el id de la venta",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		botonDevolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String resultadoOperacion;
				String productoSeleccionado = list.getSelectedValue();

				HashMap<String, Object> lineaVentaSeleccionada = null;

				for (int i = 0; i < listaCompleta.size(); i++) {
					if (((String) listaCompleta.get(i).get("album")).equals(productoSeleccionado)) {
						lineaVentaSeleccionada = listaCompleta.get(i);
						i = listaCompleta.size();
					}
				}

				if (lineaVentaSeleccionada != null) {
					resultadoOperacion = ControladorPostVenta.getInstance()
							.devolverProducto((int) lineaVentaSeleccionada.get("idLineaVenta"));
					JOptionPane.showMessageDialog(botonDevolver, resultadoOperacion, "Información de operación",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(botonDevolver,
							"Ha ocurrido un error al obtener información del producto", "Información de operación",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	@Override
	public void resetear() {
		// textoidVenta.setText("");
		listaCompleta = ControladorPostVenta.getInstance().seleccionarVentaDevolucion(idVentaActual);
		rellenarLista();
	}

	/*
	 * public void rellenar(HashMap<String, Object> venta) {
	 * textoidVenta.setText((String) venta.get("idVenta")); }
	 */

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
		if (textoidVenta.getText() != "") {
			resultado = true;
		}
		return resultado;
	}

	@Override
	public void arrancar() {
		setVisible(true);
		resetear();
	}

}
