package dataObjetAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Interfaces.InterfazDAO;
import modelo.Cliente;
import util.Conexion;

public class ClienteDAO implements InterfazDAO<Cliente> {

	private static final String SQL_INSERT = "INSERT INTO cliente (idDNICliente, nombre, apellidos, telefono, email) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM cliente WHERE idDNICliente = ?";
	private static final String SQL_UPDATE = "UPDATE cliente SET nombre = ?, apellidos = ?, telefono = ?, email = ? WHERE idDNICliente = ?";
	private static final String SQL_READ = "SELECT * FROM cliente WHERE idDNICliente = ?";
	private static final String SQL_READALL = "SELECT * FROM cliente";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(Cliente elemento) {

		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT);

			query.setInt(1, elemento.getIdDNICliente());
			query.setString(2, elemento.getNombre());
			query.setString(3, elemento.getApellidos());
			query.setInt(4, elemento.getTelefono());
			query.setString(5, elemento.getEmail());

			if (query.executeUpdate() > 0) {
				idCreada = elemento.getIdDNICliente();
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
	public boolean update(Cliente elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setString(1, elemento.getNombre());
			query.setString(2, elemento.getApellidos());
			query.setInt(3, elemento.getTelefono());
			query.setString(4, elemento.getEmail());
			query.setInt(5, elemento.getIdDNICliente());

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
	public Cliente read(Object clave) {

		Cliente aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Cliente(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getInt(4), resultado.getString(5));
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
	public ArrayList<Cliente> readAll() {

		Cliente aux = null;

		ArrayList<Cliente> lista = new ArrayList<Cliente>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);
			
			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Cliente(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getInt(4), resultado.getString(5));
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
