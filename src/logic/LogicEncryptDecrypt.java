package logic;

/*
 * Project Java-AESEncryption
 * File: LogicEncryptDecrypt.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: A Utility class to perform AES Encryption and Decryption.
 */

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LogicEncryptDecrypt {

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

    // Encryption of full message, returns string array for display on UI
    public static String[] encryptMessage(SecretKey key, String encryptionAlgorithm, String message, String ivString) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        // Generate parameter spec
        IvParameterSpec ivParamSpec = LogicIV.generateIvParamSpec(LogicHexConv.hexStringToByteArray(ivString));

        // Generate Cipher-text using encryption
        String cipherText = encrypt(encryptionAlgorithm, message, key, ivParamSpec);

        // Decrypt Cipher-text into plain text for checking of valid output
        String plainText = decrypt(encryptionAlgorithm, cipherText, key, ivParamSpec);

        // Return array of strings for output to UI
        return new String[]{LogicConverters.secretKeyToString(key), ivString, cipherText, plainText};
    }

    // Decryption of full message, returns string array for display on UI
    public static String[] decryptMessage(SecretKey key, String encryptionAlgorithm, String cipherText, String ivString) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        // Generate parameter spec
        // Decrypt Cipher-text into plain text
        String plainText = decrypt(encryptionAlgorithm, cipherText, key, (LogicIV.generateIvParamSpec(LogicHexConv.hexStringToByteArray(ivString))));

        // Return array of strings for output to UI
        return new String[]{LogicConverters.secretKeyToString(key), ivString, cipherText, plainText};
    }
}
