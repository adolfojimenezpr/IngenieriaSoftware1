package dataObjetAccess;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Interfaces.InterfazDAO;
import modelo.Reserva;
import util.Conexion;

public class ReservaDAO implements InterfazDAO<Reserva> {

	private static final String SQL_INSERT = "INSERT INTO reserva (idProducto, idDNICliente, cantidad, fechaReserva) VALUES (?, ?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM reserva WHERE idReserva = ?";
	private static final String SQL_UPDATE = "UPDATE reserva SET idProducto = ?, idDNICliente = ?, cantidad = ?, fechaReserva = ? WHERE idReserva = ?";
	private static final String SQL_READ = "SELECT * FROM reserva WHERE idReserva = ?";
	private static final String SQL_READALL = "SELECT * FROM reserva";

	private static final Conexion conn = Conexion.abrirConexion();

	PreparedStatement query;
	ResultSet resultado;

	@Override
	public int create(Reserva elemento) {

		int idCreada = -1;

		try {
			query = conn.getConn().prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			query.setInt(1, elemento.getIdProducto());
			query.setInt(2, elemento.getIdCliente());
			query.setInt(3,  elemento.getCantidad());
			//query.setDate(4, elemento.getFechaReserva());
			query.setDate(4, new Date(435));
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
	public boolean update(Reserva elemento) {

		try {
			query = conn.getConn().prepareStatement(SQL_UPDATE);

			query.setInt(1, elemento.getIdProducto());
			query.setInt(2, elemento.getIdCliente());
			query.setInt(3, elemento.getCantidad());
			query.setDate(4, elemento.getFechaReserva());
			query.setInt(5, elemento.getIdReserva());

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
	public Reserva read(Object clave) {

		Reserva aux = null;

		try {
			query = conn.getConn().prepareStatement(SQL_READ);

			query.setInt(1, (int) clave);

			resultado = query.executeQuery();

			while (resultado.next()) {
				aux = new Reserva(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4), resultado.getDate(5));
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
	public ArrayList<Reserva> readAll() {

		Reserva aux = null;

		ArrayList<Reserva> lista = new ArrayList<Reserva>();

		try {
			query = conn.getConn().prepareStatement(SQL_READALL);
			
			resultado = query.executeQuery();


			while (resultado.next()) {
				aux = new Reserva(resultado.getInt(1), resultado.getInt(2), resultado.getInt(3), resultado.getInt(4), resultado.getDate(5));
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
