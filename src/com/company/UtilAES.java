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

	// Method for generation of IV string
	public static String generateIVString() {

		// Generate Initialisation Vector and
		// Convert Initialisation Vector to String
		return UtilsConv.toHex(UtilAES.generateIV());
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
		// Encode byte array of cipher-text to a string and return cipher-text as
		// a string object to method caller
		return Base64.getEncoder().encodeToString((cipher.doFinal(message.getBytes())));
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
		// Convert byte array to a standard string object and return plain text as
		// a string object to method caller
		return new String((cipher.doFinal(Base64.getDecoder().decode(cipherText))));
	}
		
	// Take string and convert to secret key object
	public static SecretKey stringToSecretKey(String keyString) {
			
		// Decode the base 64 encoded key string and
		// Return secret key string as a secret key object to method caller
		return new SecretKeySpec((Base64.getDecoder().decode(keyString)), 0, (Base64.getDecoder().decode(keyString)).length, "AES");
	}
		
	// Take secret key object and convert to string
	public static String secretKeyToString(SecretKey key) {
			
		// Return secret key as a string to method caller
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	// Append IV to Ciphertext for sending
	public static String addIVToCiphertext(String iv, String ciphertext) {

		// Return iv and ciphertext appended
		return iv + ciphertext;
	}

	// Strip IV from read ciphertext
	public static String[] stripIVFromCiphertext(String combinedIvAndCiphertext) {

		// Define character length of IV and get length of combined IV and Ciphertext input
		int ivLength = 32;
		int stringLength = combinedIvAndCiphertext.length();

		// Split string into IV and Ciphertext, NOTE String starts at zero
		// Return both as Strings in array including separated IV and Ciphertext
		return new String[]{(combinedIvAndCiphertext.substring(0, ivLength)), (combinedIvAndCiphertext.substring(ivLength, stringLength))};
	}

	// Encryption of full message, returns string array for display on UI
	public static String[] encryptMessage(SecretKey key, String encryptionAlgorithm, String message, String ivString) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		// Generate parameter spec
		IvParameterSpec ivParamSpec = UtilAES.generateIvParamSpec(UtilsConv.hexStringToByteArray(ivString));

		// Generate Cipher-text using encryption
		String cipherText = UtilAES.encrypt(encryptionAlgorithm, message, key, ivParamSpec);

		// Decrypt Cipher-text into plain text for checking of valid output
		String plainText = UtilAES.decrypt(encryptionAlgorithm, cipherText, key, ivParamSpec);

		// Return array of strings for output to UI
		return new String[]{UtilAES.secretKeyToString(key), ivString, cipherText, plainText};

	}

	// Decryption of full message, returns string array for display on UI
	public static String[] decryptMessage(SecretKey key, String encryptionAlgorithm, String cipherText, String ivString) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		// Generate parameter spec
		IvParameterSpec ivParamSpec = UtilAES.generateIvParamSpec(UtilsConv.hexStringToByteArray(ivString));

		// Decrypt Cipher-text into plain text
		String plainText = UtilAES.decrypt(encryptionAlgorithm, cipherText, key, ivParamSpec);

		// Return array of strings for output to UI
		return new String[]{UtilAES.secretKeyToString(key), ivString, cipherText, plainText};
	}
		
// End of class
}
