package pwr.edu.app.library;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class ReadKeys {
	private static String prvKey;

	public static String getPrvKey(String path) throws Exception {
		PrivateKey key = readPrivateKey(new File("RSA/privateKey"));
		prvKey= Base64.getEncoder().encodeToString(key.getEncoded());
		return prvKey;
	}
    public static PrivateKey readPrivateKey(File keyFile) throws Exception {// read key bytes
	FileInputStream in = new FileInputStream(keyFile);
	byte[] keyBytes = new byte[in.available()];
	in.read(keyBytes);
	in.close();

	String privateKey = new String(keyBytes, "UTF-8");
	privateKey = privateKey.replaceAll("(-+BEGIN RSA PRIVATE KEY-+\\r?\\n|-+END RSA PRIVATE KEY-+\\r?\\n?)", "");

	// generate private key
	PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	return keyFactory.generatePrivate(spec);
    }
	public static PublicKey readPublicKey(File keyFile) throws Exception {// read key bytes
		FileInputStream in = new FileInputStream(keyFile);
		byte[] keyBytes = new byte[in.available()];
		in.read(keyBytes);
		in.close();

		String publicKey = new String(keyBytes, "UTF-8");
		publicKey = publicKey.replaceAll("(-+BEGIN RSA PUBLIC KEY-+\\r?\\n|-+END RSA PUBLIC KEY-+\\r?\\n?)", "");

		// generate private key
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(spec);
	}
	public static String getPubKey(String path) throws Exception {
		String content;
		return content = Files.readString(Path.of(path), StandardCharsets.US_ASCII);
	}

	public ReadKeys(){
		this.prvKey=null;
	}
}