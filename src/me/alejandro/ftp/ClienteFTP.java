package me.alejandro.ftp;

import org.apache.commons.net.ftp.FTPClient;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class ClienteFTP implements Runnable {

	private String servidor;
	private String usuario;
	private String password;
	private FTPClient ftp;

	public ClienteFTP(String servidor, String usuario, String password) {
		this.servidor = servidor;
		this.usuario = usuario;
		this.password = password;
	}

	@Override
	public void run() {
		conectar();
		String[] archivos = listarArchivos();
		System.out.println("Archivos en el servidor: " + Arrays.toString(archivos));
		boolean encontrado = buscarArchivo("archivo.txt");
		if (encontrado) {
			System.out.println("El archivo ha sido encontrado en el servidor");
			boolean descargado = descargarArchivo("archivo.txt", "/home/usuario/");
			if (descargado) {
				System.out.println("El archivo se ha descargado correctamente");
			} else {
				System.out.println("No se ha podido descargar el archivo");
			}
		} else {
			System.out.println("El archivo no ha sido encontrado en el servidor");
		}
		/*
		boolean subido = subirArchivo("/home/usuario/archivo.txt", "archivo.txt");
		if (subido) {
			System.out.println("El archivo se ha subido correctamente");
		} else {
			System.out.println("No se ha podido subir el archivo");
		}
		*/
		desconectar();
	}

	public void conectar() {
		ftp = new FTPClient();
		try {
			ftp.connect(servidor);
			ftp.login(usuario, password);
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			System.out.println("Conexión establecida con " + servidor);
		} catch (IOException e) {
			System.out.println("No se ha podido conectar con el servidor " + servidor);
		}
	}

	public void desconectar() {
		try {
			ftp.logout();
			ftp.disconnect();
			System.out.println("Conexión cerrada con " + servidor);
		} catch (IOException e) {
			System.out.println("No se ha podido cerrar la conexión con el servidor " + servidor);
		}
	}

	public String[] listarArchivos() {
		try {
			return ftp.listNames();
		} catch (IOException e) {
			System.out.println("No se ha podido obtener la lista de archivos del servidor " + servidor);
		}
		return null;
	}

	public boolean buscarArchivo(String archivo) {
		try {
			String[] archivos = ftp.listNames();
			if (archivos != null) {
				return Arrays.asList(archivos).contains(archivo);
			}
		} catch (IOException e) {
			System.out.println("No se ha podido buscar el archivo en el servidor " + servidor);
		}
		return false;
	}

	public boolean descargarArchivo(String archivoRemoto, String rutaLocal) {
		try (OutputStream outputStream = new FileOutputStream(rutaLocal + archivoRemoto)) {
			return ftp.retrieveFile(archivoRemoto, outputStream);
		} catch (IOException e) {
			System.out.println("No se ha podido descargar el archivo del servidor " + servidor);
		}
		return false;
	}

	/*
    public boolean subirArchivo(String rutaLocal, String archivoRemoto) {
        try (OutputStream outputStream = ftp.storeFileStream(archivoRemoto)) {
            return ftp.storeFile(archivoRemoto, outputStream);
        } catch (IOException e) {
            System.out.println("No se ha podido subir el archivo al servidor " + servidorFTP);
        }
        return false;
    } */
}
