package com.atom.tool.encrypt.md5;

import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 使用 apache.commons.codec 提供的 MD5 加密算法实现
 *
 * @author Atom
 */
public class ApacheMd5Util {

    /**
     * The MD5 message digest algorithm defined in RFC 1321.
     */
    public static final String MD5 = "MD5";


    /**
     * 使用 apache commons 工具类实现
     *
     * @param plainText
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String md5EncryptUseApacheCommonsV1(String plainText) throws NoSuchAlgorithmException {
        //使用DigestUtils.digest()直接获取加密后的字节数组
        byte[] hash = DigestUtils.digest(MessageDigest.getInstance(MD5), plainText.getBytes(StandardCharsets.UTF_8));
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
    }


    /**
     * 使用apache commons 工具类实现，直接调用md5Hex方法，获得加密后十六进制字符串
     *
     * @param plainText
     * @return
     */
    public static String md5EncryptUseApacheCommonsV2(String plainText) {
        return DigestUtils.md5Hex(plainText);
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.err.println(md5EncryptUseApacheCommonsV1("123456"));
        System.err.println(md5EncryptUseApacheCommonsV2("123456"));
    }
}
