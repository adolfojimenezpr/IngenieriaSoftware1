package modelo;

import java.sql.Date;

public class DetallesProducto {

	private int idDetallesProducto;
	private String artista;
	private String album;
	private String estiloMusical;
	private float precio;
	private Date fechaLanzamiento;

	public DetallesProducto(int idDetallesProducto, String artista, String album, String estiloMusical, float precio,
			Date fechaLanzamiento) {
		this.idDetallesProducto = idDetallesProducto;
		this.artista = artista;
		this.album = album;
		this.estiloMusical = estiloMusical;
		this.precio = precio;
		this.fechaLanzamiento = fechaLanzamiento;
	}

	public int getIdDetallesProducto() {
		return idDetallesProducto;
	}

	public void setIdDetallesProducto(int idDetallesProducto) {
		this.idDetallesProducto = idDetallesProducto;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getEstiloMusical() {
		return estiloMusical;
	}

	public void setEstiloMusical(String estiloMusical) {
		this.estiloMusical = estiloMusical;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Date getFechaLanzamiento() {
		return fechaLanzamiento;
	}

	public void setFechaLanzamiento(Date fechaLanzamiento) {
		this.fechaLanzamiento = fechaLanzamiento;
	}

}
