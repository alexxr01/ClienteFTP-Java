package me.alejandro.ftpclient;

import java.util.ArrayList;

import org.eclipse.jdt.annotation.NonNull;

public class ConnectionManager {
	private ArrayList<FtpConnection> connections;
	private FtpClientConfig ftpConfig;
	
	public ConnectionManager(FtpClientConfig ftpConfig) {
		this.ftpConfig = ftpConfig;
		this.connections = this.ftpConfig.getConnections();
	}
	
	public void addConnection(@NonNull FtpConnection ftpConnection) {
		this.connections.add(ftpConnection);
	}
	
	public ArrayList<FtpConnection> getConnections() {
		return this.connections;
	}
}
