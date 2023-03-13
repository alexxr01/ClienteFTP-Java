package me.alejandro.ftp;

public class TestFTP {
	public static void main(String[] args) {
		ClienteFTP cliente1 = new ClienteFTP("ftp.servidor1.com", "usuario1", "password1");
		ClienteFTP cliente2 = new ClienteFTP("ftp.servidor2.com", "usuario2", "password2");

		Thread hiloCliente1 = new Thread(cliente1);
		Thread hiloCliente2 = new Thread(cliente2);

		hiloCliente1.start();
		hiloCliente2.start();
	}
}
