package me.alejandro.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Scanner;

public class ClienteFTP implements Runnable {
	// Scanner necesario para recoger la opcion seleccionada
	Scanner sc = new Scanner(System.in);
	int opcion = 0;
	// Declaramos las variables necesarias
	private String servidor;
	private String usuario;
	private String password;
	// Heredera de Apache Commons Net
	private FTPClient ftp;
	// Constructor vacío
	public ClienteFTP() {
	}
	// Constructor con parámetros
	public ClienteFTP(String servidor, String usuario, String password) {
		this.servidor = servidor;
		this.usuario = usuario;
		this.password = password;
	}
	// Creamos un hilo, ya que la actividad nos lo pide.
	@Override
	public void run() {
		while (opcion != 8) {
			switch (opcion) {
			case 1:
				// Llamamos al método conectar
				conectar();
				break;
			case 2:
				// Almacenamos los archivos procedentes del método en un
				// Array de Strings denominado archivos
				String[] archivos = listarArchivos();
				// Mostramos los archivos que hay dentro del servidor
				System.out.println("Archivos en el servidor: " + Arrays.toString(archivos));
				break;
			case 3:
				// Almacenamos en un boolean si se ha encontrado el siguiente archivo o no
				System.out.println("Por favor, indica el archivo a encontrar:");
				String archivo = sc.next();
				boolean encontrado = buscarArchivo(archivo);
				// Si se ha encontrado se realiza lo siguiente
				if (encontrado) {
					System.out.println("El archivo se ha encontrado correctamente en el FTP.");
				} else {
					// Si nada ha sido posible, se muestra mensaje de error.
					System.out.println("No se ha podido buscar el archivo en el servidor " + servidor);
				}
				break;
			case 4:
				System.out.println("Por favor, indica el archivo del ftp a descargar:");
				String archivoDescargar = sc.next();
				System.out.println("Introduce la ruta de descarga (de tu pc):");
				String rutaDescargar = sc.next();
				// Almacenamos en un boolean si se ha descargado el siguiente archivo o no
				boolean descargado = descargarArchivo(archivoDescargar, rutaDescargar);
				// Si se ha descargado se realiza lo siguiente
				if (descargado) {
					// Se muestra un mensaje
					System.out.println("El archivo se ha descargado correctamente.");
				} else {
					// Si no tambien se muestra otro de error.
					System.out.println("No se ha podido descargar el archivo del servidor " + servidor);
				}
				break;
			case 5:
				System.out.println("Por favor, indica el archivo a subir:");
				String archivoSubir = sc.next();
				System.out.println("Indica la ruta de destino en el ftp:");
				String rutaDestino = sc.next();
				boolean subido = subirArchivo(rutaDestino, archivoSubir);
				if (subido) {
					System.out.println("El archivo se ha subido correctamente.");
				} else {
					System.out.println("La subida ha fallado.");
				}
				// Subir archivo
				break;
			case 6:
				// Solo se desconecta del servidor
				desconectar();
				break;

			case 7:
				// Salimos del bucle y mostramos mensaje
				desconectar();
				System.out.println("Has salido correctamente. También se ha hecho una desconexión del ftp.");
				break;

			default:
				break;
			}
			opcion = sc.nextInt();
		}
		/*
		boolean subido = subirArchivo("/home/usuario/archivo.txt", "archivo.txt");
		if (subido) {
			System.out.println("El archivo se ha subido correctamente");
		} else {
			System.out.println("No se ha podido subir el archivo");
		}
		 */
	}
	public void decirHola() {
		System.out.println("Hola");
	}
	/**
	 * Método conectar al un servidor
	 */
	public void conectar() {
		// Objecto de la clase FTPClient, que es de Apache Commnons Net
		ftp = new FTPClient();
		try {
			// Conectamos al servidor recogido en la variable
			ftp.connect(servidor);
			// Logueamos con el usuario y contraseña almacenado en las variables correspondientes
			ftp.login(usuario, password);
			// 
			ftp.enterLocalPassiveMode();
			// Seleccionamos el tipo de archivo
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			// Enviamos un mensaje
			System.out.println("El usuario=" + usuario + " A CONECTADO correctamente con el servidor=" + servidor);
			System.out.println(ftp.getReplyString());
		} catch (IOException e) {
			// En caso de error enviamos el siguiente mensaje
			System.out.println("El usuario=" + usuario + " NO puede conectar con el servidor=" + servidor);
			e.printStackTrace(); // También el mensaje detallado
		}
	}
	/**
	 * Método desconectar del servidor
	 */
	public void desconectar() {
		try {
			// Cerramos la sesión
			ftp.logout();
			// Desconectamos
			ftp.disconnect();
			// Mensaje
			System.out.println("Conexión cerrada con " + servidor);
		} catch (IOException e) {
			// En caso de error mostramos un mensaje
			System.out.println("No se ha podido cerrar la conexión con el servidor " + servidor);
			e.printStackTrace(); // También un mensaje de error de tallado para más información
		}
	}
	/**
	 * Método para listar archivos mediante un array
	 * @return
	 */
	public String[] listarArchivos() {
		try {
			// Obtenemos la lista de nombres
			return ftp.listNames();
		} catch (IOException e) {
			// En caso de error mostramos un mensaje de error
			System.out.println("No se ha podido obtener la lista de archivos del servidor " + servidor);
			e.printStackTrace(); // Mensaje detallado
		}
		// Devolvemos un nulo
		return null;
	}
	/**
	 * Método booleano para buscar archivos
	 * @param archivo
	 * @return
	 */
	/*
	 * Se elige booleano para obtener un SI o un NO y saber si una busqueda
	 * se ha podido desarrollar correctamente y finalmente realizar las
	 * acciones que sean necesario.
	 */
	public boolean buscarArchivo(String archivo) {
		try {
			// Almacenamos en un array la lista de nombres
			String[] archivos = ftp.listNames();
			// Si la lista de nombre de archivo contiene algo, realizamos lo siguiente:
			if (archivos != null) {
				// Devolvemos la lista y su contenido
				return Arrays.asList(archivos).contains(archivo);
			}
			// En caso contrario mostramos una excepción
		} catch (IOException e) {
			e.printStackTrace(); // Mensaje detallado
		}
		// Devolvemos una respuesta false
		return false;
	}
	/**
	 * Método para descargar los archivos del ftp a nuestro ordenador
	 * @param archivoRemoto
	 * @param rutaLocal
	 * @return
	 */
	/*
	 * Necesitamos que sea booleano, para determinar si la acción ha
	 * salido correcta o no, y también saber si hacer una acción u otra.
	 */
	public boolean descargarArchivo(String archivoRemoto, String rutaLocal) {
		// Intentamos que un fichero pueda ser escribido, en este caso del servidor
		// a nuestro ordenador. Si esto es posible, se realiza lo siguiente:
		try (OutputStream outputStream = new FileOutputStream(rutaLocal + archivoRemoto)) {
			// Devolvemos una respuesta: En este caso es el archivo.
			return ftp.retrieveFile(archivoRemoto, outputStream);
			// En caso contrario mostramos una excepción
		} catch (IOException e) {
			e.printStackTrace(); // Mensaje detallado
		}
		// Devolvemos una respuesta false
		return false;
	}
	/**
	 * Subir archivo al ftp
	 * @param rutaLocal
	 * @param archivoRemoto
	 * @return
	 */
    public boolean subirArchivo(String rutaLocal, String archivoRemoto) {
        try {
        	File file = new File(rutaLocal);
            InputStream inputStream = new FileInputStream(file);
            ftp.storeFile(archivoRemoto, inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
    } 
}
