package util;

public class CopiaSeguridad {

	@SuppressWarnings("unused")
	public static String backUp(String filePath){

		String executeCommand = "mysqldump -hden1.mysql2.gear.host -uinfamousmusic -pOc5r!8H6qe?2 infamousmusic -r "+filePath+".sql";
		String resultado = "";
		System.out.println(executeCommand);


		try {

			Process runtimeProcess = Runtime.getRuntime().exec(executeCommand);
			int processComplete = runtimeProcess.waitFor();

			if (processComplete == 0) {

				resultado = "Copia de seguridad creada correctamente.";

			} else {
				resultado = "No se ha podido crear la copia de seguridad.";
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}



}