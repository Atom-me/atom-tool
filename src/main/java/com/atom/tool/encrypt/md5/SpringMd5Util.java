package com.atom.tool.encrypt.md5;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/**
 * 使用 Spring 提供的 MD5 加密工具类实现
 *
 * @author Atom
 */
public class SpringMd5Util {

    /**
     * 使用Spring 工具类实现
     *
     * @param plainText
     * @return
     */
    public static String md5EncryptUseSpringV1(String plainText) {
        return DigestUtils.md5DigestAsHex(plainText.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 使用Spring 工具类实现
     *
     * @param plainText
     * @return
     */
    public static String md5EncryptUseSpringV2(String plainText) {
        StringBuilder sb = new StringBuilder();
        DigestUtils.appendMd5DigestAsHex(plainText.getBytes(StandardCharsets.UTF_8), sb);
        return sb.toString();
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.err.println(md5EncryptUseSpringV1("123456"));
        System.err.println(md5EncryptUseSpringV2("123456"));
    }
}
