package cipher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import java.security.cert.Certificate;

public class CipherHandler {

	public static KeyStore getKeystore(String path, String password, String keystoreType) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyStoreException {
		KeyStore ks = KeyStore.getInstance(keystoreType);
		ks.load(new FileInputStream(path), password.toCharArray());
		return ks;
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


}