package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Interfaces.InterfaceGUI;
import controlador.ControladorSeguridad;

public class InterfazSeguridad extends JFrame implements InterfaceGUI {
	private static InterfazSeguridad gui = new InterfazSeguridad();
	private JTextField textoUsuario;
	private JPasswordField textoContraseña;
	private JButton btnEntrar;
	private InterfaceGUI interfazQueSolicitaElTrabajo;

	private InterfazSeguridad() {
		setResizable(false);
		setLocation(500, 500);
		setTitle("Logeo");
		getContentPane().setLayout(new BorderLayout(20, 10));
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_4 = new JPanel();
		panel.add(panel_4);

		JLabel lblUsuario = new JLabel("      Usuario:");
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		panel_4.add(lblUsuario);

		textoUsuario = new JTextField();
		panel_4.add(textoUsuario);
		textoUsuario.setColumns(15);

		JPanel panel_5 = new JPanel();
		panel.add(panel_5);

		JLabel Clave = new JLabel("Contraseña:");
		panel_5.add(Clave);

		textoContraseña = new JPasswordField();
		textoContraseña.setColumns(15);
		panel_5.add(textoContraseña);

		JPanel panel_6 = new JPanel();
		panel.add(panel_6);

		btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comprobar();
			}
		});
		panel_6.add(btnEntrar);

		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(20, 50));
		getContentPane().add(panel_3, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);

	}

	public static InterfazSeguridad getInstance() {

		return gui;
	}

	public void mostrar(InterfaceGUI enEsteMomento) {
		setVisible(true);
		setInterfazActual(enEsteMomento);
		resetear();
	}

	private void setInterfazActual(InterfaceGUI enEsteMomento) {
		this.interfazQueSolicitaElTrabajo = enEsteMomento;
	}

	public void comprobar() {
		// boolean admin = radioAdmin.isSelected();
		String resultado = ControladorSeguridad.getInstance().comprobarLogeo(Integer.parseInt(textoUsuario.getText()),
				textoContraseña.getPassword().toString());
		if (!resultado.equals("Exito")) {
			JOptionPane.showMessageDialog(btnEntrar, resultado, "Error de loggeo", JOptionPane.ERROR_MESSAGE);
			resetear();
		} else {
			JOptionPane.showMessageDialog(btnEntrar, "            ¡Loggeo correcto!              ",
					"Información de logeo", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			interfazQueSolicitaElTrabajo.arrancar();
		}
	}

	public void resetear() {
		textoContraseña.setText("");
		textoUsuario.setText("");
	}

	@Override
	public void arrancar() {

	}
}
