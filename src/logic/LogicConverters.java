package logic;

/*
 * Project Java-AESEncryption
 * File: LogicConverters.java
 * @author Ryan Llewellyn
 * Date: 09/01/2021
 * Purpose: Handles value conversions.
 */

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class LogicConverters {

    // Take string and convert to secret key object
    public static SecretKey stringToSecretKey(String keyString) {

        // Decode the base 64 encoded key string and
        // Return secret key string as a secret key object to method caller
        return new SecretKeySpec((Base64.getDecoder().decode(keyString)), 0, (Base64.getDecoder().decode(keyString)).length, "AES");
    }

    // Take secret key object and convert to string
    public static String secretKeyToString(SecretKey key) {

        // Return secret key as a string to method caller
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
