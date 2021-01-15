package com.company;
/**
 * Project Java-AESEncryption
 * File: AESEncrypt.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: perform AES Encryption and Decryption.
 */


// import java crypto libraries
import javax.crypto.SecretKey;

// import java security libraries
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

// Main class for performing AES encryption and decryption - Terminal Based
public class AESEncrypt {

	// If user has selected a combined IV and Ciphertext input for decryption,
	// strip both from combined string and return to caller
	public static String[] handleIVAndCiphertextSelection() {

		// Prompt user for cipher text to decrypt which includes IV
		String userIvAndCiphertext = UtilGetTerminalInputs.getUserInput(13);

		// Strip IV from Ciphertext and separate them,
		// Return array containing 0 = IV, 1 = Ciphertext to caller
		return UtilAES.stripIVFromCiphertext(userIvAndCiphertext);
	}
	
	// Main method, used for calling methods
	public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {

		// Define encryption algorithm to use
		String encryptionAlgorithm = "AES/CBC/PKCS5Padding";

		// Define AES bit length to choose from
		final int[] encryptionBitLength = {128, 256};

		// Define choice of AES encryption bit length
		int encryptionBitLengthChoice = UtilGetTerminalInputs.getAESKeyLength();

		// Check user selection of key length, 1 = 128 bit, 2 = 256 bit
		if (encryptionBitLengthChoice == 1) {

			// Define AES bit length chosen, 0 = 128, 1 = 256
			encryptionBitLengthChoice = encryptionBitLength[0];

			// Default choice of 256 bit AES encryption
		} else {

			// Define AES bit length chosen, 0 = 128, 1 = 256
			encryptionBitLengthChoice = encryptionBitLength[1];
		}

		// Define program mode selection value, 1 = encrypt, 2 = decrypt
		int programModeSelection = UtilGetTerminalInputs.getProgramModeChoice();

		// Define program behavior in Encryption mode
		if (programModeSelection == 1) {

			// Tell User that encryption mode has been selected
			System.out.println("Program starting in Encryption Mode...");

			// Define string to store user input for key generation method
			int keyGenMethod = UtilGetTerminalInputs.getKeyGenMethodChoice();

			// Generate Secret Key
			SecretKey key = UtilSecretKeyHandlers.setKeyGenMethod(keyGenMethod, encryptionBitLengthChoice);

			// Convert key to string
			String keyString = UtilAES.secretKeyToString(key);

			// Perform encryption and ask user if they wish to encrypt another message
			UtilGetTerminalInputs.getRepeatingEncryption(encryptionAlgorithm, keyString);


			// Program mode set to Decryption
		} else if (programModeSelection == 2) {

			// Choice value for using combined or separate iv and ciphertext
			int combinedIvAndCipherChoice = UtilGetTerminalInputs.getCombinedIVAndCiphertextInput();

			// Get symmetric key from user
			String userDecryptionKeyString = UtilGetTerminalInputs.getKeyInput();

			// Convert input key string into Secret Key
			SecretKey userDecryptionKey = UtilAES.stringToSecretKey(userDecryptionKeyString);

			/*
			// Define ivString
			String ivString = "";

			// Define Ciphertext
			String ciphertext = "";


			if (combinedIvAndCipherChoice == 1) {

				// Define initialisation vector
				ivString = UtilGetTerminalInputs.getIVInput();

				// If user has selected to use combined iv and ciphertext
			} else {

				// Prompt user for cipher text to decrypt which includes IV
				String[] userIvAndCiphertext = handleIVAndCiphertextSelection();

				ivString = userIvAndCiphertext[0];

				ciphertext = userIvAndCiphertext[1];

			}*/

			// Convert decryption key to string
			String userDecryptionKeyStringConv = UtilAES.secretKeyToString(userDecryptionKey);

			// Perform Decryption and ask user if they would like to Decrypt another message
			UtilGetTerminalInputs.getRepeatingDecryption(encryptionAlgorithm, userDecryptionKeyStringConv, combinedIvAndCipherChoice);

		}

		// End of main method
		System.out.println("\nProgram Terminated.");
	}
// End of class
}