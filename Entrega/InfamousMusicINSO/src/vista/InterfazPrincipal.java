package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Interfaces.InterfaceGUI;
import util.CopiaSeguridad;
import util.TiendaMain;

public class InterfazPrincipal extends JFrame implements InterfaceGUI {

	private static InterfazPrincipal gui = new InterfazPrincipal();
	private final Action action = new SwingAction();
	private final String AYUDA = "En esta ventana estan implementados los accesos a las ventanas de gestion \n"
			+ "que conforman el programa.  Estas opciones son: \n"
			+ "Gestion de cupones, usuarios, almacen y clientes \n" + "\n¿Desea ver la ayuda general del programa? "
			+ "\nPresione el boton 'Si' en tal caso";

	public static InterfazPrincipal getInstance() {
		return gui;
	}

	private InterfazPrincipal() {

		setLocation(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Infamous Music");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setHgap(20);
		panel.setBackground(Color.WHITE);
		getContentPane().add(panel, BorderLayout.NORTH);

		JButton btnInterfazCupon = new JButton("Cupones");
		btnInterfazCupon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfazCupon cuponGUI = InterfazCupon.getInstance();
				// cuponGUI.addWindowListener(escuchadorVentana);
				cuponGUI.arrancar();
				setVisible(false);
			}
		});

		panel.add(btnInterfazCupon);
		JButton btnInterfazUsuarios = new JButton("Usuarios");
		btnInterfazUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfazUsuario usuariosGUI = InterfazUsuario.getInstance();
				// usuariosGUI.addWindowListener(escuchadorVentana);
				usuariosGUI.arrancar();
				setVisible(false);
			}
		});
		panel.add(btnInterfazUsuarios);

		JButton btnInterfazCliente = new JButton("Clientes");
		btnInterfazCliente.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InterfazCliente clienteGUI = InterfazCliente.getInstance();
				// almacenGUI.addWindowListener(escuchadorVentana);
				clienteGUI.arrancar();
				setVisible(false);

			}
		});
		panel.add(btnInterfazCliente);

		JButton btnInterfazAlmacen = new JButton("Almacen");
		btnInterfazAlmacen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InterfazAlmacen almacenGUI = InterfazAlmacen.getInstance();
				almacenGUI.setVisible(true);
				setVisible(false);

			}
		});
		panel.add(btnInterfazAlmacen);

		JButton botonTransacciones = new JButton("Transacciones");
		botonTransacciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InterfazTransaccion transaccionGUI = InterfazTransaccion.getInstance();
				transaccionGUI.arrancar();
				setVisible(false);
			}
		});
		panel.add(botonTransacciones);

		JButton botonPostventa = new JButton("Post-Venta");
		panel.add(botonPostventa);
		botonPostventa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InterfazPostVenta.getInstance().arrancar();
				setVisible(false);
			}
		});

		Panel panel_sur = new Panel();
		getContentPane().add(panel_sur, BorderLayout.SOUTH);

		JLabel labelImagen = new JLabel("");
		ImageIcon image = new ImageIcon("image/pic1.jpg");

		labelImagen.setIcon(new ImageIcon("sources/infamousBackground.png"));
		panel_sur.add(labelImagen);
		JMenuBar mnNewMenu = new JMenuBar();
		this.setJMenuBar(mnNewMenu);

		JMenu menu = new JMenu("Opciones");
		mnNewMenu.add(menu);

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
		menu.add(botonAyuda);

		JMenuItem botonCambiarUsuario = new JMenuItem("Cambiar usuario");
		botonCambiarUsuario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				InterfazSeguridad.getInstance().mostrar(InterfazPrincipal.getInstance());

			}
		});
		menu.add(botonCambiarUsuario);

		JMenuItem botonCopiaSeguridad = new JMenuItem("Hacer copia de seguridad");
		menu.add(botonCopiaSeguridad);

		botonCopiaSeguridad.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser file = new JFileChooser();
				file.showSaveDialog(null);
				String nombreFichero = file.getSelectedFile().getName();
				String rutaFichero = file.getSelectedFile().getAbsolutePath();
				System.out.println(rutaFichero.toString());
				String resultadoOperacion = CopiaSeguridad.backUp(rutaFichero);
				JOptionPane.showMessageDialog(botonCopiaSeguridad, resultadoOperacion, "Información de operación",
						JOptionPane.INFORMATION_MESSAGE);

			}
		});

		setSize(900, 800);
	}

	/*
	 * WindowListener escuchadorVentana =new WindowListener() {
	 * 
	 * @Override public void windowOpened(WindowEvent e) {
	 * //InterfazPrincipal.getInstance().setVisible(false); }
	 * 
	 * @Override public void windowIconified(WindowEvent e) { }
	 * 
	 * @Override public void windowDeiconified(WindowEvent e) { }
	 * 
	 * @Override public void windowDeactivated(WindowEvent e) { }
	 * 
	 * @Override public void windowClosing(WindowEvent e) {
	 * InterfazPrincipal.getInstance().setVisible(true);
	 * 
	 * }
	 * 
	 * @Override public void windowClosed(WindowEvent e) {
	 * InterfazPrincipal.getInstance().setVisible(true); }
	 * 
	 * @Override public void windowActivated(WindowEvent e) { } };
	 */

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}

	@Override
	public void arrancar() {
		setVisible(true);
	}

	@Override
	public void resetear() {

	}

	public void rellenar() {

	}

}
