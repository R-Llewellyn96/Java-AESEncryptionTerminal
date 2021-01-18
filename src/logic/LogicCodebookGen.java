package logic;

/*
 * Project Java-AESEncryption
 * File: LogicCodebookGen.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: Handles File creation and generation of monthly Codebook.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

// Class handles outputs to files
public class LogicCodebookGen {

    // Create a file with the specified filename
    public static boolean createFile(String filename) {

        // try to generate file and write values, if it fails prompt user
        try {

            // Create new file in memory
            File keyStoreFile = new File(filename);

            // Attempt to create new file in storage
            // return to caller that file could be created
            // if its not possible prompt user
            //return to caller that file already exists
            return keyStoreFile.createNewFile();


            // Catch any error from creating file
        } catch (IOException e) {

            // Set flag to overwrite just in case
            return false;
        }
    }

    // Write generated keys to a file
    public static boolean generateCodeBook(int monthNo, int keySize) throws NoSuchAlgorithmException {

        // After user has selected a month, get the number of days in that month
        int noKeysToGen = LogicMonthSelection.daysInMonth(monthNo);

        // Generate array of keys for selected month
        String[] keyArr = LogicKeyGen.generateKeyArray(noKeysToGen, keySize);

        // Get name of chosen month
        String chosenMonth = LogicMonthSelection.nameOfMonth(monthNo);

        // Define name of file to store encryption keys
        String filename = chosenMonth + "_Keys.txt";

        // try to generate file and write values, if it fails prompt user
        try {

            // Attempt to create file using selected month as filename
            boolean createdOrOverwrite = createFile(filename);

            // open text file with month name and keys, append to its current content
            FileWriter fileWriter = new FileWriter(filename, createdOrOverwrite);

            // Define buffer for writing text to a file
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Loop writing each key to file
            for (int i = 0; i < keyArr.length; i++) {

                // Write name of month, day and corresponding key to file
                bufferedWriter.write("Key for " + chosenMonth + " " + (i + 1));
                bufferedWriter.newLine();
                bufferedWriter.write("Key: " + keyArr[i]);
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }

            // Close buffer once writing is finished and then close file as no longer needed
            bufferedWriter.close();
            fileWriter.close();

            // Return true to caller to indicate successful file writing
            return true;

            // Catch any error if creating and writing to file fails
        } catch (IOException e) {

            // Return false to caller to indicate failed file writing
            return false;
        }
    }
}
