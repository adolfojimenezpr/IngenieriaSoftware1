package controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import dataObjetAccess.LineaVentaDAO;
import dataObjetAccess.PagoDAO;
import dataObjetAccess.ReservaDAO;
import dataObjetAccess.VentaDAO;
import modelo.DetallesProducto;
import modelo.LineaVenta;
import modelo.Pago;
import modelo.Producto;
import modelo.Reserva;
import modelo.Venta;

public class ControladorTransaccion {

	private Venta ventaActual = null;
	private ArrayList<Reserva> reservasActuales = null;
	private Reserva reservaActual = null;
	private Pago pagoActual = null;

	private static ControladorTransaccion instance = null;

	private ControladorTransaccion() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new ControladorTransaccion();
		}
	}

	public static ControladorTransaccion getInstance() {
		if (instance == null)
			createInstance();
		return instance;
	}

	public void añadirTransaccion(int idUsuario) {

		ventaActual = new Venta();
		reservasActuales = new ArrayList<Reserva>();
		reservaActual = new Reserva();
		pagoActual = new Pago();

		ventaActual.setIdUsuario(idUsuario);
	}

	public String añadirItem(int idProducto, int cantidad) {

		String mensaje = "";

		int cantidadDisponible = ControladorAlmacen.getInstance().obtenerProducto(idProducto).getCantidadDisponible();

		if (cantidadDisponible < cantidad) {
			mensaje = "No se pueden añadir tantos productos.\n Se añadiran los disponibles, que son "
					+ cantidadDisponible;
			cantidad = cantidadDisponible;
		} else {
			mensaje = cantidad + " productos añadidos correctamente a la lista";
		}
		ventaActual.añadirItem(idProducto, cantidad);

		reservaActual.setIdProducto(idProducto);
		reservaActual.setCantidad(cantidad);

		Reserva reservaAux = new Reserva();

		reservaAux.setIdProducto(reservaActual.getIdProducto());
		reservaAux.setCantidad(reservaActual.getCantidad());

		reservasActuales.add(reservaAux);

		return mensaje;
	}

	public float calcularPrecio(int idCupon) {

		float descuento = 0;
		float total;

		if (idCupon > 0) {
			descuento = ControladorCupon.getInstance().obtenerCupon(idCupon).getDescuento();
		} else {
			idCupon = -1;
		}

		ventaActual.setIdCupon(idCupon);

		total = ventaActual.calcularTotal(descuento);

		ventaActual.setPrecioTotal(total);
		return total;
	}

	public float realizarPago(float importeEntregado) {

		float importeVuelta;

		importeVuelta = importeEntregado - ventaActual.getPrecioTotal();

		pagoActual.setImporteVenta(ventaActual.getPrecioTotal());
		pagoActual.setImporteEntregado(importeEntregado);
		pagoActual.setImporteVuelta(importeVuelta);

		return importeVuelta;
	}

	public String finalizarVenta(int idCliente) {

		System.out.println(ventaActual.getIdUsuario());
		String mensaje = "";
		boolean exito = true;
		PagoDAO pagoDAO = new PagoDAO();
		VentaDAO ventaDAO = new VentaDAO();
		LineaVentaDAO lineaVentaDAO = new LineaVentaDAO();
		ControladorAlmacen controladorAlmacen = ControladorAlmacen.getInstance();

		ArrayList<LineaVenta> coleccionLV = null;
		LineaVenta lineaVentaActual = null;

		int idPago;
		int idVenta = -1;

		idPago = pagoDAO.create(pagoActual);

		if (idPago == -1) {
			mensaje += "Error en la creacion del pago en la BBDD";
			exito = false;
		} else {
			ventaActual.setIdCliente(idCliente);
			ventaActual.setIdPago(idPago);

			idVenta = ventaDAO.create(ventaActual);

			if (idVenta == -1) {
				mensaje += "Error en la creacion de la venta en la BBDD";
				exito = false;
			} else {
				coleccionLV = ventaActual.getColeccionLV();

				for (int i = 0; i < coleccionLV.size(); i++) {

					lineaVentaActual = coleccionLV.get(i);
					controladorAlmacen.venderProductos(lineaVentaActual.getIdProducto(),
							lineaVentaActual.getCantidad());

					lineaVentaActual.setIdVenta(idVenta);

					if (lineaVentaDAO.create(lineaVentaActual) == -1) {
						mensaje += "Error en la creacion de lineas de venta en la BBDD)";
						exito = false;
						break;
					}
				}
			}
		}
		ventaActual = null;
		pagoActual = null;
		reservasActuales = null;
		reservaActual = null;
		instance = null;

		if (exito) {
			mensaje += "Venta realizada con exito con id: " + idVenta;
		}

		return mensaje;
	}

	public String finalizarReserva(int idCliente) {

		String mensaje = "";
		boolean exito = true;
		ReservaDAO reservaDAO = new ReservaDAO();
		ControladorAlmacen controladorAlmacen = ControladorAlmacen.getInstance();

		if (idCliente > 0) {
			for (int i = 0; i < reservasActuales.size(); i++) {

				reservasActuales.get(i).setIdCliente(idCliente);
				controladorAlmacen.reservarProductos(reservasActuales.get(i).getIdProducto(),
						reservasActuales.get(i).getCantidad());
				// reservasActuales.get(i).setFechaReserva(AHORA);
				if (reservaDAO.create(reservasActuales.get(i)) == -1) {
					mensaje += "Error en la creacion de reservas";
					exito = false;
					break;
				}
			}
			if (exito) {
				mensaje += "Reservas creadas exitosamente";
			}

			ventaActual = null;
			pagoActual = null;
			reservasActuales = null;
			reservaActual = null;
			instance = null;

		} else {
			mensaje += "Se ha de añadir un idCliente";
		}

		return mensaje;
	}

	public Venta obtenerVenta(int idVenta) {

		VentaDAO ventaDAO = new VentaDAO();

		return ventaDAO.read(idVenta);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> consultarVentas() {

		VentaDAO ventaDAO = new VentaDAO();
		Venta venta = null;

		HashMap<String, Object> map = new HashMap<String, Object>();

		ArrayList<HashMap<String, Object>> listaEnviada = new ArrayList<HashMap<String, Object>>();
		ArrayList<Venta> listaRecibida = ventaDAO.readAll();

		Iterator<Venta> iterador = listaRecibida.iterator();

		while (iterador.hasNext()) {

			venta = (Venta) iterador.next();

			map.put("idVenta", venta.getIdVenta());
			map.put("idUsuario", venta.getIdUsuario());
			map.put("idCliente", venta.getIdCliente());
			map.put("idPago", venta.getIdPago());
			map.put("idCupon", venta.getIdCupon());
			map.put("precioTotal", venta.getPrecioTotal());

			listaEnviada.add((HashMap<String, Object>) map.clone());

			map.clear();
		}

		return listaEnviada;
	}

	public LineaVenta obtenerLineaVenta(int idLineaVenta) {

		LineaVentaDAO lineaVentaDAO = new LineaVentaDAO();

		return lineaVentaDAO.read(idLineaVenta);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> consultarLineasVenta(int idVenta) {

		LineaVentaDAO lineaVentaDAO = new LineaVentaDAO();
		LineaVenta lineaVenta = null;

		Producto producto;
		DetallesProducto detallesProducto;

		HashMap<String, Object> map = new HashMap<String, Object>();

		ArrayList<HashMap<String, Object>> listaEnviada = new ArrayList<HashMap<String, Object>>();
		ArrayList<LineaVenta> listaRecibida = null;

		if (idVenta == 0) {

			listaRecibida = lineaVentaDAO.readAll();

		} else {

			listaRecibida = lineaVentaDAO.readAllWhere(idVenta);

		}

		Iterator<LineaVenta> iterador = listaRecibida.iterator();

		while (iterador.hasNext()) {
			lineaVenta = (LineaVenta) iterador.next();

			map.put("idLineaVenta", lineaVenta.getIdLineaVenta());
			map.put("idVenta", lineaVenta.getIdVenta());
			map.put("idProducto", lineaVenta.getIdProducto());
			map.put("cantidad", lineaVenta.getCantidad());
			map.put("cantidadDevuelta", lineaVenta.getCantidadDevuelta());

			producto = ControladorAlmacen.getInstance().obtenerProducto(lineaVenta.getIdProducto());

			map.put("idDetalles", producto.getIdDetalles());
			map.put("idAlmacen", producto.getIdAlmacen());
			map.put("cantidadDisponible", producto.getCantidadDisponible());
			map.put("cantidadVendida", producto.getCantidadVendida());
			map.put("cantidadReservada", producto.getCantidadReservada());

			detallesProducto = ControladorAlmacen.getInstance().obtenerDetalles(producto.getIdDetalles());

			map.put("artista", detallesProducto.getArtista());
			map.put("album", detallesProducto.getAlbum());
			map.put("estiloMusical", detallesProducto.getEstiloMusical());
			map.put("precio", detallesProducto.getPrecio());
			map.put("fechaLanzamiento", detallesProducto.getFechaLanzamiento());

			listaEnviada.add((HashMap<String, Object>) map.clone());

			map.clear();
		}

		return listaEnviada;
	}

}
