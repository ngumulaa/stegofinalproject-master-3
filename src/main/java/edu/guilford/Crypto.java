package edu.guilford;

import java.security.Key;
import java.util.Base64;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.Cipher;

//This class is used to encrypt the message the user entered
//that will then be transported into the image the user chose

/**
 * The Crypto class provides methods for encrypting and decrypting a message using the AES encryption algorithm
 * It contains constants for the algorithm, transformation, and secret key
 * The encrypt method encrypts the given input string 
 * It generates a key using the generateKey method, initializes a cipher with the encryption mode and generated key and encrypts the input string
 * The decrypt method decrypts the given input string using AES decryption 
 * It generates a key using the generateKey method, initializes a cipher with the decryption mode and generated key and decrypts the input string
 * The generateKey method generates a key using the secret key string and the AES algorithm.
 * It returns the generated key as a SecretKeySpec object.
 */

public class Crypto {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String Key = "MySecretKey12345";

    public static String encrypt(String input) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] inputBytes = input.getBytes("UTF-8");
        byte[] encryptedBytes = cipher.doFinal(inputBytes);

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String input) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedBytes = Base64.getDecoder().decode(input);
        byte[] decryptBytes = cipher.doFinal(encryptedBytes);
        
        return new String(decryptBytes, "UTF-8");
    }

    private static Key generateKey() throws Exception {
        return new SecretKeySpec(Key.getBytes("UTF-8"), ALGORITHM);
        // byte[] keyBytes = Key.getBytes("UTF-8");
        // MessageDigest sha = MessageDigest.getInstance("SHA-1");
        // keyBytes = sha.digest(keyBytes);
        // keyBytes = java.util.Arrays.copyOf(keyBytes, 16);
        // return new javax.crypto.spec.SecretKeySpec(keyBytes, ALGORITHM);
    }
}
