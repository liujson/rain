package cn.liujson.comm4j.codec;

import cn.liujson.comm4j.validate.Validate;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES 加密解密
 */
public class AES {
    /**
     * 字符串编码
     */
	private final static String CHAR_ENCODING = "UTF-8";

    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        Validate.notNull(data, "The validated data is null");
        Validate.notNull(data, "The validated key is null");
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, seckey);
            byte[] result = cipher.doFinal(data);
            // 加密
            return result;
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

	/**
	 * 解密
	 * @param data 待解密内容
	 * @param key 解密密钥
	 * @return
	 */
    public static byte[] decrypt(byte[] data, byte[] key) {
		Validate.notNull(data, "The validated data is null");
		Validate.notNull(data, "The validated key is null");
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec seckey = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, seckey);
            // 加密
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static String encryptToBase64(String data, String key) {
        try {
            byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), key.getBytes(CHAR_ENCODING));
            return new String(Base64.encode(valueByte));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }

    }

    public static String decryptFromBase64(String data, String key) {
        try {
            byte[] originalData = Base64.decode(data.getBytes());
            byte[] valueByte = decrypt(originalData, key.getBytes(CHAR_ENCODING));
            return new String(valueByte, CHAR_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static String encryptWithKeyBase64(String data, String key) {
        try {
            byte[] valueByte = encrypt(data.getBytes(CHAR_ENCODING), Base64.decode(key.getBytes()));
            return new String(Base64.encode(valueByte));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    public static String decryptWithKeyBase64(String data, String key) {
        try {
            byte[] originalData = Base64.decode(data.getBytes());
            byte[] valueByte = decrypt(originalData, Base64.decode(key.getBytes()));
            return new String(valueByte, CHAR_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    public static byte[] genarateRandomKey() {
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance(AES_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(" genarateRandomKey fail!", e);
        }
        SecureRandom random = new SecureRandom();
        keygen.init(random);
        Key key = keygen.generateKey();
        return key.getEncoded();
    }

    public static String genarateRandomKeyWithBase64() {
        return new String(Base64.encode(genarateRandomKey()));
    }


    public static void main(String[] args) {
//        String key = RandomStringUtils.randomAlphabetic(16);
//        System.out.println("key: " + key);
//        System.out.println(AES.encryptToBase64("wangtiansoft#test123!#$@#$", key));
//        System.out.println(AES.encryptToBase64("wangtiansoft#test123!#$@#$", key));
//        System.out.println(AES.encryptToBase64("wangtiansoft#test123!#$@#$", key));
    }

}
