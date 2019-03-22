package util;

import javax.swing.SwingUtilities;

import vista.InterfazPrincipal;
import vista.InterfazSeguridad;

public class TiendaMain {

	private int idAlmacen = 1;
	private String nombreTienda;
	private static TiendaMain objetoTienda = new TiendaMain();
	
	private final String AYUDA = 
			"Ayuda general de InfamousMusic, propiedad de Infamous Inc.\n"
			+ "Todos los derechos reservados\n "
			+ "\nEste programa permite manejar la base de datos \n"
			+ "de una tienda de contenido musical. \n"
			+ "Se almacenan usuarios del sistema, clientes, cupones y \n"
			+ "referencias al contenido musical del almacen.\n "
			+ "Para trabajar sobre las diferentes funcionalidades debe \n"
			+ "presionar el boton que corresponda con la funcionalidad \n"
			+ "deseada. El menu principal del programa permite acceder \n"
			+ "a las diferentes secciones para realizar las gestiones\n "
			+ "de forma intiuitiva con la una interfaz basada en el uso \n"
			+ "de botones\n\n";
	public static TiendaMain getInstance() {
		return objetoTienda;
	} 
	public String getAyuda() {
		return AYUDA;
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				InterfazSeguridad.getInstance().mostrar(InterfazPrincipal.getInstance());
				//InterfazPrincipal.getInstance().arrancar();
			}
		});
	}

	public int getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public String getNombreTienda() {
		return nombreTienda;
	}

	public void setNombreTienda(String nombreTienda) {
		this.nombreTienda = nombreTienda;
	}
	
}
