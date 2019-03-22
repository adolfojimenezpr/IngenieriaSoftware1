package dataObjetAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Interfaces.InterfazDAO;
import modelo.Devolucion;
import util.Conexion;

public class DevolucionDAO implements InterfazDAO<Devolucion> {

	private static final String SQL_INSERT = "INSERT INTO devolucion (idProducto, idVenta) VALUES (?, ?)";
	private static final String SQL_DELETE = "DELETE FROM devolucion WHERE idDevolucion = ?";
	private static final String SQL_UPDATE = "UPDATE devolucion SET idProducto = ?, idVenta = ? WHERE idDevolucion = ?";
	private static final String SQL_READ = "SELECT * FROM devolucion WHERE idDevolucion = ?";
	private static final String SQL_READALL = "SELECT * FROM devolucion";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(Devolucion elemento) {

		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			query.setInt(1, elemento.getIdProducto());
			query.setInt(2, elemento.getIdVenta());

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
	public boolean update(Devolucion elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setInt(1, elemento.getIdProducto());
			query.setInt(2, elemento.getIdVenta());
			query.setInt(3, elemento.getIdDevolucion());

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
	public Devolucion read(Object clave) {

		Devolucion aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Devolucion(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3));
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
	public ArrayList<Devolucion> readAll() {

		Devolucion aux = null;

		ArrayList<Devolucion> lista = new ArrayList<Devolucion>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Devolucion(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3));
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
