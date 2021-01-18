package logic;

/*
 * Project Java-AESEncryption
 * File: LogicKeyGen.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: Handles Secret Key generation logic.
 */

import com.company.UtilGetTerminalInputs;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class LogicKeyGen {

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

    // Function generates an array of keys equal to the length of chosen month
    public static String[] generateKeyArray(int noKeysToGen, int keySize) throws NoSuchAlgorithmException {

        // Declare and initialise string array of secret keys
        String[] keyArr = new String[noKeysToGen];

        // Generate a key for the number of days in month
        for (int i = 0; i < noKeysToGen; i++) {

            // generate a key, convert it to a string and store it in the key array as a string
            keyArr[i] = LogicConverters.secretKeyToString(LogicKeyGen.generateKey(keySize));
        }

        // Return the array of generated keys to caller
        return keyArr;
    }

    // Return key based on key generation method selected by user
    public static SecretKey keyMethodSelection(int keyGenMethod, int keySize) throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Decrement key gen method entered by one
        keyGenMethod = (keyGenMethod - 1);

        // Based on user selection, sets key gen method and generates key
        return switch (keyGenMethod) {
            case 1 -> getKeyFromPassword(UtilGetTerminalInputs.getUserInput(1), UtilGetTerminalInputs.getUserInput(2)); // User has selected to generate key from provided password
            case 2 -> LogicConverters.stringToSecretKey(UtilGetTerminalInputs.getUserInput(3)); // User has selected to provide their own key
            default -> generateKey(keySize); // if no selection made, default to random AES key generation
        };
    }
}
