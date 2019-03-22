package controlador;

import java.util.ArrayList;
import java.util.HashMap;

import util.Encriptacion;

public class ControladorSeguridad {
	private static ControladorSeguridad estado = new ControladorSeguridad();
	private int usuarioActual = -1;
	private boolean admin;

	public static ControladorSeguridad getInstance() {
		return estado;
	}
	
	public String comprobarLogeo(int nombre, String pass) {
		boolean exitoNombre = false, exitoPass = false;
		String resultado = "Exito";
		ArrayList<HashMap<String, Object>> usuarios = ControladorUsuario.getInstance().consultarUsuarios();
		int indexUsuario = -1;
		for (int i = 0; i< usuarios.size(); i++) {
			if (nombre == (int) usuarios.get(i).get("idDNIUsuario")) {
				exitoNombre = true;
				indexUsuario = i;
				i = usuarios.size();
			}
		}
		if (exitoNombre) {
			String contraseñaEncriptada =(String) usuarios.get(indexUsuario).get("password"); 
			if (pass == Encriptacion.Desencriptar(contraseñaEncriptada));
				exitoPass = true;
			if (exitoPass) {
				setUsuarioActual(nombre);
				if ((int)((HashMap<String, Object>)usuarios.get(indexUsuario)).get("esAdmin") == 1){
					setAdmin(true);
				}else {
					setAdmin(false);
				}
			}	else {
				resultado = "Contraseña incorrecta";
			}
		
		}else {
			resultado = "Usuario incorrecto";
		}

		
		return resultado;
	}

	private void setAdmin(boolean adminParam) {
		admin = adminParam;
	}
	private void setUsuarioActual(int nombre) {
		usuarioActual = nombre;
	}
	public int getUsuarioActual() {
		return usuarioActual;
	}
	public boolean getAdmin() {
		return admin;
	}

}
