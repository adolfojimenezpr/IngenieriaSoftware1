package modelo;

import java.sql.Date;

public class Cupon {

	private int idCupon;
	private String nombre;
	private int descuento;
	private int duracion;
	private Date fechaInicio;

	public Cupon(int idCupon, String nombre, int descuento, int duracion, Date fechaInicio) {
		this.idCupon = idCupon;
		this.nombre = nombre;
		this.descuento = descuento;
		this.duracion = duracion;
		this.fechaInicio = fechaInicio;
	}

	public int getIdCupon() {
		return idCupon;
	}

	public void setIdCupon(int idCupon) {
		this.idCupon = idCupon;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fecha_inicio) {
		this.fechaInicio = fecha_inicio;
	}

}
