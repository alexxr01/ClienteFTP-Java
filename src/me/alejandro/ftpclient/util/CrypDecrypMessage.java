package me.alejandro.ftpclient.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public abstract class CrypDecrypMessage {
	public static byte[] encrypt(byte[] message, 
			byte[] keyBytes)
			throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, 
			BadPaddingException, IllegalBlockSizeException {

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(message);
	}
	public static byte[] decrypt(byte[] encryptedMessage, 
			byte[] keyBytes) 
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, 
			BadPaddingException, IllegalBlockSizeException {

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(encryptedMessage);
	}
}
