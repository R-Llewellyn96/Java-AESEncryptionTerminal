package com.company;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

// Class handles all terminal outputs when program is in terminal mode
public class UtilTerminalOutputs {

    // Designed for terminal outputs only, called to run encryption process and output results to terminal
    public static void terminalOutputRunEncryption(String keyString, String encryptionAlgorithm, String message, String ivString) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        // Call Encryption function
        String[] encryptionOutputArr = UtilAES.encryptMessage(UtilAES.stringToSecretKey(keyString), encryptionAlgorithm, message, ivString);

        // Following are text outputs to terminal, this hint is included last
        String securityInformationPrompt = """
                NOTE! If you wish to send an encrypted message securely you must exchange keys in a secure manner!
                Keys must not be sent over the internet or through text messaging, 
                the following information must be followed for secure communications:
                
                AES Symmetric Key     : %s
                AES Symmetric key     : PRIVATE 
               
                Must be kept SECRET, only shared between yourself and your communication partner!
                
                The following information may be shared over the internet or sent through insecure channels, 
                as these elements cannot be used to compromise message encryption without access to the private key.
                
                Initialisation Vector : %s
                Initialisation Vector : SHAREABLE
                
                CipherText            : %s
                CipherText            : SHAREABLE
                
                NOTE: Recommended method of transmitting IV and Ciphertext:
                
                Combined IV and Ciphertext : %s 
                Combined IV and Ciphertext : SHAREABLE 
                
                For the purposes of AES Symmetric key sharing, in-person key exchange is recommended 
                or another secure method of key exchange known as Diffie-Hellman key exchange is recommended 
                if personal key exchange is not possible or you believe you're being monitored.
                """.formatted(encryptionOutputArr[0], encryptionOutputArr[1], encryptionOutputArr[2], UtilAES.addIVToCiphertext(encryptionOutputArr[1], encryptionOutputArr[2]));

        // Prompt user that Encryption is happening
        System.out.println("\nEncryption Mode Active : ");

        // Convert key to string to allow for display &
        // Print AES key being used to terminal
        System.out.println("AES Symmetric key used : " + encryptionOutputArr[0]);

        // Print IV Spec
        System.out.println("Initialisation Vector  : " + encryptionOutputArr[1]);

        // Print cipher text
        System.out.println("Encrypted Ciphertext   : " + encryptionOutputArr[2]);

        // Print decoded plaintext
        System.out.println("Decrypted Ciphertext   : " + encryptionOutputArr[3]);

        // Display security information prompt to inform user what information
        // is shareable and what isn't
        System.out.println("\n" + securityInformationPrompt);

        // Print cipher text
        System.out.println("Results:\nInput Plaintext      : " + encryptionOutputArr[3]);

        // Print encrypted plaintext
        System.out.println("Encrypted Ciphertext : " + encryptionOutputArr[2] + "\n");
    }

    // Designed for terminal outputs only, called to run decryption process and output results to terminal
    public static void terminalOutputRunDecryption(String keyString, String encryptionAlgorithm, String ciphertext, String ivString) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        // Call Decryption function
        String[] decryptionOutputArr = UtilAES.decryptMessage(UtilAES.stringToSecretKey(keyString), encryptionAlgorithm, ciphertext, ivString);

        // Following are text outputs to terminal, this hint is included last
        String securityInformationPrompt = """
                NOTE! If you wish to send an encrypted message securely you must exchange keys in a secure manner!
                Keys must not be sent over the internet or through text messaging, 
                the following information must be followed for secure communications:
                
                AES Symmetric Key     : %s
                AES Symmetric key     : PRIVATE 
               
                Must be kept SECRET, only shared between yourself and your communication partner!
                
                The following information may be shared over the internet or sent through insecure channels, 
                as these elements cannot be used to compromise message encryption without access to the private key.
                
                Initialisation Vector : %s
                Initialisation Vector : SHAREABLE
                
                CipherText            : %s
                CipherText            : SHAREABLE
                
                NOTE: Recommended method of transmitting IV and Ciphertext:
                
                Combined IV and Ciphertext : %s 
                Combined IV and Ciphertext : SHAREABLE
                
                For the purposes of AES Symmetric key sharing, in-person key exchange is recommended 
                or another secure method of key exchange known as Diffie-Hellman key exchange is recommended 
                if personal key exchange is not possible or you believe you're being monitored.
                """.formatted(decryptionOutputArr[0], decryptionOutputArr[1], decryptionOutputArr[2], UtilAES.addIVToCiphertext(decryptionOutputArr[1], decryptionOutputArr[2]));

        // Prompt user that Decryption is happening
        System.out.println("\nDecryption Mode Active : ");

        // Convert key to string to allow for display &
        // Print AES key being used to terminal
        System.out.println("AES Symmetric key used : " + decryptionOutputArr[0]);

        // Print Initialisation Vector
        System.out.println("Initialisation Vector  : " + decryptionOutputArr[1]);

        // Print cipher text
        System.out.println("Encrypted Ciphertext   : " + decryptionOutputArr[2]);

        // Print decoded plaintext
        System.out.println("Decrypted Ciphertext   : " + decryptionOutputArr[3]);

        // Display security information prompt to inform user what information
        // is shareable and what isn't
        System.out.println("\n" + securityInformationPrompt);

        // Print cipher text
        System.out.println("Results:\nEncrypted Ciphertext   : " + decryptionOutputArr[2]);

        // Print decoded plaintext
        System.out.println("Decrypted Ciphertext   : " + decryptionOutputArr[3] + "\n");

    }
}
