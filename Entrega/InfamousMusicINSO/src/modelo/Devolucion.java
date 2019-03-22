package modelo;

public class Devolucion {

	private int idDevolucion;
	private int idProducto;
	private int idVenta;

	public Devolucion() {

	}

	public Devolucion(int idDevolucion, int idProducto, int idVenta) {
		this.idDevolucion = idDevolucion;
		this.idProducto = idProducto;
		this.idVenta = idVenta;
	}

	public int getIdDevolucion() {
		return idDevolucion;
	}

	public void setIdDevolucion(int idDevolucion) {
		this.idDevolucion = idDevolucion;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

}
