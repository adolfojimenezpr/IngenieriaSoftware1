package dataObjetAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import Interfaces.InterfazDAO;
import modelo.Cupon;
import util.Conexion;

public class CuponDAO implements InterfazDAO<Cupon> {

	private static final String SQL_INSERT = "INSERT INTO cupon (nombre, descuento, duracion, fechaInicio) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM cupon WHERE idCupon = ?";
	private static final String SQL_UPDATE = "UPDATE cupon SET nombre = ?, descuento = ?, duracion = ? WHERE idCupon = ?";
	private static final String SQL_READ = "SELECT * FROM cupon WHERE idCupon = ?";
	private static final String SQL_READALL = "SELECT * FROM cupon";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(Cupon elemento) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Date fechaActual = new java.sql.Date(calendar.getTime().getTime());

		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			query.setString(1, elemento.getNombre());
			query.setInt(2, elemento.getDescuento());
			query.setInt(3, elemento.getDuracion());
			query.setDate(4, fechaActual);
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
	public boolean update(Cupon elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setString(1, elemento.getNombre());
			query.setInt(2, elemento.getDescuento());
			query.setInt(3, elemento.getDuracion());
			query.setInt(4, elemento.getIdCupon());

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
	public Cupon read(Object clave) {

		Cupon aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Cupon(resultado.getInt(1), resultado.getString(2), resultado.getInt(3), resultado.getInt(4),
						resultado.getDate(5));
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
	public ArrayList<Cupon> readAll() {

		Cupon aux = null;

		ArrayList<Cupon> lista = new ArrayList<Cupon>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Cupon(resultado.getInt(1), resultado.getString(2), resultado.getInt(3), resultado.getInt(4),
						resultado.getDate(5));
				;
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
