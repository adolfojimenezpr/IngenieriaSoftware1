package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Interfaces.InterfaceGUI;
import controlador.ControladorSeguridad;
import controlador.ControladorTransaccion;
import util.TiendaMain;

public class InterfazTransaccion extends JFrame implements InterfaceGUI {
	private JTextField textoIDProducto;
	private JTextField textoCantidad;
	private JTextField textoCliente;
	private static InterfazTransaccion gui;
	// private ArrayList<String> listaProductosArray = new ArrayList<String>();
	private JTextPane listaProductos;
	private final String AYUDA = "En esta ventana estan implementadas las funcionalidades referentes \n"
			+ "a la gestion de las ventas y reservas.\n"
			+ "En el centro se muestran dos casillas en las que introducir la \n"
			+ "cantidad y el IDproducto a vender/reservar.\n"
			+ "Tras presionar el boton 'Añadir' el producto se mostrará junto a \n"
			+ "su cantidad en una lista de la transaccion a la derecha del programa\n"
			+ "como ciertas operaciones pueden requerir un cliente (una reserva) \n"
			+ "se puede establecer el identificador del cliente al que se le realiza\n"
			+ "la transaccion en el cuadro de texto habilitado para tal uso.\n"
			+ "Al finalizar la inserccion de nuevos articulos se presiona el boton\n"
			+ "'Vender' o 'Reservar' segun el caso. Despues se mostrará un dialogo\n" + "para gestionar el pago. \n\n"
			+ "\n¿Desea ver la ayuda general del programa? \n" + "Presione el boton 'Si' en tal caso\n\n";

	public static InterfazTransaccion getInstance() {
		if (gui == null)
			gui = new InterfazTransaccion();
		return gui;
	}

	private InterfazTransaccion() {
		setTitle("Ventas y reservas");
		// setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(500, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);

		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new BorderLayout(0, 0));

		listaProductos = new JTextPane();
		listaProductos.setSize(100, 400);
		listaProductos.setText("producto 1\nproducto 2\n");
		listaProductos.setEditable(false);
		panel_2.add(listaProductos);

		JPanel panel_3 = new JPanel();
		panel_3.setSize(40, 100);
		panel_2.add(panel_3, BorderLayout.SOUTH);

		JButton botonReservar = new JButton("Reservar");
		panel_3.add(botonReservar);

		JButton botonVender = new JButton("Vender");
		panel_3.add(botonVender);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 60));
		panel_2.add(rigidArea, BorderLayout.EAST);
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu botonMenuOpciones = new JMenu("Opciones");
		menuBar.add(botonMenuOpciones);

		JMenuItem botonAyuda = new JMenuItem("Ayuda");
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
		botonMenuOpciones.add(botonAyuda);

		JPanel panel_4 = new JPanel();
		getContentPane().add(panel_4, BorderLayout.WEST);
		panel_4.setLayout(new BorderLayout(20, 10));

		JPanel panel_7 = new JPanel();
		panel_4.add(panel_7, BorderLayout.NORTH);
		panel_7.setLayout(new BorderLayout(0, 0));

		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_5.getLayout();
		flowLayout_1.setVgap(20);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_7.add(panel_5, BorderLayout.NORTH);
		panel_5.setSize(50, 300);

		JLabel lblIdproducto = new JLabel("IdProducto");
		panel_5.add(lblIdproducto);

		textoIDProducto = new JTextField();
		panel_5.add(textoIDProducto);
		textoIDProducto.setColumns(15);

		JLabel lblNewLabel = new JLabel("cantidad");
		panel_5.add(lblNewLabel);

		textoCantidad = new JTextField();
		panel_5.add(textoCantidad);
		textoCantidad.setColumns(4);

		JButton botonAñadir = new JButton("Añadir");
		panel_5.add(botonAñadir);

		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setVgap(20);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_7.add(panel_6, BorderLayout.CENTER);
		panel_6.setSize(40, 300);

		JLabel lblIdcliente = new JLabel("IdCliente");
		panel_6.add(lblIdcliente);

		textoCliente = new JTextField();
		panel_6.add(textoCliente);
		textoCliente.setColumns(15);

		JPanel panel_8 = new JPanel();
		panel_7.add(panel_8, BorderLayout.SOUTH);

		JButton botonMenuPrincipal = new JButton("Menu principal");
		botonMenuPrincipal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetear();
				setVisible(false);
				InterfazPrincipal.getInstance().setVisible(true);

			}

		});
		botonReservar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String resultadoOperacion;

				if (estaTodoRelleno()) {
					if (!textoCliente.getText().isEmpty()) {

						// String resultadoOperacion =
						// ControladorReserva.getInstance().añadirReserva(IdDetalles, cantidad,
						// idCliente, idAlmacen); //TODO
						// JOptionPane.showMessageDialog(botonReservar, resultadoOperacion, "Información
						// de operación", JOptionPane.INFORMATION_MESSAGE);

						resultadoOperacion = ControladorTransaccion.getInstance()
								.finalizarReserva(Integer.parseInt(textoCliente.getText()));

						JOptionPane.showMessageDialog(botonReservar, resultadoOperacion, "Información de operación",
								JOptionPane.INFORMATION_MESSAGE);

						resetear();

					} else {
						JOptionPane.showMessageDialog(botonReservar,
								"Debes indicar un cliente para realizar la reserva", "Información de operación",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(botonReservar,
							"Debes introducir articulos antes de realizar una transaccion", "Información de operación",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		botonVender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (estaTodoRelleno()) {

					// String resultadoOperacion =
					// ControladorVenta.getInstance().añadirVenta(textoCliente.getText(),
					// ControladorVenta.----, "VENTA"); //TODO: abria que quitar el usuario (debido
					// a la clase sesion) y que el controlador venta
					// JOptionPane.showMessageDialog(botonVender, resultadoOperacion, "Información
					// de operación", JOptionPane.INFORMATION_MESSAGE);

					InterfazPago.getInstance().arrancar(getInstance());

				} else {
					JOptionPane.showMessageDialog(botonVender,
							"Debes introducir articulos antes de realizar una transaccion", "Información de operación",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		botonAñadir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String iD = textoIDProducto.getText();
				String cantidad = textoCantidad.getText();
				String resultadoOperacion;
				if (cantidad != "" && iD != "") {
					// listaProductosArray.add(iD);
					// listaProductosArray.add(cantidad);
					resultadoOperacion = ControladorTransaccion.getInstance().añadirItem(Integer.parseInt(iD),
							Integer.parseInt(cantidad));
					String aux = listaProductos.getText();
					aux += iD;
					aux += " --> ";
					aux += cantidad;
					aux += "\n";
					listaProductos.setText(aux);
					textoIDProducto.setText("");
					textoCantidad.setText("");

					JOptionPane.showMessageDialog(botonAñadir, resultadoOperacion, "Información de operación",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(botonAñadir, "Debes introducir el ID de un articulo y la cantidad",
							"Información de operación", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_8.add(botonMenuPrincipal);
		setSize(700, 500);
	}

	public void finalizarVenta() {

		int idCliente;
		String resultadoOperacion = "";

		if (textoCliente.getText().isEmpty()) {
			idCliente = -1;
		} else {
			idCliente = Integer.parseInt(textoCliente.getText());
		}

		resultadoOperacion = ControladorTransaccion.getInstance().finalizarVenta(idCliente);

		JOptionPane.showMessageDialog(null, resultadoOperacion, "Información de operación",
				JOptionPane.INFORMATION_MESSAGE);

		resetear();
	}

	@Override
	public void arrancar() {
		setVisible(true);
		resetear();
	}

	@Override
	public void resetear() {
		textoCantidad.setText("");
		textoCliente.setText("");
		textoIDProducto.setText("");
		listaProductos.setText("");

		// listaProductosArray = new ArrayList<String>();
		ControladorTransaccion.getInstance().añadirTransaccion(ControladorSeguridad.getInstance().getUsuarioActual());
	}

	private boolean estaTodoRelleno() {
		boolean resultado = false;
		// if (listaProductosArray.size() != 0) {
		if (!listaProductos.getText().equals("")) {
			resultado = true;
		}

		return resultado;
	}
}
