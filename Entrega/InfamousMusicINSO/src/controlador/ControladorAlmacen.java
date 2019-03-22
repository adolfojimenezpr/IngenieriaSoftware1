package controlador;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import dataObjetAccess.AlmacenDAO;
import dataObjetAccess.DetallesProductoDAO;
import dataObjetAccess.ProductoDAO;
import modelo.Almacen;
import modelo.DetallesProducto;
import modelo.Producto;

public class ControladorAlmacen {

	private static ControladorAlmacen instance = null;

	private ControladorAlmacen() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new ControladorAlmacen();
		}
	}

	public static ControladorAlmacen getInstance() {
		if (instance == null)
			createInstance();
		return instance;
	}

	public void añadirAlmacen(HashMap<String, Object> atributos) {

		AlmacenDAO almacenDAO = new AlmacenDAO();

		Almacen almacen = new Almacen((int) atributos.get("idAlmacen"), (String) atributos.get("nombreAlmacen"));

		almacenDAO.create(almacen);
	}

	public String añadirProductos(HashMap<String, Object> atributos, int cantidad) {

		int idDetalles = (int) atributos.get("idDetalles");
		int idProducto = (int) atributos.get("idProducto");
		int idAlmacen = (int) atributos.get("idAlmacen");

		if (idDetalles == -1) {
			DetallesProductoDAO detallesProductoDAO = new DetallesProductoDAO();

			DetallesProducto detallesProducto = new DetallesProducto(-1, (String) atributos.get("artista"),
					(String) atributos.get("album"), (String) atributos.get("estiloMusical"),
					(float) atributos.get("precio"), (Date) atributos.get("fechaLanzamiento"));

			idDetalles = detallesProductoDAO.create(detallesProducto);

			if (idDetalles == -1) {
				return "Error al añadir los detalles del producto en la BBDD";
			}
		}

		ProductoDAO productoDAO = new ProductoDAO();
		Producto producto;

		if (idProducto == -1) {
			producto = new Producto(idProducto, idDetalles, idAlmacen, cantidad, 0, 0);

			if (productoDAO.create(producto) == -1) {
				return "Error al añadir los productos en la BBDD";
			} else {
				return "Los productos han sido creados con exito";
			}
		} else {
			producto = productoDAO.read(idProducto);

			producto.setCantidadDisponible(producto.getCantidadDisponible() + cantidad);

			if (!productoDAO.update(producto)) {
				return "Error al añadir los productos en la BBDD";
			} else {
				return "Se han añadido los " + cantidad + " productos a la base de datos. Cantidad resultante: "
						+ producto.getCantidadDisponible();
			}

		}
	}

	public String eliminarProductos(int idProducto, int cantidad) {

		int cantidadDisponible = 0;
		String mensaje = "";

		ProductoDAO productoDAO = new ProductoDAO();
		Producto producto = productoDAO.read(idProducto);

		cantidadDisponible = producto.getCantidadDisponible();

		if (cantidadDisponible < cantidad) {
			cantidadDisponible = 0;
			mensaje = "Quiere eliminar más productos de los actualmente disponibles. Se eliminaran todos";
		} else {
			cantidadDisponible = cantidadDisponible - cantidad;
		}

		producto.setCantidadDisponible(cantidadDisponible);

		if (!productoDAO.update(producto)) {
			mensaje += "Error al eliminar los productos de la BBDD";
		} else {
			mensaje += "Se han eliminado correctamente " + cantidad + " productos. Total disponible: "
					+ cantidadDisponible;
		}

		return mensaje;
	}

	public String modificarProducto(HashMap<String, Object> atributos) {

		ProductoDAO productoDAO = new ProductoDAO();

		Producto producto = productoDAO.read(atributos.get("idProducto"));

		producto.setIdDetalles((int) atributos.get("idDetalles"));
		producto.setIdAlmacen((int) atributos.get("idAlmacen"));
		producto.setCantidadDisponible((int) atributos.get("cantidadDisponible"));
		producto.setCantidadVendida((int) atributos.get("cantidadVendida"));
		producto.setCantidadReservada((int) atributos.get("cantidadReservada"));

		if (!productoDAO.update(producto)) {
			return "Error al modificar el producto en la BBDD";
		} else {
			return "El producto ha sido modificado con exito";
		}
	}

	public String modificarDetallesProducto(HashMap<String, Object> atributos) {

		DetallesProductoDAO detallesProductoDAO = new DetallesProductoDAO();

		DetallesProducto detallesProducto = detallesProductoDAO.read(atributos.get("idDetalles"));

		detallesProducto.setArtista((String) atributos.get("artista"));
		detallesProducto.setAlbum((String) atributos.get("album"));
		detallesProducto.setEstiloMusical((String) atributos.get("estiloMusical"));
		detallesProducto.setPrecio((float) atributos.get("precio"));
		detallesProducto.setFechaLanzamiento((Date) atributos.get("fechaLanzamiento"));

		if (!detallesProductoDAO.update(detallesProducto)) {
			return "Error al modificar los detalles del producto en la BBDD";
		} else {
			return "Los detalles del producto han sido modificados con exito";
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> consultarProductos(int idAlmacen) {

		ProductoDAO productoDAO = new ProductoDAO();
		Producto producto = null;
		DetallesProductoDAO detallesProductoDAO = new DetallesProductoDAO();
		DetallesProducto detallesProducto = null;

		HashMap<String, Object> map = new HashMap<String, Object>();

		ArrayList<HashMap<String, Object>> listaEnviada = new ArrayList<HashMap<String, Object>>();
		ArrayList<Producto> listaRecibida = null;

		if (idAlmacen == 0) {
			listaRecibida = productoDAO.readAll();
		} else {
			listaRecibida = productoDAO.readAllWhere(idAlmacen);
		}

		Iterator<Producto> iterador = listaRecibida.iterator();

		while (iterador.hasNext()) {
			producto = (Producto) iterador.next();

			map.put("idProducto", producto.getIdProducto());
			map.put("idDetalles", producto.getIdDetalles());
			map.put("idAlmacen", producto.getIdAlmacen());
			map.put("cantidadDisponible", producto.getCantidadDisponible());
			map.put("cantidadVendida", producto.getCantidadVendida());
			map.put("cantidadReservada", producto.getCantidadReservada());

			detallesProducto = detallesProductoDAO.read(producto.getIdDetalles());

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

	public Almacen obtenerAlmacen(int idAlmacen) {

		AlmacenDAO almacenDAO = new AlmacenDAO();

		return almacenDAO.read(idAlmacen);
	}

	public Producto obtenerProducto(int idProducto) {

		ProductoDAO productoDAO = new ProductoDAO();

		return productoDAO.read(idProducto);
	}

	public DetallesProducto obtenerDetalles(int idDetalles) {

		DetallesProductoDAO detallesProductoDAO = new DetallesProductoDAO();

		return detallesProductoDAO.read(idDetalles);
	}

	public String venderProductos(int idProducto, int cantidad) {

		int cantidadDisponible, cantidadVendida = 0;
		String mensaje = "";

		ProductoDAO productoDAO = new ProductoDAO();
		Producto producto = productoDAO.read(idProducto);

		cantidadDisponible = producto.getCantidadDisponible();
		cantidadVendida = producto.getCantidadVendida();

		if (cantidadDisponible < cantidad) {
			cantidadDisponible = 0;
			cantidadVendida += cantidadDisponible;
			mensaje = "Quiere vender más productos de los actualmente disponibles. Se venderan todos";
		} else {
			cantidadDisponible = cantidadDisponible - cantidad;
			cantidadVendida += cantidad;
			mensaje = "Se han vendido correctamente " + cantidad + " discos. Total disponible: " + cantidadDisponible;
		}

		producto.setCantidadDisponible(cantidadDisponible);
		producto.setCantidadVendida(cantidadVendida);

		productoDAO.update(producto);

		return mensaje;
	}

	public String reservarProductos(int idProducto, int cantidad) {

		int cantidadDisponible, cantidadReservada = 0;
		String mensaje = "";

		ProductoDAO productoDAO = new ProductoDAO();
		Producto producto = productoDAO.read(idProducto);

		cantidadDisponible = producto.getCantidadDisponible();
		cantidadReservada = producto.getCantidadReservada();

		if (cantidadDisponible < cantidad) {
			cantidadDisponible = 0;
			cantidadReservada += cantidadDisponible;
			mensaje = "Quiere reservar más productos de los actualmente disponibles. Se reservaran todos";
		} else {
			cantidadDisponible = cantidadDisponible - cantidad;
			cantidadReservada += cantidad;
			mensaje = "Se han reservado correctamente " + cantidad + " discos. Total disponible: " + cantidadDisponible;
		}

		producto.setCantidadDisponible(cantidadDisponible);
		producto.setCantidadReservada(cantidadReservada);

		productoDAO.update(producto);

		return mensaje;
	}

}
