package com.wangyc.hehe.utils.RSA;



import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyStore {
        
        public static final String CHARSET  = "UTF-8";
        public static final String ALGORITHM_RSA = "RSA";
        private static final int SIZE = 1024*8;


        /**
         * 密钥编码为String
         * @param key
         * @return
         */
        public static String encodeBase64(Key key) {
                byte[] keyByte = key.getEncoded();
                return Base64.encodeBase64String(keyByte);
        }
        /**
         * 生成密钥对
         * @return
         */
        public static KeyPair generateRSAKeyPair() {
                KeyPairGenerator keyPairGen;
                try {
                        keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
                        keyPairGen.initialize(SIZE);
                        KeyPair keyPair = keyPairGen.generateKeyPair();
                        return keyPair;
                } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException("生成RSA密钥对失败!", e);
                }
        }

        /**
         * 根据公钥串获取RSA公钥
         * @param publicKeyStr
         * @return
         */
        public static RSAPublicKey getPublicKey(String publicKeyStr) {
                try {
                        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
                        byte[] keyBytes = Base64.decodeBase64(publicKeyStr);
                        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
                        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
                } catch (NoSuchAlgorithmException e1) {
                        throw new RuntimeException("公钥无效或不合法！", e1);
                } catch (InvalidKeySpecException e2) {
                        throw new RuntimeException("公钥无效或不合法！", e2);
                }
        }
        
        /**
         * 根据私钥串获取RSA私钥
         * @param privateKeyStr
         * @return
         */
        public static RSAPrivateKey getPrivateKey(String privateKeyStr) {
                try {
                        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
                        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
                        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
                } catch (NoSuchAlgorithmException e1) {
                        throw new RuntimeException("私钥无效或不合法！", e1);
                } catch (InvalidKeySpecException e2) {
                        throw new RuntimeException("私钥无效或不合法！", e2);
                }
        }

}