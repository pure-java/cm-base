package com.github.pure.cm.common.core.util.encry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author bairitan
 * @since 2019/11/15
 */
@Slf4j
public class RsaUtil {

    /**
     * 指定加密算法为RSA
     */
    private static final String ALGORITHM = "RSA";
    private static final String CHARSET = "UTF-8";

    /**
     * 获取rsa KeyPair
     *
     * @param length 加密长度
     * @return KeyPair
     */
    public static KeyPair getKeyPair(int length) throws Exception {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            // 秘钥长度
            keyPairGenerator.initialize(length);
            // 初始化秘钥对
            return keyPairGenerator.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            log.error("加密算法错误");
            throw e;
        }
    }

    /**
     * 获取rsa非对称加密，并经过 base64编码的秘钥对
     *
     * @param length 加密长度
     * @return rsa非对称加密秘钥，并经过 base64编码的秘钥对
     */
    public static RsaKey getKey(int length) throws Exception {
        // 初始化秘钥对
        KeyPair keyPair = getKeyPair(length);
        // 私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        // 公钥
        RSAPublicKey aPublic = (RSAPublicKey) keyPair.getPublic();
        return new RsaKey().setPrivateKey(aPrivate).setPublicKey(aPublic);
    }

    /**
     * 获取 rsa 秘钥对
     *
     * @param publicFile  公钥文件
     * @param privateFile 私钥文件
     * @return 秘钥对
     */
    public static RsaKey getKey(File publicFile, File privateFile) throws Exception {
        PublicKey publicKey = getPublicKey(publicFile);
        PrivateKey privateKey = getPrivateKey(privateFile);
        RsaKey key = new RsaKey();
        key.setPrivateKey(privateKey);
        key.setPublicKey(publicKey);
        return key;
    }

    /**
     * 将 rsa 秘钥保存到指定目录
     *
     * @param privateFile 秘钥保存文件
     * @param publicFile  公钥保存文件
     * @param rsaKey      秘钥对
     */
    public static boolean saveKeyFile(String privateFile, String publicFile, RsaKey rsaKey) throws Exception {
        try (FileOutputStream privateOutputStream = new FileOutputStream(new File(privateFile));
             FileOutputStream publicOutputStream = new FileOutputStream(new File(publicFile));) {
            privateOutputStream.write(rsaKey.getPrivateKey().getEncoded());
            publicOutputStream.write(rsaKey.getPublicKey().getEncoded());
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    /**
     * 获取公钥
     *
     * @param file 文件路径
     */
    public static PublicKey getPublicKey(File file) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(file);
             DataInputStream dis = new DataInputStream(inputStream);) {
            byte[] b = new byte[inputStream.available()];
            dis.readFully(b);
            return getPublicKey(b);
        }

    }

    /**
     * 获取公钥
     *
     * @param keyBytes key
     */
    public static PublicKey getPublicKey(byte[] keyBytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);


    }

    /**
     * 获取私钥
     *
     * @param file 文件
     */
    public static PrivateKey getPrivateKey(File file) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(file);
             DataInputStream dis = new DataInputStream(inputStream);) {
            byte[] b = new byte[inputStream.available()];
            dis.readFully(b);
            return getPrivateKey(b);
        }
    }

    /**
     * 获取私钥
     *
     * @param keyBytes key
     */
    public static PrivateKey getPrivateKey(byte[] keyBytes) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance(ALGORITHM).generatePrivate(spec);

    }


    /**
     * 使用秘钥进行加密
     *
     * @param content 加密内容
     * @param key     秘钥
     */
    public static String encrypt(String content, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(content.getBytes(CHARSET));
        return Base64.encodeBase64String(bytes);
    }


    /**
     * 进行解密，公钥加密用私钥解密；私钥加密使用公钥解密；
     *
     * @param content 需要解密内容
     * @param key     秘钥
     */
    public static String decryptBase64(String content, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 执行解密操作

        byte[] b = cipher.doFinal(Base64.decodeBase64(content.getBytes(CHARSET)));
        return new String(b);

    }

    public static String encodeBase64(Key key) {
        return Base64.encodeBase64String(key.getEncoded());
    }

    public static byte[] decodeBase64(String key) {
        return Base64.decodeBase64(key);
    }

    @Getter
    @Setter
    @ToString
    @Accessors(chain = true)
    public static class RsaKey {

        private PrivateKey privateKey;
        private PublicKey publicKey;

    }
}
