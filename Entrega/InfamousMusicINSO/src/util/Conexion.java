package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

	private static final String driverName = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://den1.mysql2.gear.host:3306/infamousmusic?autoReconnect=true&useSSL=true";
	private static final String user = "infamousmusic";
	private static final String password = "Oc5r!8H6qe?2";

	public static Conexion instanciaSingleton;
	private Connection conn;

	private Conexion() {

		try {
			Class.forName(driverName);
			this.conn = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static Conexion abrirConexion() {

		if (instanciaSingleton == null) {
			instanciaSingleton = new Conexion();
		}
		return instanciaSingleton;
	}

	public Connection getConn() {
		return conn;
	}

	public void cerrarConexion() {
		instanciaSingleton = null;
	}
}

/*
 * 
 * package modelo;
 * 
 * import java.sql.Connection; import java.sql.DriverManager;
 * 
 * public class ConexionBD {
 * 
 * private String dataBaseURL; private String driverName; private String user;
 * private String pass; private Connection conexion;
 * 
 * public ConexionBD() {
 * 
 * this.dataBaseURL = null; this.driverName = null; this.user = null; this.pass
 * = null; this.conexion = null;
 * 
 * }
 * 
 * public void abrirConexion() throws Exception {
 * 
 * if ((dataBaseURL == "") || (user == "") || (pass == "") || (driverName ==
 * "")) { System.out.println("Error al crear la conexion con etos valores");
 * //¿Estan inicializados? //this.mostrarValoresConexion(); }else {
 * 
 * try { Class.forName(this.driverName); this.conexion =
 * DriverManager.getConnection(this.dataBaseURL, this.user, this.pass); } catch
 * (Exception e) {
 * 
 * throw new Exception("Al abrir la base de datos "+e.getMessage()); }
 * 
 * }
 * 
 * }
 * 
 * public void cerrarConexion() throws Exception{
 * 
 * try { this.conexion.close();
 * System.out.println("Cierre correcto de la conexion con la base de datos"); }
 * catch (Exception e) { throw new
 * Exception("Al cerrar la conexion de la base de datos. "+ e.getMessage()); }
 * 
 * }
 * 
 * public String getDataBaseURL() { return dataBaseURL; }
 * 
 * public void setDataBaseURL(String dataBaseURL) { this.dataBaseURL =
 * dataBaseURL; }
 * 
 * public String getDriverName() { return driverName; }
 * 
 * public void setDriverName(String driverName) { this.driverName = driverName;
 * }
 * 
 * public String getUser() { return user; }
 * 
 * public void setUser(String user) { this.user = user; }
 * 
 * public String getPass() { return pass; }
 * 
 * public void setPass(String pass) { this.pass = pass; }
 * 
 * public Connection getConexion() { return conexion; }
 * 
 * public void setConexion(Connection conexion) { this.conexion = conexion; }
 * 
 * }
 * 
 * 
 */
