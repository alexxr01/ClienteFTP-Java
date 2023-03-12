package me.alejandro.ftp;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;

public class DamFtpClient {
	private FTPClient ftpClient;
	private String server;
    private int port = 21;
    private String user = "usuario";
    private String pass = "password";

    public DamFtpClient(String server) {
    	this.server = server;
    }
    
	public void loginFtp() {
		this.ftpClient = new FTPClient();
		try {
			this.ftpClient.connect(this.server, this.port);
		} catch (SocketException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
