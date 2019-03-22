package modelo;

import java.sql.Date;

public class Usuario {

	private int idDNIUsuario;
	private String password;
	private String nombre;
	private String apellidos;
	private int telefono;
	private String email;
	private int esAdmin;
	private Date fechaContratacion;

	public Usuario(int idDNIUsuario, String password, String nombre, String apellidos, int telefono, String email,int esAdmin, Date fechaContratacion) {
		this.idDNIUsuario = idDNIUsuario;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		this.esAdmin = esAdmin;
		this.fechaContratacion = fechaContratacion;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIdDNIUsuario() {
		return idDNIUsuario;
	}

	public void setIdDNIUsuario(int idDNIUsuario) {
		this.idDNIUsuario = idDNIUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEsAdmin() {
		return esAdmin;
	}

	public void setEsAdmin(int esAdmin) {
		this.esAdmin = esAdmin;
	}

	public Date getFechaContratacion() {
		return fechaContratacion;
	}

	public void setFechaContratacion(Date fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}
	
	

}
