package me.alejandro.ftp;

import java.util.Scanner;

import me.alejandro.ftpclient.ConnectionManager;
import me.alejandro.ftpclient.FtpClientConfig;
import me.alejandro.ftpclient.FtpConnection;

public class TestFtpClient {
	public static void main(String[] args) {
		ConnectionManager cm = new ConnectionManager(FtpClientConfig.instance());
		Scanner sc = new Scanner(System.in);
		//ejemplo: mostrar las conexiones guardadas en el fichero
		System.out.println("Usuarios registrados en el archivo [ftpconfig.data]:");
		for(FtpConnection ftpConnection:cm.getConnections()) {
			System.out.println(ftpConnection.getName());
		}
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
}
