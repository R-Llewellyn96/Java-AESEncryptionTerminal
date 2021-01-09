package com.company;
/**
 * Project Java-AESEncryption
 * File: AESEncrypt.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: perform AES Encryption and Decryption.
 */

// import java security libraries

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

// Main class for performing AES encryption and decryption
public class AESEncrypt {
	
	// Main method, used for calling methods
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
		
		// Define User input message
		String message = "Testing of AES CBC and PKCS5Padding, this is a test of JCE on this subject"
				+ "matter and an evaluation of the java crpytography extension for use as a text encryptor";
		
		// Define encryption algorithm to use
		String EncryptionAlgorithm = "AES/CBC/PKCS5Padding";
		
		int keyGenMethod = 0;
		
		// User has selected to generate key from provided password
		if (keyGenMethod == 1) {
			
			// Define password to generate key from			
			final String password = "password123";
						
			// Define salt to apply to password before hashing
			final String salt = "12345";
			
			// Generate secure key using password
			final SecretKey keyPBE = UtilAES.getKeyFromPassword(password, salt);
			
			// Convert key to string to allow for display &
			// Print AES key being used to terminal
			System.out.println("AES Symmetric key used: " + UtilAES.secretKeyToString(keyPBE));
			
			// Generate Initialisation Vector
			IvParameterSpec ivParamSpec = UtilAES.generateIv();
			
			// Generate Cipher-text using encryption
			String cipherText = UtilAES.encrypt(EncryptionAlgorithm, message, keyPBE, ivParamSpec);
			
			// Decrypt Cipher-text into plain text
			String plainText = UtilAES.decrypt(EncryptionAlgorithm, cipherText, keyPBE, ivParamSpec);
			
			System.out.println("Input message: " + message);
			
			System.out.println("Encrypted Ciphertext: " + cipherText);
			
			System.out.println("Decrypted Ciphertext: " + plainText);
		
		// User has selected to provide their own key
		} else if (keyGenMethod == 2) {
			
			String userInputKey = "5EMkroyxam/w+pseq8anu1yXTUPmXfMDG/H8TPr9S5w=";
			
			// Convert input string from user to use as key for encryption
			final SecretKey keyUser = UtilAES.stringToSecretKey(userInputKey);
			
			// Convert key to string to allow for display &
			// Print AES key being used to terminal
			System.out.println("AES Symmetric key used: " + UtilAES.secretKeyToString(keyUser));
			
			// Generate Initialisation Vector
			IvParameterSpec ivParamSpec = UtilAES.generateIv();
			
			// Generate Cipher-text using encryption
			String cipherText = UtilAES.encrypt(EncryptionAlgorithm, message, keyUser, ivParamSpec);
			
			// Decrypt Cipher-text into plain text
			String plainText = UtilAES.decrypt(EncryptionAlgorithm, cipherText, keyUser, ivParamSpec);
			
			System.out.println("Input message: " + message);
			
			System.out.println("Encrypted Ciphertext: " + cipherText);
			
			System.out.println("Decrypted Ciphertext: " + plainText);
			
		// if no selection made, default to random AES key generation
		} else {
			
			// Generate secure random key for AES
			final SecretKey keyRandom = UtilAES.generateKey(256);
			
			// Convert key to string to allow for display &
			// Print AES key being used to terminal
			System.out.println("AES Symmetric key used: " + UtilAES.secretKeyToString(keyRandom));
			
			// Generate Initialisation Vector
			IvParameterSpec ivParamSpec = UtilAES.generateIv();
			
			// Generate Cipher-text using encryption
			String cipherText = UtilAES.encrypt(EncryptionAlgorithm, message, keyRandom, ivParamSpec);
			
			// Decrypt Cipher-text into plain text
			String plainText = UtilAES.decrypt(EncryptionAlgorithm, cipherText, keyRandom, ivParamSpec);
			
			System.out.println("Input message: " + message);
			
			System.out.println("Encrypted Ciphertext: " + cipherText);
			
			System.out.println("Decrypted Ciphertext: " + plainText);
			
		}

	// End of main method
	}
	
// End of class
}
