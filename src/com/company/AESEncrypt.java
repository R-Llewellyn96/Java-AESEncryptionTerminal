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

// import java scanner library
import java.util.Scanner;

// Main class for performing AES encryption and decryption
public class AESEncrypt {

	public static Scanner scanner2 = new Scanner(System.in);

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
		final String password = getUserInput(1);

		// Define salt to apply to password before hashing
		final String salt = getUserInput(2);

		// Generate secure key using password return key to method caller
		return UtilAES.getKeyFromPassword(password, salt);
	}

	// Return key based on key generation method selected by user
	public static SecretKey keyMethodSelection(int keyGenMethod, int keySize) throws NoSuchAlgorithmException, InvalidKeySpecException {

		// User has selected to generate key from provided password
		if (keyGenMethod == 1) {

			// Get password based encryption inputs, generate key and return to caller
			return passwordBasedInputs();

			// User has selected to provide their own key
		} else if (keyGenMethod == 2) {

			// Define user input key EXAMPLE: 5EMkroyxam/w+pseq8anu1yXTUPmXfMDG/H8TPr9S5w=
			String userInputKey = getUserInput(2);

			// Return user input key to caller
			return UtilAES.stringToSecretKey(userInputKey);

			// if no selection made, default to random AES key generation
		} else {

			// Generate secure random key for AES
			return UtilAES.generateKey(256);
		}
	}

	// Get message for encryption from user input prompt and return to caller
	public static String getUserInput(int promptSelection){

		// Define user input message
		String userInputPromptMessage = "";

		// If else switch statement, changes message based on prompt selection input to method
		if (promptSelection == 0) {
			userInputPromptMessage = "Enter the message you wish to encrypt, NOTE: Must not be blank!: ";
		} else if (promptSelection == 1) {
			userInputPromptMessage = "Enter the password you wish to use for key generation, " +
					"NOTE: Must not be blank!: ";
		} else if (promptSelection == 2) {
			userInputPromptMessage = "Enter the salt you wish to use for key generation, " +
					"NOTE: Must not be blank!: ";
		} else if (promptSelection == 3) {
			userInputPromptMessage = "Enter the pre-generated key you wish to use for encryption, " +
					"NOTE: Must not be blank!: ";
		}

		// Define user input string
		String userInput = "";

		// Generate new scanner object
		//Scanner scanner = new Scanner(System.in);

		// Remove trailing whitespace from user input and check string is not empty
		while (userInput.trim().isEmpty()){

			// Prompt user to enter message for encryption
			System.out.println(userInputPromptMessage);

			// Read user input
			userInput = scanner2.nextLine();
		}

		// Close scanner after getting input
		//scanner.close();

		// Return user input as message string to caller
		return userInput.trim();
	}
	
	// Main method, used for calling methods
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

		////// Arguments for program use, add to CLI args for conversion of program to CLI based program //////

		// Define encryption algorithm to use
		String encryptionAlgorithm = "AES/CBC/PKCS5Padding";

		// Define AES bit length to choose from
		final int[] encryptionBitLength = {128, 256};

		// Define AES bit length chosen, 128 or 256
		int encryptionBitLengthChoice = encryptionBitLength[0];

		// Select key generation method
		int keyGenMethod = 1;

		// Select user input prompt selection
		int UserInputPromptSelection = 0;

		////// End of CLI Args //////

		// Get user input message after validation
		String message = getUserInput(UserInputPromptSelection);

		// Get key from key generation method selected
		SecretKey key = keyMethodSelection(keyGenMethod, encryptionBitLengthChoice);

		// Close scanner
		scanner2.close();

		// Begin Message encryption using generated key, encryption algorithm and message
		methodCaller(key, encryptionAlgorithm, message);

	// End of main method
	}
	
// End of class
}
