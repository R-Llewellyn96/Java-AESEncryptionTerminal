package com.company;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

// Handles secret key generation functions in terminal mode
public class UtilSecretKeyHandlers {

    // Return key generated using password based inputs
    public static SecretKey passwordBasedInputs() throws InvalidKeySpecException, NoSuchAlgorithmException {

        // Define password to generate key from
        // Define salt to apply to password before hashing
        // Generate secure key using password and return key to method caller
        return UtilAES.getKeyFromPassword(UtilGetTerminalInputs.getUserInput(1), UtilGetTerminalInputs.getUserInput(2));
    }

    // Return key based on key generation method selected by user
    public static SecretKey keyMethodSelection(int keyGenMethod, int keySize) throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Based on user selection, sets key gen method and generates key
        return switch (keyGenMethod) {
            case 1 -> passwordBasedInputs(); // User has selected to generate key from provided password
            case 2 -> UtilAES.stringToSecretKey(UtilGetTerminalInputs.getUserInput(3)); // User has selected to provide their own key
            default -> UtilAES.generateKey(keySize); // if no selection made, default to random AES key generation
        };
    }

    // Method for setting keygen method
    public static SecretKey setKeyGenMethod(int keyGenMethod, int encryptionBitLengthChoice) throws InvalidKeySpecException, NoSuchAlgorithmException {

        // Define program behaviour depending upon user keygen method chosen
        // User has chosen random keygen method as default = 0
        // Define program behaviour for password based key generation = 1
        // Define program behaviour for User provided key = 2
        // Get key from key generation method selected
        // Enhanced switch statement
        return switch (keyGenMethod) {
            case 2 -> keyMethodSelection(1, encryptionBitLengthChoice);
            case 3 -> keyMethodSelection(2, encryptionBitLengthChoice);
            default -> keyMethodSelection(0, encryptionBitLengthChoice);
        };
    }
}
