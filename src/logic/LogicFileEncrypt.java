package logic;

/*
 * Project Java-AESEncryption
 * File: LogicFileEncrypt.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: A Utility class to perform AES Encryption and Decryption of files.
 */

// import java input / output, crypto and security libraries
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class LogicFileEncrypt {

    // Method for encryption of a file
    public static void encryptFile(String algorithm, SecretKey key, IvParameterSpec iv,
                                   File inputFile, File outputFile) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        // Define cipher being used
        Cipher cipher = Cipher.getInstance(algorithm);

        // Initialise cipher for encryption
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        // Define input stream to read unencrypted file data
        FileInputStream inputStream = new FileInputStream(inputFile);

        // Define output stream to write encrypted file data
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        // Define a new file buffer of 64 bit bytes for file data reading, so as not to overflow system ram
        byte[] buffer = new byte[64];

        // Define bytes read integer value to track number of bytes in file so that input and output match
        int bytesRead;

        // Read all bytes in buffer until buffer is empty,
        // encrypt each 64 bit byte and output to byte array, if the output is not empty write bytes to file output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {

            // Output byte array stores encrypted data from cipher algorithm
            byte[] output = cipher.update(buffer, 0, bytesRead);

            // If the output byte array is not empty, write contents to output stream
            if (output != null) {

                // Write contents of output byte array to output stream
                outputStream.write(output);

            }
        }

        // output bytes are end of encryption algorithm
        byte[] outputBytes = cipher.doFinal();

        // If there are any bytes left, write them to the output stream
        if (outputBytes != null) {

            // Write content out the output bytes array to the output stream
            outputStream.write(outputBytes);

        }

        // Close input stream
        inputStream.close();

        // Close output stream
        outputStream.close();

    }

    // Method for encryption of a file
    public static void decryptFile(String algorithm, SecretKey key, IvParameterSpec iv,
                                   File inputFile, File outputFile) throws IOException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        // Define cipher being used
        Cipher cipher = Cipher.getInstance(algorithm);

        // Initialise cipher for encryption
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        // Define input stream to read unencrypted file data
        FileInputStream inputStream = new FileInputStream(inputFile);

        // Define output stream to write encrypted file data
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        // Define a new file buffer of 64 bit bytes for file data reading, so as not to overflow system ram
        byte[] buffer = new byte[64];

        // Define bytes read integer value to track number of bytes in file so that input and output match
        int bytesRead;

        // Read all bytes in buffer until buffer is empty,
        // encrypt each 64 bit byte and output to byte array, if the output is not empty write bytes to file output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {

            // Output byte array stores encrypted data from cipher algorithm
            byte[] output = cipher.update(buffer, 0, bytesRead);

            // If the output byte array is not empty, write contents to output stream
            if (output != null) {

                // Write contents of output byte array to output stream
                outputStream.write(output);

            }
        }

        // output bytes are end of encryption algorithm
        byte[] outputBytes = cipher.doFinal();

        // If there are any bytes left, write them to the output stream
        if (outputBytes != null) {

            // Write content out the output bytes array to the output stream
            outputStream.write(outputBytes);

        }

        // Close input stream
        inputStream.close();

        // Close output stream
        outputStream.close();

    }
}
