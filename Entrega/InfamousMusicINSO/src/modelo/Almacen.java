package modelo;

public class Almacen {

	private int idAlmacen;
	private String nombreAlmacen;

	public Almacen(int idAlmacen, String nombreAlmacen) {
		this.idAlmacen = idAlmacen;
		this.nombreAlmacen = nombreAlmacen;
	}

	public int getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public String getNombreAlmacen() {
		return nombreAlmacen;
	}

	public void setNombreAlmacen(String nombreAlmacen) {
		this.nombreAlmacen = nombreAlmacen;
	}

}
