package modelo;

public class Producto {

	private int idProducto;
	private int idDetalles;
	private int idAlmacen;
	private int cantidadDisponible;
	private int cantidadVendida;
	private int cantidadReservada;

	public Producto(int idProducto, int idDetalles, int idAlmacen, int cantidadDisponible, int cantidadVendida,
			int cantidadReservada) {
		this.idProducto = idProducto;
		this.idDetalles = idDetalles;
		this.idAlmacen = idAlmacen;
		this.cantidadDisponible = cantidadDisponible;
		this.cantidadVendida = cantidadVendida;
		this.cantidadReservada = cantidadReservada;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getIdDetalles() {
		return idDetalles;
	}

	public void setIdDetalles(int idDetalles) {
		this.idDetalles = idDetalles;
	}

	public int getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public int getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	public int getCantidadVendida() {
		return cantidadVendida;
	}

	public void setCantidadVendida(int cantidadVendida) {
		this.cantidadVendida = cantidadVendida;
	}

	public int getCantidadReservada() {
		return cantidadReservada;
	}

	public void setCantidadReservada(int cantidadReservada) {
		this.cantidadReservada = cantidadReservada;
	}

}
