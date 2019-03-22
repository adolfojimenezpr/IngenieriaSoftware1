package controlador;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import dataObjetAccess.UsuarioDAO;
import modelo.Usuario;

public class ControladorUsuario {

	private static ControladorUsuario instance = null;

	private ControladorUsuario() {
	}

	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new ControladorUsuario();
		}
	}

	public static ControladorUsuario getInstance() {
		if (instance == null)
			createInstance();
		return instance;
	}

	public String añadirUsuario(HashMap<String, Object> atributos) {

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		Usuario usuario = new Usuario((int) atributos.get("idDNIUsuario"), (String) atributos.get("password"),
				(String) atributos.get("nombre"), (String) atributos.get("apellidos"), (int) atributos.get("telefono"),
				(String) atributos.get("email"), (int) atributos.get("esAdmin"), (Date) atributos.get("fechaContratacion")); // Atributos.get etc

		if (usuarioDAO.create(usuario) == -1) {
			return "Error al añadir el usuario en la BBDD";
		} else {
			return "El usuario ha sido creado con exito";
		}
	}

	public String eliminarUsuario(int idUsuario) {

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		if (!usuarioDAO.delete(idUsuario)) {
			return "Error al eliminar el usuario de la BBDD";
		} else {
			return "El usuario ha sido eliminado con exito";
		}
	}

	public String modificarUsuario(HashMap<String, Object> atributos) {

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		Usuario usuario = usuarioDAO.read(atributos.get("idDNIUsuario"));

		usuario.setPassword((String) atributos.get("password"));
		usuario.setNombre((String) atributos.get("nombre"));
		usuario.setApellidos((String) atributos.get("apellidos"));
		usuario.setTelefono((int) atributos.get("telefono"));
		usuario.setEmail((String) atributos.get("email"));
		usuario.setEsAdmin((int) atributos.get("esAdmin"));

		if (!usuarioDAO.update(usuario)) {
			return "Error al modificar el usuario en la BBDD";
		} else {
			return "El usuario ha sido modificado con exito";
		}
	}

	public Usuario obtenerUsuario(int idUsuario) {

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		return usuarioDAO.read(idUsuario);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> consultarUsuarios() {

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = null;

		HashMap<String, Object> map = new HashMap<String, Object>();

		ArrayList<HashMap<String, Object>> listaEnviada = new ArrayList<HashMap<String, Object>>();
		ArrayList<Usuario> listaRecibida = usuarioDAO.readAll();

		Iterator<Usuario> iterador = listaRecibida.iterator();

		while (iterador.hasNext()) {

			usuario = (Usuario) iterador.next();

			map.put("idDNIUsuario", usuario.getIdDNIUsuario());
			map.put("password", usuario.getPassword());
			map.put("nombre", usuario.getNombre());
			map.put("apellidos", usuario.getApellidos());
			map.put("telefono", usuario.getTelefono());
			map.put("email", usuario.getEmail());
			map.put("esAdmin", usuario.getEsAdmin());
			map.put("fechaContratacion", usuario.getFechaContratacion());

			listaEnviada.add((HashMap<String, Object>) map.clone()); // Mirar si se vacia por tema de punteros o se copia

			map.clear();
		}

		return listaEnviada;
	}

}
