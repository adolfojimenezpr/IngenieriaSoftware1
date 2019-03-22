package modelo;

import java.sql.Date;

public class Pago {

	private int idPago;
	private float importeVenta;
	private float importeEntregado;
	private float importeVuelta;
	private Date fechaPago;

	public Pago() {

	}

	public Pago(int idPago, float importeVenta, float importeEntregado, float importeVuelta, Date fechaPago) {
		this.idPago = idPago;
		this.importeVenta = importeVenta;
		this.importeEntregado = importeEntregado;
		this.importeVuelta = importeVuelta;
		this.fechaPago = fechaPago;
	}

	public int getIdPago() {
		return idPago;
	}

	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}

	public float getImporteVenta() {
		return importeVenta;
	}

	public void setImporteVenta(float importeVenta) {
		this.importeVenta = importeVenta;
	}

	public float getImporteEntregado() {
		return importeEntregado;
	}

	public void setImporteEntregado(float importeEntregado) {
		this.importeEntregado = importeEntregado;
	}

	public float getImporteVuelta() {
		return importeVuelta;
	}

	public void setImporteVuelta(float importeVuelta) {
		this.importeVuelta = importeVuelta;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

}
