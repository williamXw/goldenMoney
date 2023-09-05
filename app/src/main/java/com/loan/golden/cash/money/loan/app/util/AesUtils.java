package com.loan.golden.cash.money.loan.app.util;

import org.bouncycastle.util.encoders.Hex;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author :    hxw
 * @Date :    2023/9/5 15:52
 * @Describe :    加密解密工具类
 */
public class AesUtils {

    private static final String key = "6mmnZNKbxbkAkERn";//接口请求AES密钥

    /**
     * 算法的名称
     */
    private static final String AES = "AES";

    /**
     * 默认 AES/CBC/PKCS5Padding
     * <p>
     * 算法：AES
     * 模式：CBC； 其中CBC、CFB模式需要向量；OFB模式不需要向量
     * 填充：PKCS5Padding
     */
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 编码 utf-8
     */
    private static final String UTF_8 = "utf-8";

    /**
     * 加密
     *
     * @param needEncryptStr 待加密字符串
     * @return
     * @throws Exception
     */
    public static String encrypt(String needEncryptStr) throws Exception {
        //创建AES的Key生产者
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        // 利用用户密码作为随机数初始化出,创建AES的Key生产者
        //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        // 根据用户密码，生成一个密钥
        SecretKey generatedSecretKey = keyGenerator.generateKey();
        // 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
        byte[] encodedBytes = generatedSecretKey.getEncoded();
//        byte[] raw = key.getBytes(UTF_8);
        //设置秘钥
        SecretKeySpec keySpec = new SecretKeySpec(encodedBytes, AES);
        //初始化加密方式  Cipher.ENCRYPT_MODE 加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        //加密；   设置为utf-8, 防止中文和英文混合
        byte[] encrypted = cipher.doFinal(needEncryptStr.getBytes(UTF_8));
        //对加密结果HEX编码； 解密时也就需要使用HEX解码；
        byte[] encode = Hex.encode(encrypted);
        return new String(encode).toUpperCase();
    }

    /**
     * 解密
     *
     * @param needDecryptStr 秘钥
     * @param needDecryptStr
     * @return
     * @throws Exception
     */
    public static String decrypt(String needDecryptStr) throws Exception {
        //创建AES的Key生产者
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        // 根据用户密码，生成一个密钥
        SecretKey generatedSecretKey = keyGenerator.generateKey();
        //设置秘钥
        byte[] encodedBytes = generatedSecretKey.getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(encodedBytes, AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        //解密
        byte[] original = cipher.doFinal(needDecryptStr.getBytes(UTF_8));
        byte[] decode = Hex.decode(original);
        return new String(decode);
    }
}
