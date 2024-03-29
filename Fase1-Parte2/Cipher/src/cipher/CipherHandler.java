package cipher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import java.security.cert.Certificate;

/**
 * @author Ana Albuquerque 53512, Gonçalo Antunes 52831, Tiago Cabrita 52741
 */
public class CipherHandler {

	public static KeyStore getKeystore(String path, String password, String keystoreType) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyStoreException {
		KeyStore ks = KeyStore.getInstance(keystoreType);
		ks.load(new FileInputStream(path), password.toCharArray());
		return ks;
	}
	
	public static SecretKey generateKey(String algortihm) throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(algortihm);
		kg.init(128);
		return kg.generateKey();
	}
	
	public static byte[] encryptKey(Key key, Key pk) throws IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher c = Cipher.getInstance(pk.getAlgorithm());
		c.init(Cipher.WRAP_MODE, pk);
		return c.wrap(key);
	}
	
	public static Key decryptKey(byte[] wrappedKey, int type, Key decryptedKey, String algorithm) throws IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher c = Cipher.getInstance(decryptedKey.getAlgorithm());
        c.init(Cipher.UNWRAP_MODE, decryptedKey);
        return c.unwrap(wrappedKey, algorithm, type);
    }
	
	public static PrivateKey getPrivateKeyFromKeystorePath(String pathToKeystore, String ketstorePass, String keystoreAlias,
			String keystoreType) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, CertificateException, IOException {
		FileInputStream kfile = new FileInputStream(pathToKeystore);
		KeyStore kstore = KeyStore.getInstance(keystoreType);
		kstore.load(kfile,ketstorePass.toCharArray());
		return  (PrivateKey) kstore.getKey(keystoreAlias, ketstorePass.toCharArray( ));
	}

	public static Certificate getCertificate(String pathToKeystore, String keystorePass, String keystoreAlias,
			String keystoreType) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		FileInputStream kfile = new FileInputStream(pathToKeystore); 
		KeyStore kstore = KeyStore.getInstance(keystoreType);
		kstore.load(kfile,keystorePass.toCharArray());
		return kstore.getCertificate(keystoreAlias); 
	}
	
	public static Certificate getCertificateFromPath(String path) throws CertificateException, FileNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        CertificateFactory fac = CertificateFactory.getInstance("X509");
        return fac.generateCertificate(fis);
    }
	
	public static PublicKey getPublicKeyFromPathToKeystore(String pathToKeystore, String keystorePass, String keystoreAlias,
			String keystoreType) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		Certificate cert = getCertificate(pathToKeystore,keystorePass, keystoreAlias, keystoreType);
		return cert.getPublicKey(); 
	}

	public static boolean verifySignature(PublicKey pk, byte [] data, byte [] signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature s = Signature.getInstance("MD5withRSA");
		s.initVerify(pk);
		s.update(data);
		return s.verify(signature);
	}

	public static byte [] sign (byte[] buf, PrivateKey pk ) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(pk);
		signature.update(buf);
			
		return signature.sign();
	}

	public static PrivateKey getPrivateKeyFromKeystore(KeyStore keystore, String keystorePassword,
			String keyStoreAlias) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return (PrivateKey) keystore.getKey(keyStoreAlias, keystorePassword.toCharArray( ));
	}

	public static byte[] encryptMessage(Key key, byte[] content) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher c = Cipher.getInstance(key.getAlgorithm());
		c.init(Cipher.ENCRYPT_MODE, key);
		return c.doFinal(content);
	}

	public static byte[] decryptMessage(Key key, byte[] content) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher c = Cipher.getInstance(key.getAlgorithm());
		c.init(Cipher.DECRYPT_MODE, key);
		return c.doFinal(content);
	}
}