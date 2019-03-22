package controlador;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import dataObjetAccess.CuponDAO;
import modelo.Cupon;

public class ControladorCupon {

	private static ControladorCupon instance = null;

	private ControladorCupon() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new ControladorCupon();
		}
	}

	public static ControladorCupon getInstance() {
		if (instance == null)
			createInstance();
		return instance;
	}

	public String añadirCupon(HashMap<String, Object> atributos) {

		CuponDAO cuponDAO = new CuponDAO();

		Cupon cupon = new Cupon(-1, (String) atributos.get("nombre"),
				(int) atributos.get("descuento"), (int) atributos.get("duracion"), (Date) atributos.get("fechaInicio"));

		if (cuponDAO.create(cupon) == -1) {
			return "Error al añadir el cupon en la BBDD";
		} else {
			return "El cupon ha sido creado con exito";
		}
	}

	public String eliminarCupon(int idCupon) {

		CuponDAO cuponDAO = new CuponDAO();

		if (!cuponDAO.delete(idCupon)) {
			return "Error al eliminar el cupon de la BBDD";
		} else {
			return "El cupon ha sido eliminado con exito";
		}
	}

	public String modificarCupon(HashMap<String, Object> atributos) {

		CuponDAO cuponDAO = new CuponDAO();

		Cupon cupon = cuponDAO.read(atributos.get("idCupon"));

		cupon.setNombre((String) atributos.get("nombre"));
		cupon.setDescuento((int) atributos.get("descuento"));
		cupon.setDuracion((int) atributos.get("duracion"));
		cupon.setFechaInicio((Date) atributos.get("fechaInicio"));

		if (!cuponDAO.update(cupon)) {
			return "Error al modificar el cupon en la BBDD";
		} else {
			return "El cupon ha sido modificado con exito";
		}
	}

	public Cupon obtenerCupon(int idCupon) {

		CuponDAO cuponDAO = new CuponDAO();

		return cuponDAO.read(idCupon);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> consultarCupones() {

		CuponDAO cuponDAO = new CuponDAO();
		Cupon cupon = null;

		HashMap<String, Object> map = new HashMap<String, Object>();

		ArrayList<HashMap<String, Object>> listaEnviada = new ArrayList<HashMap<String, Object>>();
		ArrayList<Cupon> listaRecibida = cuponDAO.readAll();

		Iterator<Cupon> iterador = listaRecibida.iterator();

		while (iterador.hasNext()) {

			cupon = (Cupon) iterador.next();

			map.put("idCupon", cupon.getIdCupon());
			map.put("nombre", cupon.getNombre());
			map.put("descuento", cupon.getDescuento());
			map.put("duracion", cupon.getDuracion());
			map.put("fechaInicio", cupon.getFechaInicio());

			listaEnviada.add((HashMap<String, Object>) map.clone());

			map.clear();
		}

		return listaEnviada;
	}
}
