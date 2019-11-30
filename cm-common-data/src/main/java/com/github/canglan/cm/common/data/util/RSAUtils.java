package com.github.canglan.cm.common.data.util;

import java.io.ByteArrayOutputStream;
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
import java.util.UUID;
import javax.crypto.Cipher;
import org.springframework.util.Base64Utils;

/**
 * RSA加密、解密工具类
 *
 * @author Administrator
 * @create 2018-09-06 20:18
 **/

public class RSAUtils {

  private static String RSA = "RSA";
  /**
   * RSA最大加密明文大小
   */
  private static final int MAX_ENCRYPT_BLOCK = 117;

  /**
   * 随机生成RSA密钥对(默认密钥长度为1024)
   */
  public static KeyPair generateRSAKeyPair() {
    return generateRSAKeyPair(1024);
  }

  /**
   * 随机生成RSA密钥对
   *
   * @param keyLength 密钥长度，范围：512～2048<br> 一般1024
   */
  public static KeyPair generateRSAKeyPair(int keyLength) {
    try {
      KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
      kpg.initialize(keyLength);

      return kpg.genKeyPair();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 将Base64编码后的公钥字符串转换成PublicKey对象
   *
   * @param pubStr Base64编码后的公钥字符串
   * @return 结果：PublicKey
   */
  public static PublicKey string2PublicKey(String pubStr) throws Exception {
    byte[] keyBytes = Base64Utils.decodeFromString(pubStr);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(RSA);
    PublicKey publicKey = keyFactory.generatePublic(keySpec);
    return publicKey;
  }

  /**
   * 将Base64编码后的私钥字符串转换成PrivateKey对象
   *
   * @param priStr Base64编码后的私钥字符串
   * @return 结果：PrivateKey
   */
  public static PrivateKey string2PrivateKey(String priStr) throws Exception {
    byte[] keyBytes = Base64Utils.decodeFromString(priStr);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance(RSA);
    PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
    return privateKey;
  }

  /**
   * 用公钥加密 <br> 每次加密的字节数，不能超过密钥的长度值减去11
   *
   * @param data 需加密数据的byte数据
   * @param publicKey 公钥
   * @return 加密后的byte型数据
   */
  public static byte[] encryptData(byte[] data, PublicKey publicKey) {

    try {
      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      // 编码前设定编码方式及密钥
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      // 传入编码数据并返回编码结果
      int inputLen = data.length;
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      int offSet = 0;
      byte[] cache;
      int i = 0;
      // 对数据分段加密
      while (inputLen - offSet > 0) {
        if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
          cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
        } else {
          cache = cipher.doFinal(data, offSet, inputLen - offSet);
        }
        out.write(cache, 0, cache.length);
        i++;
        offSet = i * MAX_ENCRYPT_BLOCK;
      }
      byte[] encryptedData = out.toByteArray();
      out.close();
      return encryptedData;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 用私钥解密
   *
   * @param encryptedData 经过encryptedData()加密返回的byte数据
   * @param privateKey 私钥
   */
  public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
    try {
      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      return cipher.doFinal(encryptedData);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 公钥加密
   *
   * @param data 要加密的数据
   * @param publicKey 公钥字符串
   * @return 加密后的数据
   */
  public static String encryptByPublicKey(String data, String publicKey) throws Exception {
    RSAPublicKey publicK = (RSAPublicKey) string2PublicKey(publicKey);
    byte[] bytes = encryptData(data.getBytes(), publicK);
    return Base64Utils.encodeToString(bytes);
  }

  /**
   * 私钥解密
   *
   * @param data 加密后的数据
   * @param privateKey 私钥字符串
   * @return 解密后的数据
   */
  public static String decryptByPrivateKey(String data, String privateKey) throws Exception {
    RSAPrivateKey privateK = (RSAPrivateKey) string2PrivateKey(privateKey);
    byte[] bytes = decryptData(Base64Utils.decodeFromString(data), privateK);
    return new String(bytes, "UTF-8");
  }

  /**
   * APP使用的公钥
   */
  public static String APP_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCI2UITDhEZnwvkKuhEDgkJLQbV2PX8WGTbRnzzVqJu0jGXyEQ4l01SCeCj3WL143kHqPzZWyykAXkdyMvQjR2Om2xNCoyYmRncBAgIXWIsAKx4UyUYV3ErLx4mfUjRbKceSa58XHnHwNU7mI6EIKlO7B1KopeJ27X3BbqaWuCptwIDAQAB";

  /**
   * APP使用的私钥
   */
  public static String APP_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIjZQhMOERmfC+Qq6EQOCQktBtXY9fxYZNtGfPNWom7SMZfIRDiXTVIJ4KPdYvXjeQeo/NlbLKQBeR3Iy9CNHY6bbE0KjJiZGdwECAhdYiwArHhTJRhXcSsvHiZ9SNFspx5JrnxcecfA1TuYjoQgqU7sHUqil4nbtfcFuppa4Km3AgMBAAECgYA893V9Yb3yiS3WB9uUPiB+awXpjZABTSwZmGygSE9zScd4VGs4PwfebbIbrsyguSe5ZurGttzLcDCSOVu73LJ2qCLbXuvU4+6/9p/MgQA9GHYZ6jwZ36ph3PSINBN5V1tsrE+Sc5LuoRYefI9SpgLTT7vVp/KImLGBqjSMqj0y4QJBAMEN1bvWzrCBBDXevEqVClSpkAULd+D3qGFboG8KyKWsQ6OlQ7/s9t68+8eCz5P6aqxnw5h+W2obc6UFhKkiuxECQQC1d/n/Lq8SXL90BzJ7ONHOfoiFLkHL5jZX931p7rBTsPK6VzJ/ueh6T1DzvhJEnVgNUWbqUkYmh+VB+3m/gkhHAkB01tq0hJ0zVeImeFedC/bkpnrzkq1LCpht0/aVPgGRw2Bsemk2j0QaKmZ3qUPYW4aUMXi/ojnRTiv/hAd2Ff8BAkBPMG74669cbPrjMCzYVuA/ozoCHEB8MRbv5kFlU9LAgsw5gsm3sTIBBdHGu+AO7za4gRhPEt4n/E+QLqRfbQQLAkAIM9y3bIhqN+63R2+4AEeBRlXGf823llutcl+2LbJuMQEtWMrKKyvHSw6UiWUGKpMMMO94wNpkmxCFJPz8BCtR";

  public static void main(String[] args) throws Exception {
    /*KeyPair keyPair = generateRSAKeyPair();
    RSAPrivateKey privateK = (RSAPrivateKey)keyPair.getPrivate();
    RSAPublicKey publicK = (RSAPublicKey)keyPair.getPublic();

    String privateKey = Base64Utils.encodeToString(privateK.getEncoded());
    System.out.println("私钥："+privateKey);

    String publicKey = Base64Utils.encodeToString(publicK.getEncoded());
    System.out.println("公钥："+publicKey);*/

    String encryptByPublicKey = encryptByPublicKey("admin" , APP_PUBLIC_KEY);
    System.out.println("admin用公钥加密后：" + encryptByPublicKey);

    /*String aa = "";

    String decryptByPrivateKey = decryptByPrivateKey(aa, APP_PRIVATE_KEY);
    System.out.println("admin用私钥解密后："+decryptByPrivateKey);*/

    System.out.println(UUID.randomUUID().toString());
  }


}
