package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Interfaces.InterfaceGUI;
import controlador.ControladorTransaccion;
import util.TiendaMain;

public class InterfazPago extends JFrame {

	private static InterfazPago gui = null;

	private final String AYUDA = "En esta ventana estan implementadas las funcionalidades referentes \n"
			+ "a la gestion post-venta.\n" + "A la izquierda se muestra una lista con los productos involucrados\n"
			+ "en la venta indicada en el campo 'id Venta'. Si selecciona un producto podrá proceder\n"
			+ "a devolver ese producto y se abrirá una ventana para el pago. \n"
			+ "Para regresar al menu principal debe presionar el boton situado\n"
			+ "a la derecha llamado 'Menu principal' \n\n" + "\n¿Desea ver la ayuda general del programa? \n"
			+ "Presione el boton 'Si' en tal caso\n\n";
	private JTextField textoImporteTotal;
	private JTextField textoImportePuesto;
	private JTextField textoCupon;
	private InterfaceGUI invocador;

	public static InterfazPago getInstance() {
		if (gui == null)
			gui = new InterfazPago();
		return gui;
	}

	private InterfazPago() {

		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnNewMenu = new JMenu("Opciones");
		menuBar.add(mnNewMenu);

		JMenuItem botonAyuda = new JMenuItem("Ayuda");
		mnNewMenu.add(botonAyuda);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);

		JLabel lblImporteTotal = new JLabel("Importe total:");
		panel_1.add(lblImporteTotal);

		textoImporteTotal = new JTextField();
		textoImporteTotal.setEditable(false);
		panel_1.add(textoImporteTotal);
		textoImporteTotal.setColumns(10);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		panel.add(panel_2);

		JLabel lblImportePuesto = new JLabel("Importe puesto:");
		panel_2.add(lblImportePuesto);

		textoImportePuesto = new JTextField();
		panel_2.add(textoImportePuesto);
		textoImportePuesto.setColumns(10);
		setSize(300, 300);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);

		JLabel lblCupon = new JLabel("idCupon:");
		panel_3.add(lblCupon);

		textoCupon = new JTextField();
		panel_3.add(textoCupon);
		textoCupon.setColumns(10);
		setSize(300, 300);

		int idCupon = -1;
		textoImporteTotal.setText(String.valueOf(ControladorTransaccion.getInstance().calcularPrecio(idCupon)));

		JPanel panel_4 = new JPanel();
		panel.add(panel_4);

		JButton botonCupon = new JButton("Añadir cupon");
		panel_4.add(botonCupon);

		JButton botonPago = new JButton("Realizar pago");
		panel_4.add(botonPago);
		botonPago.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// calcular importe
				float importeADevolver = 0;
				importeADevolver = ControladorTransaccion.getInstance()
						.realizarPago(Float.parseFloat(textoImportePuesto.getText()));
				String resultado = "Importe a devolver: " + importeADevolver;
				JOptionPane.showMessageDialog(botonPago, resultado, "Información de pago", JOptionPane.OK_OPTION);

				setVisible(false);

				InterfazTransaccion.getInstance().finalizarVenta();
				invocador.arrancar();
			}
		});
		botonCupon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textoImporteTotal.setText(String.valueOf(
						ControladorTransaccion.getInstance().calcularPrecio(Integer.parseInt(textoCupon.getText()))));

			}
		});
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

	public void arrancar(InterfaceGUI invocador) {
		setVisible(true);
		this.invocador = invocador;
	}
}
