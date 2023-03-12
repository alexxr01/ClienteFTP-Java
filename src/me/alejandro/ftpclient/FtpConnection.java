package me.alejandro.ftpclient;

import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpConnection implements Runnable, Comparator {
	private String user;
	private String pass;
	private String name;
	private String address;
	private int port;
	private Boolean connected;
	private ArrayList<String> replies;
	private FTPClient ftpConnection;
	private int instant;
	
	public FtpConnection(String name, String address, int port, String user, 
			String pass) {
		this.user = user;
		this.pass = pass;
		this.name = name;
		this.address = address;
		this.port = port;
		this.replies = new ArrayList<String>();
	}	
	@Override
	public void run() {				
		while(!this.connected && Math.abs(LocalDateTime.now().getSecond()-instant)<10)
			this.connect();
	}
	@Override
	public int compare(Object arg0, Object arg1) {		
		return ((FtpConnection)arg0).getName().compareTo(((FtpConnection)arg1).getName());
	}
	private void connect() {		
		if(!this.connected) {
			instant = LocalDateTime.now().getSecond();
			try {
				this.ftpConnection.connect(this.address, this.port);				

				int replyCode = this.ftpConnection.getReplyCode();
				if (!FTPReply.isPositiveCompletion(replyCode)) {
					this.replies.add("Operation failed. Server reply code: " + replyCode);	                
				} else {
					this.replies.add("Connected to " + this.name);
				}

				boolean success = this.ftpConnection.login(this.user, this.pass);
				if (!success) {
					this.replies.add("Could not login to the server");
					this.connected = false;
				} else {
					this.replies.add("LOGGED IN SERVER");
					this.connected = true;
				}
			} catch (SocketException e) {				
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean close() {
		this.connected = false;
		try {
			this.ftpConnection.disconnect();
			return true;
		} catch (IOException e) {		
			e.printStackTrace();
			return false;
		}
	}
	public ArrayList<String> getServerReply() {
		this.replies.clear();
		String[] response= this.ftpConnection.getReplyStrings();

		if (response != null && response.length > 0)
			this.replies.addAll(Arrays.asList(response));
		else
			this.replies.add("No hay respuesta del servidor");
		return this.replies;
	}
	public int getPort() {
		return this.port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	public boolean getConnected() {
		return this.connected;
	}
}
