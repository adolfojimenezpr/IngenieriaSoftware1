package dataObjetAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Interfaces.InterfazDAO;
import modelo.LineaVenta;
import util.Conexion;

public class LineaVentaDAO implements InterfazDAO<LineaVenta> {

	private static final String SQL_INSERT = "INSERT INTO lineaventa (idVenta, idProducto, cantidad, cantidadDevuelta) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM lineaventa WHERE idLineaVenta = ?";
	private static final String SQL_UPDATE = "UPDATE lineaventa SET idVenta = ?, idProducto = ?, cantidad = ?, cantidadDevuelta = ? WHERE idLineaVenta = ?";
	private static final String SQL_READ = "SELECT * FROM lineaventa WHERE idLineaVenta = ?";
	private static final String SQL_READALL = "SELECT * FROM lineaventa";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(LineaVenta elemento) {

		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			query.setInt(1, elemento.getIdVenta());
			query.setInt(2, elemento.getIdProducto());
			query.setInt(3, elemento.getCantidad());
			query.setInt(4, elemento.getCantidadDevuelta());

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
	public boolean update(LineaVenta elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setInt(1, elemento.getIdVenta());
			query.setInt(2, elemento.getIdProducto());
			query.setInt(3, elemento.getCantidad());
			query.setInt(4, elemento.getCantidadDevuelta());
			query.setInt(5, elemento.getIdLineaVenta());

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
	public LineaVenta read(Object clave) {

		LineaVenta aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new LineaVenta(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4),
						resultado.getInt(5));
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
	public ArrayList<LineaVenta> readAll() {

		LineaVenta aux = null;

		ArrayList<LineaVenta> lista = new ArrayList<LineaVenta>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new LineaVenta(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4),
						resultado.getInt(5));
				lista.add(aux);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}
		return lista;
	}

	public ArrayList<LineaVenta> readAllWhere(int idVenta) {

		LineaVenta aux = null;

		ArrayList<LineaVenta> lista = new ArrayList<LineaVenta>();

		try {
			query = conn.getConn().prepareStatement("SELECT * FROM lineaventa WHERE idVenta = ?");

			query.setInt(1, (int) idVenta);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new LineaVenta(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4),
						resultado.getInt(5));

				lista.add(aux);

			}

			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}
		return lista;
	}

}
