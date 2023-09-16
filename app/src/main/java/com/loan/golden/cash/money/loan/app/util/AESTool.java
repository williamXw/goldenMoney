package com.loan.golden.cash.money.loan.app.util;


import android.util.Base64;

import org.bouncycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class AESTool {

    /**
     * 加密算法
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 字符编码
     */
    private static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * AES 加密
     *
     * @param data      待加密内容
     * @param secretKey 加密密码，长度：16 或 32 个字符
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt1(String data, String secretKey) {
        try {
            //创建密码器
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //初始化为加密密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(secretKey));
            byte[] encryptByte = cipher.doFinal(data.getBytes(CHARSET_UTF8));
            // 将加密以后的数据进行 Base64 编码
            return new String(Hex.encode(encryptByte));
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * 如果 AES 的密钥小于 {@code length} 的长度，就对秘钥进行补位，保证秘钥安全。
     *
     * @param secretKey 密钥 key
     * @param length    密钥应有的长度
     * @param text      默认补的文本
     * @return 密钥
     */
    private static String toMakeKey(String secretKey, int length, String text) {
        // 获取密钥长度
        int strLen = secretKey.length();
        // 判断长度是否小于应有的长度
        if (strLen < length) {
            // 补全位数
            StringBuilder builder = new StringBuilder();
            // 将key添加至builder中
            builder.append(secretKey);
            // 遍历添加默认文本
            for (int i = 0; i < length - strLen; i++) {
                builder.append(text);
            }
            // 赋值
            secretKey = builder.toString();
        }
        return secretKey;
    }

    /**
     * 使用密码获取 AES 秘钥
     */
    public static SecretKeySpec getSecretKey(String secretKey) {
        secretKey = toMakeKey(secretKey, 16, "0");
        return new SecretKeySpec(secretKey.getBytes(CHARSET_UTF8), KEY_ALGORITHM);
    }

    /**
     * 将 字节数组 转换成 Base64 编码
     */
    public static String base64Encode(byte[] data) {
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

    /**
     * 处理异常
     */
    private static void handleException(Exception e) {
        e.printStackTrace();
    }

    /**
     * AES加密字符串
     *
     * @param content  需要被加密的字符串
     * @param password 加密需要的密码
     * @return 密文
     */
    public static byte[] encrypt(String content, String password) {
        try {
            if (password == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (password.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, new SecureRandom(password.getBytes()));// 利用用户密码作为随机数初始化出
            byte[] raw = password.getBytes("utf-8");
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            SecretKeySpec skeySpec = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] result = cipher.doFinal(content.getBytes("utf-8"));

//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            random.setSeed(password.getBytes());
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
//            kgen.init(128, random);// 利用用户密码作为随机数初始化出
//            // 128位的key生产者
//            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
//            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
//            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
//            // null。
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
//            byte[] byteContent = content.getBytes("utf-8");
//            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
//            byte[] result = cipher.doFinal(byteContent);// 加密
            return result;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串
     *
     * @param content  AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            // 判断Key是否正确
            if (password == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (password.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = password.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

            try {
                byte[] original = cipher.doFinal(content);
                return original;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }

//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            random.setSeed(password.getBytes());
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
//            kgen.init(128, random);// 利用用户密码作为随机数初始化出
//            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
//            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
//            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
//            byte[] result = cipher.doFinal(content);
//            return null; // 明文
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String content, String password) {
        byte[] decryptFrom = parseHexStr2Byte(content);
        byte[] decrypt = decrypt(decryptFrom, password);
        if (decrypt != null && decrypt.length != 0) {
            return new String(decrypt);
        }
        return null;
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void test() {
        String password = "1234512345123456";

        String str = "12382929sadadasd";

        byte[] encryptResult = encrypt(str, password);
        String encryptResultStr = parseByte2HexStr(encryptResult);
        System.out.println("加密后：" + encryptResultStr);

        byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
        byte[] decryptResult = decrypt(decryptFrom, password);
        System.out.println("解密后：" + new String(decryptResult));
    }
}
