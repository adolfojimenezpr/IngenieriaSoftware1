package dataObjetAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Interfaces.InterfazDAO;
import modelo.DetallesProducto;
import util.Conexion;

public class DetallesProductoDAO implements InterfazDAO<DetallesProducto> {

	private static final String SQL_INSERT = "INSERT INTO detallesproducto (artista, album, estiloMusical, precio, fechaLanzamiento) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM detallesproducto WHERE idDetallesProducto = ?";
	private static final String SQL_UPDATE = "UPDATE detallesproducto SET artista = ?, album = ?, estiloMusical = ?, precio = ?, fechaLanzamiento = ? WHERE idDetallesProducto = ?";
	private static final String SQL_READ = "SELECT * FROM detallesproducto WHERE idDetallesProducto = ?";
	private static final String SQL_READALL = "SELECT * FROM detallesproducto";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(DetallesProducto elemento) {

		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			query.setString(1, elemento.getArtista());
			query.setString(2, elemento.getAlbum());
			query.setString(3, elemento.getEstiloMusical());
			query.setFloat(4, elemento.getPrecio());
			query.setDate(5, elemento.getFechaLanzamiento());

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
	public boolean update(DetallesProducto elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setString(1, elemento.getArtista());
			query.setString(2, elemento.getAlbum());
			query.setString(3, elemento.getEstiloMusical());
			query.setFloat(4, elemento.getPrecio());
			query.setDate(5, elemento.getFechaLanzamiento());
			query.setInt(6, elemento.getIdDetallesProducto());

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
	public DetallesProducto read(Object clave) {

		DetallesProducto aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new DetallesProducto(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getFloat(5), resultado.getDate(6));
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
	public ArrayList<DetallesProducto> readAll() {

		DetallesProducto aux = null;

		ArrayList<DetallesProducto> lista = new ArrayList<DetallesProducto>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new DetallesProducto(resultado.getInt(1), resultado.getString(2), resultado.getString(3),
						resultado.getString(4), resultado.getFloat(5), resultado.getDate(6));
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
