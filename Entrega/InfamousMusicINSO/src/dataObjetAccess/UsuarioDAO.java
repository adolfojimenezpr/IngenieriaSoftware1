package dataObjetAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import Interfaces.InterfazDAO;
import modelo.Usuario;
import util.Conexion;

public class UsuarioDAO implements InterfazDAO<Usuario> {

	private static final String SQL_INSERT = "INSERT INTO usuario (idDNIUsuario, password, nombre, apellidos, telefono, email, esAdmin, fechaContratacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM usuario WHERE idDNIUsuario = ?";
	private static final String SQL_UPDATE = "UPDATE usuario SET password = ?, nombre = ?, apellidos = ?, telefono = ?, email = ?, esAdmin = ? WHERE idDNIUsuario = ?";
	private static final String SQL_READ = "SELECT * FROM usuario WHERE idDNIUsuario = ?";
	private static final String SQL_READALL = "SELECT * FROM usuario";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(Usuario elemento) {

		Calendar calendar = Calendar.getInstance();
		java.sql.Date fechaActual = new java.sql.Date(calendar.getTime().getTime());
		
		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT);

			query.setInt(1, elemento.getIdDNIUsuario());
			query.setString(2, elemento.getPassword());
			query.setString(3, elemento.getNombre());
			query.setString(4, elemento.getApellidos());
			query.setInt(5, elemento.getTelefono());
			query.setString(6, elemento.getEmail());
			query.setInt(7, elemento.getEsAdmin());
			query.setDate(8, fechaActual);

			if (query.executeUpdate() > 0) {
				idCreada = elemento.getIdDNIUsuario();
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
	public boolean update(Usuario elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setString(1, elemento.getPassword());
			query.setString(2, elemento.getNombre());
			query.setString(3, elemento.getApellidos());
			query.setInt(4, elemento.getTelefono());
			query.setString(5, elemento.getEmail());
			query.setInt(6, elemento.getEsAdmin());
			query.setInt(7, elemento.getIdDNIUsuario());

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
	public Usuario read(Object clave) {

		Usuario aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Usuario(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getInt(5), resultado.getString(6), resultado.getInt(7), resultado.getDate(8));
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
	public ArrayList<Usuario> readAll() {

		Usuario aux = null;

		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Usuario(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getInt(5), resultado.getString(6), resultado.getInt(7), resultado.getDate(8));
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
