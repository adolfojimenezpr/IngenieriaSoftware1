package controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import dataObjetAccess.ClienteDAO;
import modelo.Cliente;

public class ControladorCliente {

	private static ControladorCliente instance = null;

	private ControladorCliente() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new ControladorCliente();
		}
	}

	public static ControladorCliente getInstance() {
		if (instance == null)
			createInstance();
		return instance;
	}

	public String añadirCliente(HashMap<String, Object> atributos) {

		ClienteDAO clienteDAO = new ClienteDAO();

		Cliente cliente = new Cliente((int) atributos.get("idDNICliente"), (String) atributos.get("nombre"),
				(String) atributos.get("apellidos"), (int) atributos.get("telefono"), (String) atributos.get("email"));

		if (clienteDAO.create(cliente) == -1) {
			return "Error al añadir el cliente en la BBDD";
		} else {
			return "El cliente ha sido creado con exito";
		}
	}

	public String eliminarCliente(int idCliente) {

		ClienteDAO clienteDAO = new ClienteDAO();

		if (!clienteDAO.delete(idCliente)) {
			return "Error al eliminar el cliente de la BBDD";
		} else {
			return "El cliente ha sido eliminado con exito";
		}
	}

	public String modificarCliente(HashMap<String, Object> atributos) {

		ClienteDAO clienteDAO = new ClienteDAO();

		Cliente cliente = clienteDAO.read(atributos.get("idDNICliente"));

		cliente.setNombre((String) atributos.get("nombre"));
		cliente.setApellidos((String) atributos.get("apellidos"));
		cliente.setTelefono((int) atributos.get("telefono"));
		cliente.setEmail((String) atributos.get("email"));

		if (!clienteDAO.update(cliente)) {
			return "Error al modificar el cliente en la BBDD";
		} else {
			return "El cliente ha sido modificado con exito";
		}
	}

	public Cliente obtenerCliente(int idCliente) {
		ClienteDAO clienteDAO = new ClienteDAO();

		return clienteDAO.read(idCliente);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> consultarClientes() {

		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = null;

		HashMap<String, Object> map = new HashMap<String, Object>();

		ArrayList<HashMap<String, Object>> listaEnviada = new ArrayList<HashMap<String, Object>>();
		ArrayList<Cliente> listaRecibida = clienteDAO.readAll();

		Iterator<Cliente> iterador = listaRecibida.iterator();

		while (iterador.hasNext()) {

			cliente = (Cliente) iterador.next();

			map.put("idDNICliente", cliente.getIdDNICliente());
			map.put("nombre", cliente.getNombre());
			map.put("apellidos", cliente.getApellidos());
			map.put("telefono", cliente.getTelefono());
			map.put("email", cliente.getEmail());

			listaEnviada.add((HashMap<String, Object>) map.clone());

			map.clear();
		}

		return listaEnviada;
	}

}
