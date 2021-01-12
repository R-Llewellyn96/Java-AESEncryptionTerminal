package com.company;
/**
 * Project Java-AESEncryption
 * File: AESEncrypt.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: perform AES Encryption and Decryption.
 */


// import java crypto libraries
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

// import java security libraries
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

// Main class for performing AES encryption and decryption
public class AESEncrypt {

	// Method Caller
	public static void methodCaller(SecretKey key, String encryptionAlgorithm, String message) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		// Generate Initialisation Vector
		IvParameterSpec ivParamSpec = UtilAES.generateIv();

		// Generate Cipher-text using encryption
		String cipherText = UtilAES.encrypt(encryptionAlgorithm, message, key, ivParamSpec);

		// Decrypt Cipher-text into plain text
		String plainText = UtilAES.decrypt(encryptionAlgorithm, cipherText, key, ivParamSpec);

		// Convert key to string to allow for display &
		// Print AES key being used to terminal
		System.out.println("AES Symmetric key used : " + UtilAES.secretKeyToString(key));

		// Print input message
		System.out.println("Input message          : " + message);

		// Print cipher text
		System.out.println("Encrypted Ciphertext   : " + cipherText);

		// Print decoded plaintext
		System.out.println("Decrypted Ciphertext   : " + plainText);
	}

	// Return key generated using password based inputs
	public static SecretKey passwordBasedInputs() throws InvalidKeySpecException, NoSuchAlgorithmException {

		// Define password to generate key from
		final String password = "password123";

		// Define salt to apply to password before hashing
		final String salt = "12345";

		// Generate secure key using password return key to method caller
		return UtilAES.getKeyFromPassword(password, salt);
	}

	// Return key based on key generation method selected by user
	public static SecretKey keyMethodSelection(int keyGenMethod) throws NoSuchAlgorithmException, InvalidKeySpecException {

		// User has selected to generate key from provided password
		if (keyGenMethod == 1) {

			// Get password based encryption inputs, generate key and return to caller
			return passwordBasedInputs();

			// User has selected to provide their own key
		} else if (keyGenMethod == 2) {

			// Define user input key
			String userInputKey = "5EMkroyxam/w+pseq8anu1yXTUPmXfMDG/H8TPr9S5w=";

			// Return user input key to caller
			return UtilAES.stringToSecretKey(userInputKey);

			// if no selection made, default to random AES key generation
		} else {

			// Generate secure random key for AES
			return UtilAES.generateKey(256);
		}
	}

	// Get message for encryption from user input prompt and return to caller
	public static String getUserMessageInput(){

		// Define User input message
		String message = "Testing of AES CBC and PKCS5Padding, this is a test of JCE on this subject"
				+ "matter and an evaluation of the java crpytography extension for use as a text encryptor";

		// Return message string to caller
		return message;
	}
	
	// Main method, used for calling methods
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

		// Define encryption algorithm to use
		String encryptionAlgorithm = "AES/CBC/PKCS5Padding";

		// Select key generation method
		int keyGenMethod = 2;

		// Get user input message after validation
		String message = getUserMessageInput();

		// Get key from key generation method selected
		SecretKey key = keyMethodSelection(keyGenMethod);

		// Begin Message encryption using generated key, encryption algorithm and message
		methodCaller(key, encryptionAlgorithm, message);

	// End of main method
	}
	
// End of class
}
