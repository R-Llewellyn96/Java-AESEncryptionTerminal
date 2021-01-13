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
	public static void methodCallerEncrypt(SecretKey key, String encryptionAlgorithm, String message) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		// Generate Initialisation Vector
		byte[] iv = UtilAES.generateIV();

		// Generate parameter spec
		IvParameterSpec ivParamSpec = UtilAES.generateIvParamSpec(iv);

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

	// Method Caller
	public static void methodCallerDecrypt(SecretKey key, String encryptionAlgorithm, String cipherText, String iv) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		// Generate parameter spec
		IvParameterSpec ivParamSpec = UtilAES.generateIvParamSpec(UtilsConv.hexStringToByteArray(iv));

		// Decrypt Cipher-text into plain text
		String plainText = UtilAES.decrypt(encryptionAlgorithm, cipherText, key, ivParamSpec);

		// Convert key to string to allow for display &
		// Print AES key being used to terminal
		System.out.println("AES Symmetric key used : " + UtilAES.secretKeyToString(key));

		// Print Initialisation Vector
		System.out.println("IV: " + iv);

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
			String userInputKey = getUserInput(3);

			// Return user input key to caller
			return UtilAES.stringToSecretKey(userInputKey);

			// if no selection made, default to random AES key generation
		} else {

			// Generate secure random key for AES
			return UtilAES.generateKey(keySize);
		}
	}

	// Get message for encryption from user input prompt and return to caller
	public static String getUserInput(int promptSelection) {

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
		} else if (promptSelection == 4) {
			userInputPromptMessage = """
					Select program mode,
					NOTE: Must not be blank!
					[1] = Encrypt, [2] = Decrypt:\s""";
		} else if (promptSelection == 5) {
			userInputPromptMessage = """
					Select Encryption mode,
					NOTE: Must not be blank!
					[1] = Random, [2] = Password + Salt, [3] = User Provided Key:\s""";
		} else if (promptSelection == 6) {
			userInputPromptMessage = "Enter Ciphertext to Decrypt";
		} else if (promptSelection == 7) {
			userInputPromptMessage = "Enter Key for Decryption";
		} else if (promptSelection == 8) {
			userInputPromptMessage = "Enter initialisation vector";
		}

		// Define user input string
		String userInput = "";

		// Remove trailing whitespace from user input and check string is not empty
		while (userInput.trim().isEmpty()){

			// Prompt user to enter message for encryption
			System.out.println(userInputPromptMessage);

			// Read user input
			userInput = scanner2.nextLine();

			// Check whether user provided key is more than 2 characters
			if (promptSelection == 3) {

				// Check that length of input key is greater than 2 characters
				if ((userInput.trim()).length() != 44) {
					userInput = "";

					System.out.println("WARNING, Key Length must be exactly 44 characters!");
					System.out.println("IJEAVZ7eOIadyjJSeWuTvD2KSlYeaulVeYFO7BQnmYY=".length());
				}
			} else if (promptSelection == 7) {

				// Check that length of input key is greater than 2 characters
				if (((userInput.trim()).length() != 44) && ((userInput.trim()).length() != 32)) {
					userInput = "";

					System.out.println("WARNING, Key Length must be exactly 32 or 44 characters!");
					System.out.println("IJEAVZ7eOIadyjJSeWuTvD2KSlYeaulVeYFO7BQnmYY=".length());
				}

			}
		}

		// Close scanner after getting input
		//scanner.close();

		// Return user input as message string to caller
		return userInput.trim();
	}

	// Method for setting keygen method
	public static SecretKey setKeyGenMethod(int keyGenMethod, int encryptionBitLengthChoice) throws InvalidKeySpecException, NoSuchAlgorithmException {

		//int keyGenMethod = Integer.parseInt(keyGenMethodString);

		// Define program behaviour depending upon user keygen method chosen
		// User has chosen random keygen method as default = 0
		// Define program behaviour for password based key generation = 1
		// Define program behaviour for User provided key = 2
		// Get key from key generation method selected
		/*return switch (keyGenMethod) {
			case 2 -> keyMethodSelection(1, encryptionBitLengthChoice);
			case 3 -> keyMethodSelection(2, encryptionBitLengthChoice);
			default -> keyMethodSelection(0, encryptionBitLengthChoice);
		};*/

		if (keyGenMethod == 2) {
			return keyMethodSelection(1, encryptionBitLengthChoice);
		} else if (keyGenMethod == 3) {
			return keyMethodSelection(2, encryptionBitLengthChoice);
		} else {
			return keyMethodSelection(0, encryptionBitLengthChoice);
		}

	}
	
	// Main method, used for calling methods
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

		// Define program mode selection value, 1 = encrypt, 2 = decrypt
		int programModeSelection = 0;

		// Define encryption algorithm to use
		String encryptionAlgorithm = "AES/CBC/PKCS5Padding";

		// Define AES bit length to choose from
		final int[] encryptionBitLength = {128, 256};

		// Define AES bit length chosen, 128 or 256
		int encryptionBitLengthChoice = encryptionBitLength[1];

		// While loop for reminding user to select valid encryption or decryption mode
		while (programModeSelection != 1 && programModeSelection != 2) {

			// Prompt user for program mode, Encrypt or Decrypt
			String programModeSelectionString = getUserInput(4);

			// Attempt string to integer conversion on user input, if it fails repeat the loop and try again
			try {

				// Read user input and attempt integer conversion
				programModeSelection = Integer.parseInt(programModeSelectionString.trim());

				// Catch any errors and handle failure to convert input to integer
			} catch (Exception e) {

				// Set program mode selection to zero so that while loop runs again
				programModeSelection = 0;

				// Prompt user of input error and retry
				System.out.println("ERROR, Could not convert input to a number, please enter a number!");
			}
		}

		// Define program behavior in Encryption mode
		if (programModeSelection == 1) {

			// Tell User that encryption mode has been selected
			System.out.println("Program starting in Encryption Mode...");

			// Define string to store user input for key generation method
			int keyGenMethod = 0;

			// Ask user which key generation method they wish to use, 1 = Random, 2 = Password, 3 = User provided
			while (keyGenMethod != 1 && keyGenMethod != 2 && keyGenMethod != 3) {

				// Prompt user for key generation method mode, random = 0, password = 1, user provided = 3
				String keyGenMethodString = getUserInput(5);

				// Attempt string to integer conversion on user input, if it fails repeat the loop and try again
				try {

					// Read user input and attempt integer conversion
					keyGenMethod = Integer.parseInt(keyGenMethodString.trim());

					// Catch any errors and handle failure to convert input to integer
				} catch (Exception e) {

					// Set mode selection to zero so that while loop runs again
					keyGenMethod = 0;

					// Prompt user of input error and retry
					System.out.println("ERROR, Could not convert input to a number, please enter a number!");
				}
			}

			SecretKey key = setKeyGenMethod(keyGenMethod, encryptionBitLengthChoice);

			// Get user input message after validation
			String message = getUserInput(0);

			// Close scanner
			scanner2.close();

			// Begin Message encryption using generated key, encryption algorithm and message
			methodCallerEncrypt(key, encryptionAlgorithm, message);

		} else if (programModeSelection == 2) {

			// Prompt user for cipher text to decrypt
			String userCiphertext = getUserInput(6);

			// Define decryption key both string and secret key
			String userDecryptionKeyString = "";
			SecretKey userDecryptionKey = null;

			// Wrapped while loop to ensure key input
			while (userDecryptionKeyString.trim().isEmpty()) {

				// Prompt user for decryption key
				userDecryptionKeyString = getUserInput(7);

				// attempt string conversion to key
				try {

					// Define secret key and convert input string to key
					userDecryptionKey = UtilAES.stringToSecretKey(userDecryptionKeyString.trim());

					// If it fails prompt user, clear input and try again
				} catch (Exception e) {

					// Prompt user of conversion error
					System.out.println("ERROR!: Failure converting input to key, please check your inputs and try again!");

					// Set input string to empty to allow for retry
					userDecryptionKeyString = "";

				}
			}

			// Define initialisation vector
			String iv = "";

			// Wrapped while loop to ensure key input
			while (iv.trim().isEmpty()) {

				try {

					// Prompt user for initialisation vector
					iv = getUserInput(8);

				} catch (Exception e) {

					// Prompt user of conversion error
					System.out.println("ERROR!: Failure converting input to initialisation vector, please check your inputs and try again!");

					// Set input string to empty to allow for retry
					iv = "";

				}
			}

			// Call methods for Decryption
			methodCallerDecrypt(userDecryptionKey, encryptionAlgorithm, userCiphertext.trim(), iv);
		}

		////// Arguments for program use, add to CLI args for conversion of program to CLI based program //////

		////// End of CLI Args //////




	// End of main method
	}
	
// End of class
}
