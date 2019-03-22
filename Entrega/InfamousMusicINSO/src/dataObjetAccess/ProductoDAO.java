package dataObjetAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Interfaces.InterfazDAO;
import modelo.Producto;
import util.Conexion;

public class ProductoDAO implements InterfazDAO<Producto> {

	private static final String SQL_INSERT = "INSERT INTO producto (idDetallesProducto, idAlmacen, cantidadDisponible, cantidadVendida, cantidadReservada) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM producto WHERE idProducto = ?";
	private static final String SQL_UPDATE = "UPDATE producto SET idDetallesProducto = ?, idAlmacen = ?, cantidadDisponible = ?, cantidadVendida = ?, cantidadReservada = ? WHERE idProducto = ?";
	private static final String SQL_READ = "SELECT * FROM producto WHERE idProducto = ?";
	private static final String SQL_READALL = "SELECT * FROM producto";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(Producto elemento) {

		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			query.setInt(1, elemento.getIdDetalles());
			query.setInt(2, elemento.getIdAlmacen());
			query.setInt(3, elemento.getCantidadDisponible());
			query.setInt(4, elemento.getCantidadVendida());
			query.setInt(5, elemento.getCantidadReservada());

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
	public boolean update(Producto elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setInt(1, elemento.getIdDetalles());
			query.setInt(2, elemento.getIdAlmacen());
			query.setInt(3, elemento.getCantidadDisponible());
			query.setInt(4, elemento.getCantidadVendida());
			query.setInt(5, elemento.getCantidadReservada());
			query.setInt(6, elemento.getIdProducto());

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
	public Producto read(Object clave) {

		Producto aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Producto(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4),
						resultado.getInt(5), resultado.getInt(6));
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
	public ArrayList<Producto> readAll() {

		Producto aux = null;

		ArrayList<Producto> lista = new ArrayList<Producto>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);
			
			resultado = query.executeQuery();


			while (resultado.next()) {
				aux = new Producto(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4),
						resultado.getInt(5), resultado.getInt(6));
				lista.add(aux);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.cerrarConexion();
		}
		return lista;
	}

	public ArrayList<Producto> readAllWhere(int idAlmacen) {

		Producto aux = null;

		ArrayList<Producto> lista = new ArrayList<Producto>();

		try {
			query = conn.getConn().prepareStatement("SELECT * FROM producto WHERE idAlmacen = ?");

			query.setInt(1, (int) idAlmacen);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Producto(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4),
						resultado.getInt(5), resultado.getInt(6));

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
