package me.alejandro.ftpclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FtpClientConfig {
	private static FtpClientConfig config = null;
	private ArrayList<FtpConnection> connections;
	private ClassLoader classLoader;

	private FtpClientConfig() {
		String line;
		String[] fields;
		this.classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("ftpconfig.data");
		this.connections = new ArrayList<FtpConnection>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		try {
			//connectionname#address#puerto#usuarioftp#claveftp
			while(reader.ready()) {
				line = reader.readLine();
				fields = line.split("#");
				this.connections.add(new FtpConnection(fields[0],
						fields[1], Integer.valueOf(fields[2]), fields[3], fields[4]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
	}
	public static FtpClientConfig instance() {
		if(FtpClientConfig.config == null) 
			FtpClientConfig.config = new FtpClientConfig();
		return FtpClientConfig.config;
	}
	public ArrayList<FtpConnection> getConnections(){
		return this.connections;
	}
	public void updateConnections() {
		File dataFile=null;
		File tempFile = null;
		String dataConnection;
		PrintWriter writer = null;
		try {
			dataFile = new File(this.classLoader.getResource("ftpconfig.data").toString());
			tempFile = new File(this.classLoader.getResource("ftpconfig.temp").toString());
			writer = new PrintWriter(tempFile);
			this.connections.sort(this.connections.get(0));
			for(FtpConnection ftpConnection:this.connections) {
				dataConnection = ftpConnection.getName()+ "#" +
						ftpConnection.getAddress() + "#" +
						ftpConnection.getPort() + "#" +
						ftpConnection.getUser() + "#" +
						ftpConnection.getPass();
				writer.println(dataConnection);
			}			
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}finally {
			writer.close();
			dataFile.delete();
			tempFile.renameTo(dataFile);
		}
	}
}
