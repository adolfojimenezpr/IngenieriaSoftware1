package modelo;

import java.sql.Date;

public class Reserva {

	private int idReserva;
	private int idCliente;
	private int idProducto;
	private int cantidad;
	private Date fechaReserva;

	/*
	 * private float cantidadPagada;
	 *
	 * ESTO NO ESTA IMPLEMENTADO EN NADA, PERO SE PUEDE PLANTEAR. ES LO QUE HABLAMOS
	 * DE QUE SI NO PODIAMOS PRECIO PODIAN VENIR Y COGER LOS QUE QUISIERAN Y FALTA
	 * CADUCIDAD Y MAS TEMAS SI QUEREMOS HACERLO DEL TDO
	 * 
	 */

	public Reserva() {

	}

	public Reserva(int idReserva, int idProducto, int idCliente, int cantidad, Date fechaReserva) {
		this.idReserva = idReserva;
		this.idProducto = idProducto;
		this.idCliente = idCliente;
		this.cantidad = cantidad;
		this.fechaReserva = fechaReserva;
	}

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	public Date getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(Date fecha) {
		this.fechaReserva = fecha;
	}

}
