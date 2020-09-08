package com.atom.tool.encrypt.md5;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * JDK MD5 加密算法实现
 *
 * @author Atom
 */
public class JdkMd5Util {

    /**
     * The MD5 message digest algorithm defined in RFC 1321.
     */
    public static final String MD5 = "MD5";

    /**
     * 使用JDK自带MD5加密算法实现,其他第三方工具类封装都是基于这个算法原理
     *
     * @param plainText
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String md5EncryptUseJdk(String plainText) throws NoSuchAlgorithmException {
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest md5 = MessageDigest.getInstance(MD5);
        // 使用指定的字节更新摘要
        md5.update(plainText.getBytes(StandardCharsets.UTF_8));
        // 获得密文,digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        byte[] hash = md5.digest();

        StringBuilder buf = new StringBuilder();
        for (byte b : hash) {
            //将字节 转换为 十六进制字符串的表示形式，取最后两位（如果不够两位，前面补0）拼接为一个32位字符串
            System.out.println(b + " ----> " + Integer.toHexString(b));
            /**
             * %02x means print at least 2 digits, prepend it with 0's if there's less.
             * In your case it's 7 digits, so you get no extra 0 in front.
             * Also, %x is for int, but you have a long. Try %08lx instead.
             */
            buf.append(String.format("%02X", b));
        }

        return buf.toString().toLowerCase();
        /**
         * 或者使用  BigInteger 方法转换十六进制
         BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
         return new BigInteger(1, hash).toString(16);
         */
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.err.println(md5EncryptUseJdk("123456"));
    }
}
