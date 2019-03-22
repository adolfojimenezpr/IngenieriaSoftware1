package modelo;

import java.util.ArrayList;

public class Venta {

	private int idVenta;
	private int idUsuario;
	private int idCliente;
	private int idPago;
	private int idCupon;
	private float precioTotal;

	private ArrayList<LineaVenta> coleccionLV;

	public Venta() {
		this.coleccionLV = new ArrayList<LineaVenta>();

	}

	public Venta(int idVenta, int idUsuario, int idCliente, int idPago, int idCupon, float precioTotal) {
		this.idVenta = idVenta;
		this.idUsuario = idUsuario;
		this.idCliente = idCliente;
		this.idPago = idPago;
		this.idCupon = idCupon;
		this.precioTotal = precioTotal;

		this.coleccionLV = new ArrayList<LineaVenta>();
	}

	public void añadirItem(int idProducto, int cantidad) {

		LineaVenta lineaVentaActual = new LineaVenta();

		lineaVentaActual.setIdProducto(idProducto);
		lineaVentaActual.setCantidad(cantidad);
		lineaVentaActual.setCantidadDevuelta(0);
		
		coleccionLV.add(lineaVentaActual);
	}

	public float calcularTotal(float descuento) {

		float total = 0;

		for (int i = 0; i < coleccionLV.size(); i++) {

			total += coleccionLV.get(i).getCantidad() * coleccionLV.get(i).calcularSubTotal();
		}

		return total * ((100 - descuento) / 100);
	}

	public float realizarPago(float dineroEntregado) {

		return 0;
	}

	public void actualizarVenta() {

	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdPago() {
		return idPago;
	}

	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}

	public int getIdCupon() {
		return idCupon;
	}

	public void setIdCupon(int idCupon) {
		this.idCupon = idCupon;
	}

	public float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = precioTotal;
	}

	public ArrayList<LineaVenta> getColeccionLV() {
		return coleccionLV;
	}

	public void setColeccionLV(ArrayList<LineaVenta> coleccionLV) {
		this.coleccionLV = coleccionLV;
	}

}
