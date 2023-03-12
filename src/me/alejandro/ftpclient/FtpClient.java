package me.alejandro.ftpclient;

import java.util.ArrayList;
import java.util.Scanner;


public class FtpClient implements Runnable{
	private final int CONNECT = 1;
	private final int SEL_FOLDER = 2;
	private final int MK_FOLDER = 3;
	private final int EXIT = 100;
	private FtpClientConfig ftpConfig;
	private FtpConnection ftpConnection;
	private boolean close = false;
	private Scanner sc;
	
	public FtpClient(FtpClientConfig ftpConfig) {
		this.ftpConfig = ftpConfig;
	}

	public boolean connect() {
		Thread newConnection = new Thread(ftpConnection);
		newConnection.start();
		return ftpConnection.getConnected();
	}

	public boolean close() {
		if(this.ftpConnection.getConnected())
			return this.ftpConnection.close();
		return true;
	}

	@Override
	public void run() {
		while(!close) {
			this.mainMenu();
		}
	}

	private void mainMenu() {
		this.sc = new Scanner(System.in);
		ArrayList<String> serverReply;
		int opcion=0;
		while(opcion!=EXIT) {
			System.out.println("Seleccione una opción:");
			System.out.println("1. Conectar");
			System.out.println("2. Seleccionar carpeta");
			System.out.println("3. Crear carpeta");
			System.out.println("100. Salir");
			opcion = Integer.valueOf(sc.nextLine());
			switch(opcion) {
			case CONNECT:
				this.connect();
				serverReply = this.ftpConnection.getServerReply();
				if(serverReply.size()>0) {
					for(String reply:serverReply) {
						System.out.println(reply);
					}
				}
				System.out.println("Pulse 0 para continuar");
				this.sc.nextLine();
				break;
			case SEL_FOLDER:

				break;
			case MK_FOLDER:
				break;
			default:
				break;
			}
		}
	}
}
