package hn.com.tigo.simcardinquiry.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptDecrypt {

	public static String encript(final String param) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		byte[] iv = "87654321".getBytes("UTF-8");
		byte[] key = "U2ltY2FyZA==".getBytes("UTF-8");
		SecretKeySpec skeySpec = new SecretKeySpec(key, "RC2");
		RC2ParameterSpec ivSpec = new RC2ParameterSpec(key.length * 8, iv);
		Cipher cipher = Cipher.getInstance("RC2/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
		byte[] encrypted = cipher.doFinal(param.getBytes("UTF-8"));
		return new String(Base64.encodeBase64(encrypted));
	}

	public static String decript(final String param) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		byte[] iv = "87654321".getBytes("UTF-8");
		byte[] key = "U2ltY2FyZA==".getBytes("UTF-8");
		SecretKeySpec skeySpec = new SecretKeySpec(key, "RC2");
		RC2ParameterSpec ivSpec = new RC2ParameterSpec(key.length * 8, iv);
		Cipher cipher = Cipher.getInstance("RC2/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
		byte[] encrypted = cipher.doFinal(Base64.decodeBase64(param.getBytes("UTF-8")));
		return new String(encrypted);
	}
}
