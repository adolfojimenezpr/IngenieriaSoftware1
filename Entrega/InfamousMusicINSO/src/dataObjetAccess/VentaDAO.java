package dataObjetAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import Interfaces.InterfazDAO;
import modelo.Venta;
import util.Conexion;

public class VentaDAO implements InterfazDAO<Venta> {

	private static final String SQL_INSERT = "INSERT INTO venta (idDNIUsuario, idDNICliente, idPago, idCupon, precioTotal) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM venta WHERE idVenta = ?";
	private static final String SQL_UPDATE = "UPDATE venta idDNIUsuario = ?, idDNICliente = ?, idPago = ?, idCupon = ?, precioTotal = ? WHERE idVenta = ?";
	private static final String SQL_READ = "SELECT * FROM venta WHERE idVenta = ?";
	private static final String SQL_READALL = "SELECT * FROM venta";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(Venta elemento) {

		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			query.setInt(1, elemento.getIdUsuario());
			if(elemento.getIdCliente()==-1) {
				query.setNull(2, Types.INTEGER);
			}else {
				query.setInt(2, elemento.getIdCliente());
			}
			
			query.setInt(3, elemento.getIdPago());
			
			if(elemento.getIdCupon()==-1) {
				query.setNull(4, Types.INTEGER);
			}else {
				query.setInt(4, elemento.getIdCupon());
			}
			query.setFloat(5, elemento.getPrecioTotal());

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
	public boolean update(Venta elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setInt(1, elemento.getIdUsuario());
			query.setInt(2, elemento.getIdCliente());
			query.setInt(3, elemento.getIdPago());
			query.setInt(4, elemento.getIdCupon());
			query.setFloat(5, elemento.getPrecioTotal());
			query.setInt(6, elemento.getIdVenta());

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
	public Venta read(Object clave) {

		Venta aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Venta(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4),
						resultado.getInt(5), resultado.getFloat(6));
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
	public ArrayList<Venta> readAll() {

		Venta aux = null;

		ArrayList<Venta> lista = new ArrayList<Venta>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Venta(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4),
						resultado.getInt(5), resultado.getFloat(6));
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