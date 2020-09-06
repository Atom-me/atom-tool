package com.atom.tool.encrypt;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

/**
 * PBKDF2WithHmacSHA256 加密算法加密
 *
 * @author Atom
 */
public class EncryptUtil {

    /**
     * 生成hash方法
     *
     * @param password
     * @param salt
     * @return
     * @throws Exception
     */
    private static byte[] generateHash(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 4096, 36 * 8);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return secretKeyFactory.generateSecret(spec).getEncoded();
    }

    /**
     * 转换方法
     *
     * @param inByte
     * @return
     */
    private static String byteToStr(byte[] inByte) {
        StringBuilder byteString = new StringBuilder();
        for (byte b : inByte) {
            byte highbyte = (byte) (b & 0xF0);
            byte lowByte = (byte) (b & 0xF);
            highbyte = (byte) (highbyte >>> 4);
            highbyte = (byte) (highbyte & 0xF);

            char high = (highbyte >= 10) ? (char) (highbyte + 87) : (char) (highbyte + 48);
            char low = (lowByte >= 10) ? (char) (lowByte + 87) : (char) (lowByte + 48);

            String tmp = String.valueOf(high);
            byteString.append(tmp);
            tmp = String.valueOf(low);
            byteString.append(tmp);
        }

        return byteString.toString();
    }

    /**
     * 转换方法
     *
     * @param s
     * @return
     * @throws Exception
     */
    public static byte[] strToByte(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            throw new Exception("input string is null");
        }
        s = s.trim();
        if (s.length() % 2 != 0) {
            throw new Exception("input string length error");
        }
        byte[] bytearray = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); ++i) {
            byte highByte, lowByte;
            int k = i++;
            if (s.charAt(k) >= 'a') {
                highByte = (byte) (s.charAt(k) - 'W' & 0xF);
            } else {
                highByte = (byte) (s.charAt(k) & 0xF);
            }
            if (s.charAt(i) >= 'a') {
                lowByte = (byte) (s.charAt(i) - 'W' & 0xF);
            } else {
                lowByte = (byte) (s.charAt(i) & 0xF);
            }

            highByte = (byte) (highByte << 4);
            bytearray[(i / 2)] = (byte) (highByte | lowByte);
        }

        return bytearray;
    }

    /**
     * 验证方法
     *
     * @param a
     * @param b
     * @return
     */
    private static boolean notEquals(byte[] a, byte[] b) {
        for (int i = 0; i < a.length; ++i) {
            if (a[i] != b[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证方法
     *
     * @param password
     * @param salt
     * @param baseHash
     * @return
     */
    public static boolean verifyHash(String password, byte[] salt, byte[] baseHash) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 4096, 36 * 8);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] matchHash = secretKeyFactory.generateSecret(spec).getEncoded();
            return Arrays.equals(matchHash, baseHash);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 加密入口
     *
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] salt = new byte[20];
            secureRandom.nextBytes(salt);
            byte[] hash = generateHash(password, salt);
            String encryptPassword = byteToStr(hash) + byteToStr(salt);
            System.err.println("encrypt hash:::" + byteToStr(hash));
            System.err.println("encrypt salt:::" + byteToStr(salt));
            return encryptPassword;
        } catch (Exception e) {
            return password;
        }
    }

    /**
     * 结果校验
     *
     * @param encryptText
     * @param plainText
     * @return
     */
    public static boolean checkEncrypt(String encryptText, String plainText) {
        try {
            byte[] hash = strToByte(encryptText.substring(0, 72));
            byte[] salt = strToByte(encryptText.substring(72));
            return verifyHash(plainText, salt, hash);
        } catch (Exception e) {
            return false;
        }
    }


}
