package com.company;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

// Handles secret key generation functions in terminal mode
public class UtilSecretKeyHandlers {

    // Return key generated using password based inputs
    public static SecretKey passwordBasedInputs() throws InvalidKeySpecException, NoSuchAlgorithmException {

        // Define password to generate key from
        final String password = UtilGetTerminalInputs.getUserInput(1);

        // Define salt to apply to password before hashing
        final String salt = UtilGetTerminalInputs.getUserInput(2);

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
            String userInputKey = UtilGetTerminalInputs.getUserInput(3);

            // Return user input key to caller
            return UtilAES.stringToSecretKey(userInputKey);

            // if no selection made, default to random AES key generation
        } else {

            // Generate secure random key for AES
            return UtilAES.generateKey(keySize);
        }
    }

    // Method for setting keygen method
    public static SecretKey setKeyGenMethod(int keyGenMethod, int encryptionBitLengthChoice) throws InvalidKeySpecException, NoSuchAlgorithmException {

        // Define program behaviour depending upon user keygen method chosen
        // User has chosen random keygen method as default = 0
        // Define program behaviour for password based key generation = 1
        // Define program behaviour for User provided key = 2
        // Get key from key generation method selected
        if (keyGenMethod == 2) {
            return keyMethodSelection(1, encryptionBitLengthChoice);
        } else if (keyGenMethod == 3) {
            return keyMethodSelection(2, encryptionBitLengthChoice);
        } else {
            return keyMethodSelection(0, encryptionBitLengthChoice);
        }
    }

}
