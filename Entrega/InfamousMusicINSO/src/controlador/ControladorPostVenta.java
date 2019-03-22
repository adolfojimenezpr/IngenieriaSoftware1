package controlador;

import java.util.ArrayList;
import java.util.HashMap;

import dataObjetAccess.DevolucionDAO;
import dataObjetAccess.LineaVentaDAO;
import dataObjetAccess.ProductoDAO;
import modelo.DetallesProducto;
import modelo.Devolucion;
import modelo.LineaVenta;
import modelo.Producto;

public class ControladorPostVenta {

	private Devolucion devolucionActual = null;

	private static ControladorPostVenta instance = null;

	private ControladorPostVenta() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new ControladorPostVenta();
		}
	}

	public static ControladorPostVenta getInstance() {
		if (instance == null)
			createInstance();
		return instance;
	}

	public ArrayList<HashMap<String, Object>> seleccionarVentaDevolucion(int idVenta) {

		ControladorTransaccion controladorTransicion = ControladorTransaccion.getInstance();

		return controladorTransicion.consultarLineasVenta(idVenta);
	}

	public String devolverProducto(int idLineaVenta) {

		String mensaje = "";
		float importeADevolver = 0;
		
		DevolucionDAO devolucionDAO = new DevolucionDAO();
		ProductoDAO productoDAO = new ProductoDAO();
		LineaVentaDAO lineaVentaDAO = new LineaVentaDAO();

		ControladorAlmacen controladorAlmacen = ControladorAlmacen.getInstance();
		ControladorTransaccion controladorTransaccion = ControladorTransaccion.getInstance();

		LineaVenta lineaVenta = controladorTransaccion.obtenerLineaVenta(idLineaVenta);
		Producto producto = controladorAlmacen.obtenerProducto(lineaVenta.getIdProducto());
		DetallesProducto detallesProducto = controladorAlmacen.obtenerDetalles(producto.getIdDetalles());

		devolucionActual = new Devolucion();

		if (lineaVenta.getCantidad() > lineaVenta.getCantidadDevuelta()) {

			lineaVenta.setCantidadDevuelta(lineaVenta.getCantidadDevuelta() + 1);
			producto.setCantidadDisponible(producto.getCantidadDisponible() + 1);
			producto.setCantidadVendida(producto.getCantidadVendida() - 1);

			devolucionActual.setIdVenta(lineaVenta.getIdVenta());
			devolucionActual.setIdProducto(lineaVenta.getIdProducto());

			importeADevolver = detallesProducto.getPrecio();

			lineaVentaDAO.update(lineaVenta);
			productoDAO.update(producto);
			devolucionDAO.create(devolucionActual);

			mensaje += "Devolucion realizada con exito. Se ha de devolver " + importeADevolver + " euros al cliente.";
		}else {
			mensaje += "Error en la devolucion, ya se han devuleto todos los productos de la Linea de Venta.";

		}
		devolucionActual = null;

		return mensaje;
	}

}
