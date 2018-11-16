package com.example.administrator.internetpassport;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class KeyStretcher {

    final static int outputKeyLength = 256;
    final static String validation_text = "InternetPassport";

    /**
     * derive encryption key from user-input password and salt.
     * reference https://android-developers.googleblog.com/2013/02/using-cryptography-to-store-credentials.html
     * @param password
     * @return `outputKeyLength`-bit encryption key
     */
    public static byte[] deriveKey(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        final int iterations = 1000;

        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, outputKeyLength);
        SecretKey secretKey = kf.generateSecret(keySpec);
        return secretKey.getEncoded();
    }

    public static byte[] createKey(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        final int salt_length = 64;

        byte[] salt = new byte[salt_length];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        byte[] key = deriveKey(password, salt);
        return key;
    }

    public static byte[] encrypt(SecretKey key, byte[] data)
            throws GeneralSecurityException {
        Cipher c = Cipher.getInstance("AES/CBC/PKS");
        c.init(Cipher.ENCRYPT_MODE, key);
        return c.doFinal(data);
    }
}
