package logic;

/*
 * Project Java-AESEncryption
 * File: LogicIV.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: A Utility class to perform Initialisation Vector generation and manipulation.
 */

// import java crypto and security libraries
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public class LogicIV {
		
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
		return LogicHexConv.toHex(LogicIV.generateIV());
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
		
// End of class
}
