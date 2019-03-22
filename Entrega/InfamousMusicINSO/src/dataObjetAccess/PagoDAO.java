package dataObjetAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import Interfaces.InterfazDAO;
import modelo.Pago;
import util.Conexion;

public class PagoDAO implements InterfazDAO<Pago> {

	private static final String SQL_INSERT = "INSERT INTO pago (importeVenta, importeEntregado, importeVuelta, fechaPago) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM pago WHERE idPago = ?";
	private static final String SQL_UPDATE = "UPDATE pago SET importeVenta = ?, importeEntregado = ?, importeVuelta = ?, fechaPago = ? WHERE idPago = ?";
	private static final String SQL_READ = "SELECT * FROM pago WHERE idPago = ?";
	private static final String SQL_READALL = "SELECT * FROM pago";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(Pago elemento) {
		
		Calendar calendar = Calendar.getInstance();
		java.sql.Date fechaActual = new java.sql.Date(calendar.getTime().getTime());
		
		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			query.setFloat(1, elemento.getImporteVenta());
			query.setFloat(2, elemento.getImporteEntregado());
			query.setFloat(3, elemento.getImporteVuelta());
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
	public boolean update(Pago elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setFloat(1, elemento.getImporteVenta());
			query.setFloat(2, elemento.getImporteEntregado());
			query.setFloat(3, elemento.getImporteVuelta());
			query.setDate(4, elemento.getFechaPago());
			query.setInt(5, elemento.getIdPago());

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
	public Pago read(Object clave) {

		Pago aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Pago(resultado.getInt(1), resultado.getFloat(2), resultado.getFloat(3), resultado.getFloat(4),
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
	public ArrayList<Pago> readAll() {

		Pago aux = null;

		ArrayList<Pago> lista = new ArrayList<Pago>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);
			
			resultado = query.executeQuery();


			while (resultado.next()) {
				aux = new Pago(resultado.getInt(1), resultado.getFloat(2), resultado.getFloat(3), resultado.getFloat(4),
						resultado.getDate(5));
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
