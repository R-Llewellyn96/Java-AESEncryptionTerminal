package com.company;

/*
 * Project Java-AESEncryption
 * File: UtilGetTerminalInputs.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: Handles terminal inputs to program.
 */

import logic.LogicConverters;
import logic.LogicIV;

import javax.crypto.SecretKey;

// import java scanner library
import java.util.Scanner;

// Class handles all terminal inputs
public class UtilGetTerminalInputs {

    // Define input scanner as global variable
    public static Scanner scanner = new Scanner(System.in);

    // Prompt user to select the length of AES key they want
    public static int getAESKeyLength() {

        // Define choice of AES encryption bit length
        int encryptionBitLengthChoice = 0;

        // While loop to repeat prompt to user for input to key length choice
        while (encryptionBitLengthChoice == 0) {

            // Prompt user to select key length
            String userEncryptionBitLengthChoice = UtilGetTerminalInputs.getUserInput(9);

            // Attempt string to integer conversion on user input, if it fails repeat the loop and try again
            try {

                // Read user input and attempt integer conversion
                encryptionBitLengthChoice = Integer.parseInt(userEncryptionBitLengthChoice.trim());

                // Catch any errors and handle failure to convert input to integer
            } catch (Exception e) {

                // Set program mode selection to zero so that while loop runs again
                encryptionBitLengthChoice = 0;

                // Prompt user of input error and retry
                System.out.println("ERROR, Could not convert input to a number, please enter a number!");
            }
        }
        return encryptionBitLengthChoice;
    }

    // Prompt user to choose program mode, encryption / decryption
    public static int getProgramModeChoice() {

        // Define program mode selection value, 1 = encrypt, 2 = decrypt
        int programModeSelection = 0;

        // While loop for reminding user to select valid encryption or decryption mode
        while (programModeSelection != 1 && programModeSelection != 2 && programModeSelection != 3) {

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
        return programModeSelection;
    }

    // Prompt user to select key generation method for encryption
    public static int getKeyGenMethodChoice() {

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

        // Return users choice of key generation method to caller
        return keyGenMethod;
    }

    // Prompt user to input their decryption key
    public static String getKeyInput(){

        String userDecryptionKeyString = "";
        SecretKey userDecryptionKey = null;

        // Wrapped while loop to ensure key input
        while (userDecryptionKeyString.trim().isEmpty()) {

            // Prompt user for decryption key
            userDecryptionKeyString = getUserInput(7);

            // attempt string conversion to key
            try {

                // Define secret key and convert input string to key
                userDecryptionKey = LogicConverters.stringToSecretKey(userDecryptionKeyString.trim());

                // If it fails prompt user, clear input and try again
            } catch (Exception e) {

                // Prompt user of conversion error
                System.out.println("ERROR!: Failure converting input to key, please check your inputs and try again!");

                // Set input string to empty to allow for retry
                userDecryptionKeyString = "";
            }
        }

        // Convert secret key back into string
        return LogicConverters.secretKeyToString(userDecryptionKey);
    }

    // Get combined IV and Ciphertext
    public static int getCombinedIVAndCiphertextInput(){

        // Choice value for using combined or separate iv and ciphertext
        int combinedIvAndCipherChoice = 0;

        // Prompt user to choose whether they're using a separate or combined IV and Ciphertext
        while (combinedIvAndCipherChoice != 1 && combinedIvAndCipherChoice !=2) {

            // Prompt user for IV and Ciphertext mode, separate = 1, combined = 2
            String combinedIvAndCipherChoiceString = getUserInput(12);

            // Attempt string to integer conversion on user input, if it fails repeat the loop and try again
            try {

                // Read user input and attempt integer conversion
                combinedIvAndCipherChoice = Integer.parseInt(combinedIvAndCipherChoiceString.trim());

                // Catch any errors and handle failure to convert input to integer
            } catch (Exception e) {

                // Set mode selection to zero so that while loop runs again
                combinedIvAndCipherChoice = 0;

                // Prompt user of input error and retry
                System.out.println("ERROR, Could not convert input to a number, please enter a number!");
            }
        }

        // return users choice of whether they have a combined or separate IV and Ciphertext to input
        return combinedIvAndCipherChoice;

    }

    // Get user input of initialisation vector
    public static String getIVInput() {

        // Define initialisation vector
        String ivString = "";

        // Wrapped while loop to ensure key input
        while (ivString.trim().isEmpty() || ivString.length() != 32) {

            try {

                // Prompt user for initialisation vector
                ivString = getUserInput(8);

            } catch (Exception e) {

                // Prompt user of conversion error
                System.out.println("ERROR!: Failure converting input to initialisation vector, please check your inputs and try again!");

                // Set input string to empty to allow for retry
                ivString = "";
            }
        }

        // return ivString to caller
        return ivString;
    }

    // Perform encryption and ask user if they wish to encrypt another message
    public static void getRepeatingEncryption(String encryptionAlgorithm, String keyString) {

        // Define user selection for repeating input loop
        String repeatEncryptionWithNewCipherText = "y";

        // While loop allows for repetition while the user has confirmed y to repeat with new message
        while (repeatEncryptionWithNewCipherText.equals("y") || repeatEncryptionWithNewCipherText.equals("Y")) {

            // Get user input message after validation
            String message = getUserInput(0);

            // Generate Initialisation Vector String
            String ivString = LogicIV.generateIVString();

            try {

                // Begin Message encryption using generated key, encryption algorithm, message and generated iv
                UtilTerminalOutputs.terminalOutputRunEncryption(keyString, encryptionAlgorithm, message, ivString);

            } catch (Exception e) {

                // Prompt user that message encryption has failed
                System.out.println("ERROR!: Failure to Encrypt message, please check Encryption key, ciphertext, initialisation vector and try again!");
            }

            // Prompt user whether they wish to Encrypt another message
            repeatEncryptionWithNewCipherText = getUserInput(11);

            // if user has chosen not to repeat, then close input scanner and exit main method
            if (repeatEncryptionWithNewCipherText.equals("n") || repeatEncryptionWithNewCipherText.equals("N")) {

                // Close scanner
                scanner.close();
            }
        }
    }

    // Perform decryption and ask user if they wish to encrypt another message
    public static void getRepeatingDecryption(String encryptionAlgorithm, String userDecryptionKeyStringConv, int combinedIvAndCipherChoice) {

        // Define user selection for repeating input loop
        String repeatDecryptionWithNewCipherText = "y";

        while (repeatDecryptionWithNewCipherText.equals("y") || repeatDecryptionWithNewCipherText.equals("Y")) {

            // Define IV and Ciphertext
            String ivString;
            String ciphertext;

            // Check whether user has selected a separate or combined IV and Ciphertext
            if (combinedIvAndCipherChoice == 1) {

                // Define initialisation vector
                ivString = UtilGetTerminalInputs.getIVInput();

                // Prompt user for cipher text to decrypt
                ciphertext = getUserInput(6);

                // If user has selected to use combined iv and ciphertext
            } else {

                // Prompt user for cipher text to decrypt which includes IV
                String[] userIvAndCiphertext = AESEncrypt.handleIVAndCiphertextSelection();

                ivString = userIvAndCiphertext[0];

                ciphertext = userIvAndCiphertext[1];
            }

            // Attempt decryption, if failure prompt user
            try {

                // Call methods for Decryption
                UtilTerminalOutputs.terminalOutputRunDecryption(userDecryptionKeyStringConv, encryptionAlgorithm, ciphertext.trim(), ivString);

            } catch (Exception e) {

                // Prompt user that message decryption has failed
                System.out.println("ERROR!: Failure to Decrypt message, please check Decryption key, ciphertext, initialisation vector and try again!");
            }

            // Prompt user whether they wish to Decrypt another message
            repeatDecryptionWithNewCipherText = getUserInput(10);

            // if user has chosen not to repeat, then close input scanner and exit main method
            if (repeatDecryptionWithNewCipherText.equals("n") || repeatDecryptionWithNewCipherText.equals("N")) {

                // Close scanner
                scanner.close();
            }
        }
    }

    // Get the month user selected
    public static int getUserMonth() {

        // Define ane initialise month number
        int monthNo = 0;

        // Prompt user to choose whether they're using a separate or combined IV and Ciphertext
        while (monthNo < 1 || monthNo > 13) {

            // Prompt user for IV and Ciphertext mode, separate = 1, combined = 2
            String monthNoString = getUserInput(14);

            // Attempt string to integer conversion on user input, if it fails repeat the loop and try again
            try {

                // Read user input and attempt integer conversion
                monthNo = Integer.parseInt(monthNoString.trim());

                // Catch any errors and handle failure to convert input to integer
            } catch (Exception e) {

                // Set mode selection to zero so that while loop runs again
                monthNo = 0;

                // Prompt user of input error and retry
                System.out.println("ERROR, Could not convert input to a number, please enter a number!");
            }
        }

        // return the number of users selected month to caller
        return monthNo;
    }

    // Get user inputs for a variety of things
    // Get message for encryption from user input prompt and return to caller
    public static String getUserInput(int promptSelection) {

        // Define user input message
        String userInputPromptMessage;

        // Input prompt using enhanced switch
        userInputPromptMessage = switch (promptSelection) {
            case 0 -> "Enter the message you wish to encrypt, NOTE: Must not be blank!: ";
            case 1 -> "Enter the password you wish to use for key generation, " +
                    "NOTE: Must not be blank!: ";
            case 2 -> "Enter the salt you wish to use for key generation, " +
                    "NOTE: Must not be blank!: ";
            case 3 -> "Enter the pre-generated key you wish to use for encryption, " +
                    "NOTE: Must not be blank!: ";
            case 4 -> """
                    Select program mode,
                    NOTE: Must not be blank!
                    [1] = Encrypt, [2] = Decrypt, [3] = Generate Monthly Code Book:\s""";
            case 5 -> """
                    Select Encryption mode,
                    NOTE: Must not be blank!
                    [1] = Random, [2] = Password + Salt, [3] = User Provided Key:\s""";
            case 6 -> "Enter Ciphertext to Decrypt:";
            case 7 -> "Enter Key for Decryption:";
            case 8 -> "Enter Initialisation vector:";
            case 9 -> """
                    Select AES Encryption Key length,
                    NOTE: Must not be blank!
                    [1] = 128 bit, [2] = 256 bit:\s""";
            case 10 -> "Do you wish to perform decryption on another string using the same key?\n" +
                    "Yes = [y/Y], No = [n/N]: ";
            case 11 -> "Do you wish to perform encryption on another string using the same key?\n" +
                    "Yes = [y/Y], No = [n/N]: ";
            case 12 -> """
                    Select whether you have a Separate IV and Ciphertext to input, 
                    Or whether you have a combined IV and Ciphertext to input
                    NOTE: Must not be blank!
                    [1] = Separate IV and Ciphertext, [2] = Combined IV and Ciphertext:\s""";
            case 13 -> "Enter combined Initialisation vector and Ciphertext: ";
            case 14 -> """
                    Enter number of month you wish to generate keys for:, 
                    E.G. [1] = January, [2] = February, [3] = March, etc..
                    NOTE: To select February leap year, input [13] = February Leap Year
                    Enter Month Number:\s""";
            default -> throw new IllegalStateException("Unexpected value: " + promptSelection);
        };

        // Define user input string
        String userInput = "";

        // Remove trailing whitespace from user input and check string is not empty
        while (userInput.trim().isEmpty()){

            // Prompt user to enter message for encryption
            System.out.println(userInputPromptMessage);

            // Read user input
            userInput = scanner.nextLine();

            // Check whether user provided key is more than 2 characters
            if (promptSelection == 3) {

                // Check that length of input key is greater than 2 characters
                if ((userInput.trim()).length() != 44) {
                    userInput = "";

                    System.out.println("WARNING, Key Length must be exactly 44 characters!");
                }
            } else if (promptSelection == 7) {

                // Check that length of input key is greater than 2 characters
                if (((userInput.trim()).length() != 44) && ((userInput.trim()).length() != 32)) {
                    userInput = "";

                    System.out.println("WARNING, Key Length must be exactly 32 or 44 characters!");
                }

            } else if (promptSelection == 8) {

                // Check that length of input key is greater than 2 characters
                if (((userInput.trim()).length() != 32)) {
                    userInput = "";

                    System.out.println("WARNING, Key Length must be exactly 32 characters!");
                }

            } else if (promptSelection == 10 || promptSelection == 11) {

                // Check that input is y or n
                if (!userInput.trim().equals("y") && !userInput.trim().equals("Y") && !userInput.trim().equals("n") && !userInput.trim().equals("N")) {

                    userInput = "";

                    System.out.println("WARNING, You must enter [y], [Y], [n] or [N] to make a valid selection!");
                }
            } else if (promptSelection == 13) {

                // Check that length of input key is greater than 2 characters
                if (((userInput.trim()).length() <= 32)) {
                    userInput = "";

                    System.out.println("WARNING, Combined IV and Ciphertext must be greater than 32 characters!");
                }
            }
        }

        // Return user input as message string to caller
        return userInput.trim();
    }

}
