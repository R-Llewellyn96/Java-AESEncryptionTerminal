package com.company;
/**
 * Project Java-AESEncryption
 * File: UtilAES.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: A Utility class to perform AES Encryption and Decryption, 
 * along with key generation both secure random and password based, including the creation of an initialisation vector.
 */

// import java security libraries

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class UtilAES {

	// Generate Secret Key for AES, Symmetric key used for encryption and decryption
	public static SecretKey generateKey(int keySize) throws NoSuchAlgorithmException {

		// Create key generator object and define instance as AES Symmetric Key
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			
		// Initialise key generator
		keyGenerator.init(keySize);
		    
		// Generate key and assign value to key and return key object to method caller
		return keyGenerator.generateKey();
	}
		
	// Generate Secret Key from user given password, note this is much less secure than random generation
	public static SecretKey getKeyFromPassword(String password, String salt)
				throws NoSuchAlgorithmException, InvalidKeySpecException {
			
		// Create key generator object and define instance as Password Based Encryption with SHA256 hashing
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			
		// Define key specifications for password based encryption, convert password to byte array,
		// apply salt and use SHA256 to hash the result
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		    
		// Generate secret key using PBKDF2 algorithm and specifications defined above and
		// return secret key object to method caller
		return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
	}
		
	// AES Requires an initialisation vector in CBC mode,
	// this method generates an initialisation vector in a securely random way
	public static IvParameterSpec generateIvParamSpec(byte[] iv) {
		    
		// Return initialisation vector to method caller
		return new IvParameterSpec(iv);
	}

	// Generate Initialisation Vector
	public static byte[] generateIV() {

		// Define initialisation vector as a 16 bit byte
		byte[] iv = new byte[16];

		// Generate random byte string in a secure way
		new SecureRandom().nextBytes(iv);

		// Return Initialisation Vector to caller
		return iv;
	}
		
	// Begin AES Encryption using algorithm, message, key and initialisation vector
	public static String encrypt(String algorithm, String message, SecretKey key,
			    IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
			    InvalidAlgorithmParameterException, InvalidKeyException,
			    BadPaddingException, IllegalBlockSizeException {
			
		// Define cipher being used
		Cipher cipher = Cipher.getInstance(algorithm);
			
		// Initialise cipher for encryption
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			
		// Define cipher-text as encrypted input string
		byte[] cipherText = cipher.doFinal(message.getBytes());
			
		// Encode byte array of cipher-text to a string and return cipher-text as
		// a string object to method caller
		return Base64.getEncoder().encodeToString(cipherText);
	}
		
	// Begin AES Decryption using algorithm, cipher-text, key and initialisation vector
	public static String decrypt(String algorithm, String cipherText, SecretKey key,
			    IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
			    InvalidAlgorithmParameterException, InvalidKeyException,
			    BadPaddingException, IllegalBlockSizeException {
			    
		// Define cipher being used
		Cipher cipher = Cipher.getInstance(algorithm);
			
		// Initialise cipher for decryption
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
			
		// Define plain-text as decrypted byte array of cipher-text
		byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
			   
		// Convert byte array to a standard string object and return plain text as
		// a string object to method caller
		return new String(plainText);
	}
		
	// Take string and convert to secret key object
	public static SecretKey stringToSecretKey(String keyString) {
			
		// Decode the base 64 encoded key string
		byte[] decodedKey = Base64.getDecoder().decode(keyString);
			
		// Return secret key string as a secret key object to method caller
		return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	}
		
	// Take secret key object and convert to string
	public static String secretKeyToString(SecretKey key) {
			
		// Return secret key as a string to method caller
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
		
// End of class
}








