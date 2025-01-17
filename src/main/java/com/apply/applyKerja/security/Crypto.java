package com.apply.applyKerja.security;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESLightEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class Crypto {

    private static final String defaultKey = "2d38e01c52c0d9c475dac174eff7cea6464778ec35f3d78eba5c1421d684a3e6";
    public static String performEncrypt(String keyText, String plainText) {
        try{
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] ptBytes = plainText.getBytes();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(true, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(ptBytes.length)];
            int oLen = cipher.processBytes(ptBytes, 0, ptBytes.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(Hex.encode(rv));
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performEncrypt(String cryptoText) {
        return performEncrypt(defaultKey, cryptoText);
    }

    public static String performDecrypt(String keyText, String cryptoText) {
        try {
            byte[] key = Hex.decode(keyText.getBytes());
            byte[] cipherText = Hex.decode(cryptoText.getBytes());
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESLightEngine()));
            cipher.init(false, new KeyParameter(key));
            byte[] rv = new byte[cipher.getOutputSize(cipherText.length)];
            int oLen = cipher.processBytes(cipherText, 0, cipherText.length, rv, 0);
            cipher.doFinal(rv, oLen);
            return new String(rv).trim();
        } catch(Exception e) {
            return "Error";
        }
    }

    public static String performDecrypt(String cryptoText) {
        return performDecrypt(defaultKey, cryptoText);
    }

    public static void main(String[] args) {

        Long timeMilis = System.currentTimeMillis();
        System.out.println(timeMilis);
        String strToEncrypt = "eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiJlNDFlNDEyZC03N2Y2LTQyYjQtYWQ1Mi0zZTQ5NmUxZGEwN2EiLCJzdWIiOiJhbmdnaUB0ZXN0LmNvbSIsImV4cCI6MTczMzM4NzM1NSwiaWF0IjoxNzMzMzgzNzU1fQ.iwxBjSITANYuGv_E3jfoMlM01R5-g7H0RHl2_eQbeyah8b2nghOxWQqHIBkSN578FFcCC8dnu8DzW3gNhNTzXw";//put text to encrypt in here
        String encryptionResult = new Crypto().performEncrypt(strToEncrypt);
        System.out.println("Encryption Result : "+encryptionResult);

        String strToDecrypt = "f9b2c48efd1750e0c7be5bf03a6f52de";//put text to decrypt in here
        String decriptionResult = new Crypto().performDecrypt(strToDecrypt);
        System.out.println("Decryption Result : "+decriptionResult);
    }
}