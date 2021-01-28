package com.wangyc.hehe.utils.RSA;




import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;

public class RSAPrivateUtil {

        /**
         * 私钥加密
         * @param privateKey
         * @param plainText
         * @return
         */
        public static String encrypt(String privateKey, String plainText) {
                RSAPrivateKey secretKey = KeyStore.getPrivateKey(privateKey);
                try {
                        Cipher cipher = Cipher.getInstance(KeyStore.ALGORITHM_RSA);
                        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                        byte [] cipherData = cipher.doFinal(plainText.getBytes(KeyStore.CHARSET));
                        return Base64.encodeBase64String(cipherData);
                } catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
                        throw new RuntimeException("RSA私钥加密出错！", e1);
                } catch (InvalidKeyException e2) {
                        throw new RuntimeException("RSA私钥加密出错！", e2);
                } catch (IllegalBlockSizeException e3) {
                        throw new RuntimeException("RSA私钥加密出错！", e3);
                } catch (BadPaddingException e4) {
                        throw new RuntimeException("RSA私钥加密出错！", e4);
                } catch (UnsupportedEncodingException e5) {
                        throw new RuntimeException("RSA私钥加密出错！", e5);
                }
        }
        
        /**
         * 私钥解密
         * @param privateKey
         * @param cipherText
         * @return
         */
        public static String decrypt(String privateKey, String cipherText) {
                RSAPrivateKey secretKey = KeyStore.getPrivateKey(privateKey);
                try {
                        byte[] byteData = Base64.decodeBase64(cipherText);
                        Cipher cipher = Cipher.getInstance(KeyStore.ALGORITHM_RSA);
                        cipher.init(Cipher.DECRYPT_MODE, secretKey);
                        byte [] cipherData = cipher.doFinal(byteData);
                        return new String(cipherData, KeyStore.CHARSET);
                } catch (NoSuchAlgorithmException e1) {
                        throw new RuntimeException("RSA私钥解密出错！", e1);
                } catch (NoSuchPaddingException e2) {
                        throw new RuntimeException("RSA私钥解密出错！", e2);
                } catch (InvalidKeyException e3) {
                        throw new RuntimeException("RSA私钥解密出错！", e3);
                } catch (UnsupportedEncodingException e4) {
                        throw new RuntimeException("RSA私钥解密出错！", e4);
                } catch (IllegalBlockSizeException e5) {
                        throw new RuntimeException("RSA私钥解密出错！", e5);
                } catch (BadPaddingException e6) {
                        throw new RuntimeException("RSA私钥解密出错！", e6);
                }
        }

}