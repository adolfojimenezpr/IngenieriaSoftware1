package modelo;

import controlador.ControladorAlmacen;

public class LineaVenta {

	private int idLineaVenta;
	private int idVenta;
	private int idProducto;
	private int cantidad;
	private int cantidadDevuelta;

	public LineaVenta() {

	}

	public LineaVenta(int idLineaVenta, int idVenta, int idProducto, int cantidad, int cantidadDevuelta) {
		this.idLineaVenta = idLineaVenta;
		this.idVenta = idVenta;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.cantidadDevuelta = cantidadDevuelta;
	}

	public float calcularSubTotal() {

		ControladorAlmacen controladorAlmacen = ControladorAlmacen.getInstance();
		Producto productoActual = controladorAlmacen.obtenerProducto(idProducto);
		DetallesProducto detallesProducto = controladorAlmacen.obtenerDetalles(productoActual.getIdDetalles());

		return detallesProducto.getPrecio();
	}

	public void actualizarLineaVenta() {

	}

	public int getIdLineaVenta() {
		return idLineaVenta;
	}

	public void setIdLineaVenta(int idLineaVenta) {
		this.idLineaVenta = idLineaVenta;
	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCantidadDevuelta() {
		return cantidadDevuelta;
	}

	public void setCantidadDevuelta(int cantidadDevuelta) {
		this.cantidadDevuelta = cantidadDevuelta;
	}

}
