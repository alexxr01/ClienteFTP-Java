package me.alejandro.ftpclient;

import java.util.Scanner;

public class FtpClientApp {
	private final int SHOW_CONNECTIONS=1;
	private final int SEL_CONNECTION=2;
	private final int OPEN_CONNECTION=3;
	private final int EXIT=10;
	private FtpClientConfig ftpConfig;
	private ConnectionManager ftpConnectionManager;
	private Scanner sc;
	
	public static void main(String[] args) {
		FtpClientApp damFtpClient = new FtpClientApp();
		damFtpClient.ftpConfig = FtpClientConfig.instance();
		damFtpClient.sc = new Scanner(System.in);
		damFtpClient.menuPrincipal();
	}
	
	private void menuPrincipal() {
		int opcion = 0;
		while(opcion!=EXIT){
			opcion = Integer.valueOf(this.sc.nextLine());
			switch(opcion) {
			case SHOW_CONNECTIONS:
				break;
			case SEL_CONNECTION:
				break;
			case OPEN_CONNECTION:
				break;
			case EXIT:
				break;
			default:
				break;
			}
		}
	}
}
