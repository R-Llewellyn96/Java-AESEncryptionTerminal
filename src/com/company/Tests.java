package com.company;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Tests {

    public static String GlobKeyString = "";
    public static String GlobIvString = "";
    public static String GlobCipher = "";


    // Method Caller
    public static void testEncrypt(SecretKey key, String encryptionAlgorithm, String message) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        // Generate Initialisation Vector
        byte[] iv = UtilAES.generateIV();

        // Generate parameter spec
        IvParameterSpec ivParamSpec = UtilAES.generateIvParamSpec(iv);

        // Generate Cipher-text using encryption
        String cipherText = UtilAES.encrypt(encryptionAlgorithm, message, key, ivParamSpec);

        // test iv conversion
        ivParamSpec = null;

        String ivString = UtilsConv.toHex(iv);

        byte[] iv2 = UtilsConv.hexStringToByteArray(ivString);

        IvParameterSpec ivParamSpec2 = UtilAES.generateIvParamSpec(iv2);
        // end iv conversion test

        //start key conversion test
        String keyString = UtilAES.secretKeyToString(key);

        SecretKey key2 = UtilAES.stringToSecretKey(keyString);
        // end key conversion test

        // Decrypt Cipher-text into plain text
        String plainText = UtilAES.decrypt(encryptionAlgorithm, cipherText, key2, ivParamSpec2);

        // set to global vars for testing
        GlobKeyString = keyString;
        GlobIvString = ivString;
        GlobCipher = cipherText;

        // Prompt user that Encryption is happening
        System.out.println("\nEncryption Mode Active : ");

        // Convert key to string to allow for display &
        // Print AES key being used to terminal
        System.out.println("AES Symmetric key used : " + UtilAES.secretKeyToString(key2));

        // Print IV Spec
        System.out.println("Initialisation Vector  : " + UtilsConv.toHex(iv2));

        // Print cipher text
        System.out.println("Encrypted Ciphertext   : " + cipherText);

        // Print input message
        System.out.println("Input message          : " + message);

        // Print decoded plaintext
        System.out.println("Decrypted Ciphertext   : " + plainText);
    }

    // Method Caller
    public static void testDecrypt(SecretKey key, String encryptionAlgorithm, String cipherText, String ivString) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        // Generate parameter spec
        IvParameterSpec ivParamSpec = UtilAES.generateIvParamSpec(UtilsConv.hexStringToByteArray(ivString));

        // Decrypt Cipher-text into plain text
        String plainText = UtilAES.decrypt(encryptionAlgorithm, cipherText, key, ivParamSpec);

        // Prompt user that Decryption is happening
        System.out.println("\nDecryption Mode Active : ");

        // Convert key to string to allow for display &
        // Print AES key being used to terminal
        System.out.println("AES Symmetric key used : " + UtilAES.secretKeyToString(key));

        // Print Initialisation Vector
        System.out.println("Initialisation Vector  : " + ivString);

        // Print cipher text
        System.out.println("Encrypted Ciphertext   : " + cipherText);

        // Print decoded plaintext
        System.out.println("Decrypted Ciphertext   : " + plainText);

    }



    // Main method, used for calling methods
    public static void main(String[] args) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        // Encryption Algorithm
        String encryptionAlgorithm = "AES/CBC/PKCS5Padding";

        // Key
        SecretKey key = UtilAES.stringToSecretKey("IJEAVZ7eOIadyjJSeWuTvD2KSlYeaulVeYFO7BQnmYY=");

        // Passed Initialisation Vector
        String iv = "6f1ef4de34ffb27cc90ee100f684a437";

        // Generated Ciphertext
        String ciphertext = "HU/8TD2aNiN0WiFBidmNTjsR+Xa6EI7eYtGaR1l/ZeA=";

        // Setup test
        //testEncrypt(key, encryptionAlgorithm, "Im a test message");

        testDecrypt(UtilAES.stringToSecretKey("IJEAVZ7eOIadyjJSeWuTvD2KSlYeaulVeYFO7BQnmYY="), encryptionAlgorithm, "R47xSfk0JVLOjbnRX50LQC99So86RNVc6/bQI84xphU=", "895462c0cb4614f7b8cad77a68f7abfb");

        /*
        AES Symmetric key used : IJEAVZ7eOIadyjJSeWuTvD2KSlYeaulVeYFO7BQnmYY=
        Initialisation Vector  : 895462c0cb4614f7b8cad77a68f7abfb
        Encrypted Ciphertext   : R47xSfk0JVLOjbnRX50LQC99So86RNVc6/bQI84xphU=
        Input message          : Im a test message
        Decrypted Ciphertext   : Im a test message
         */


    }
}
