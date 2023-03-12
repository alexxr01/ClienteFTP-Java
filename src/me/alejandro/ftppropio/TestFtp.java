package me.alejandro.ftppropio;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class TestFtp {
	/*
	 * Parámetros de configuracion
	 */
	final String servidor = "102.168.1.19";
	final int puerto = 22;
	final String usuario = "usuario";
	final String contrasena = "usuario";
	
	FTPClient clienteFtp = new FTPClient();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println(" ");
		System.out.println("-  ¿Que deseas hacer?");
		System.out.println(""
				+ "1. Conectar y desconectar\n"
				+ "2. Listar ficheros y directorios\n"
				+ "3. Buscar un fichero o directorio\n"
				+ "4. Descargar un fichero\n"
				+ "5. Subir un fichero\n");
		int opcion = sc.nextInt();
		switch (opcion) {
		case 1:
			System.out.println("Vas a conectar a un servidor.");
			break;

		default:
			break;
		}
	}
	
	public void conectarServidor() {
		try {
			clienteFtp.connect(servidor, puerto);
			
			int respuestaRecogida = clienteFtp.getReplyCode();
			
			if (!FTPReply.isPositiveCompletion(respuestaRecogida)) {
				System.out.println("ERROR " + respuestaRecogida + ". Algo ha salido mal");
			}
			
			boolean loginSatisfactorio = clienteFtp.login(usuario, contrasena);
			
			if (loginSatisfactorio) {
				System.out.println("Se ha iniciado sesión correctamente.");
			} else {
				System.out.println("El usuario " + usuario + " y contraseña " + contrasena + " son inválidos.");
			}
			
		} catch (IOException e) {
			System.err.print("Error: " + e.getMessage());
		}
	}

}
