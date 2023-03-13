package me.alejandro.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestFTP {

	public static void main(String[] args) {
		// Declaramos la ruta del archivo
		String archivoConfig = "res/ftpconfig.data";
		// Variables necesarias para el bucle
		String linea;
		String[] lectura = null;
		try {
			File archivo = new File(archivoConfig);
			InputStream is = new FileInputStream(archivo);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			//connectionname#address#puerto#usuarioftp#claveftp
			while(br.ready()) {
				linea = br.readLine();
				lectura = linea.split("#");
			}
			is.close();
			br.close();
		} catch (IOException e) {
			System.out.println("Hay algún error en el archivo y su lectura.");
			e.printStackTrace();
		}

		
		ClienteFTP cliente1 = new ClienteFTP(lectura[0], lectura[1], lectura[2]);
		//ClienteFTP cliente2 = new ClienteFTP("ftp.servidor2.com", "usuario2", "password2");

		Thread hiloCliente1 = new Thread(cliente1);
		//Thread hiloCliente2 = new Thread(cliente2);

		hiloCliente1.start();
		//hiloCliente2.start();
		 
	}
}
