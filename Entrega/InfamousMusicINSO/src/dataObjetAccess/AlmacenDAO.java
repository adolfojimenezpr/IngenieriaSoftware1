package dataObjetAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Interfaces.InterfazDAO;
import modelo.Almacen;
import util.Conexion;

public class AlmacenDAO implements InterfazDAO<Almacen> {

	private static final String SQL_INSERT = "INSERT INTO almacen (nombreAlmacen) VALUES (?, ?)";
	private static final String SQL_DELETE = "DELETE FROM almacen WHERE idAlmacen = ?";
	private static final String SQL_UPDATE = "UPDATE almacen SET nombreAlmacen = ? WHERE idAlmacen = ?";
	private static final String SQL_READ = "SELECT * FROM almacen WHERE idAlmacen = ?";
	private static final String SQL_READALL = "SELECT * FROM almacen";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(Almacen elemento) {


		
		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			query.setString(1, elemento.getNombreAlmacen());

			if (query.executeUpdate() > 0) {
				ResultSet claves = query.getGeneratedKeys();
				claves.next();
				idCreada = claves.getInt(1);
				return idCreada;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}

		return idCreada;
	}

	@Override
	public boolean delete(Object clave) {

		try {
			query = conn.getConn().prepareStatement(SQL_DELETE);

			query.setInt(1, (int) clave);

			if (query.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}
		return false;
	}

	@Override
	public boolean update(Almacen elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setString(1, elemento.getNombreAlmacen());
			query.setInt(2, elemento.getIdAlmacen());

			if (query.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}
		return false;

	}

	@Override
	public Almacen read(Object clave) {

		Almacen aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Almacen(resultado.getInt(1), resultado.getString(2));
			}

			return aux;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}
		return aux;
	}

	@Override
	public ArrayList<Almacen> readAll() {

		Almacen aux = null;

		ArrayList<Almacen> lista = new ArrayList<Almacen>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);
			
			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Almacen(resultado.getInt(1), resultado.getString(2));
				lista.add(aux);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}
		return lista;
	}

}
