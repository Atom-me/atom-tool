package com.atom.tool.encrypt.md5;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;

/**
 * 使用 Pbkdf2 算法， 实现动态盐 加密
 * <p>
 * Do not use MD5 — Java’s MessageDigest was invented in 1992 s a fast algorithm
 * and therefore useless against brute-force attacks.
 * Just google ‘md5 decrypt online’ and first link will destroy your ‘security’
 * <p>
 * PBKDF2, BCrypt, and SCrypt are recommended algorithms.
 * Each of these three is slow, and each has the feature of having a configurable strength,
 * so as computers increase in strength, we can slow down the algorithm by changing the inputs.
 *
 * @author Atom
 */
public class Pbkdf2UtilV1 {

    /**
     * sha1 hash 迭代次数
     */
    private static final int ITERATION_COUNT = 10000;
    private static final int KEY_LENGTH = 16 * 8;

    public static String encrypt(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        // 生成随机盐
        byte[] randomSalt = generateRandomSalt(16);
        System.err.println("随机盐: " + Hex.encodeHexString(randomSalt));

        // 明文密码+随机盐 进行 指定次数的SHA-1 散列运算（不可逆）
        byte[] randomSaltHash = hashPassword(password, randomSalt, ITERATION_COUNT);
        System.err.println("随机盐Hash: " + Hex.encodeHexString(randomSaltHash));

        //将 随机盐 和 saltHash 进行 hex 编码,得到最终密码
        String encryptedPassword = Hex.encodeHexString(randomSalt) + Hex.encodeHexString(randomSaltHash);
        System.err.println("加密得到的最终密码: " + encryptedPassword);
        return encryptedPassword;
    }

    /**
     * 校验密码
     * 主要是根据 加密后的密码字符串，解析出 加密时 使用的 随机盐
     * 然后 使用这个随机盐 + 请求密码  再次进行 相同的 sha1 hash运算
     * 因为两次加密使用的随机盐是相同的，如果密码相同，则会得到一个 和加密时的到的 相同的hash值
     * <p>
     * 自然，随机盐 + hash值 十六进制转码得到的字符串也是相同的
     *
     * @param plainText
     * @param oldEncryptPassword
     * @return
     * @throws DecoderException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static boolean verifyPassword(String plainText, String oldEncryptPassword) throws DecoderException, InvalidKeySpecException, NoSuchAlgorithmException {
        //从数据库查出对应账户的密码，解析出加密时使用的随机盐
        String oldRandomSaltStr = oldEncryptPassword.substring(0, 16 * 2);
        byte[] oldRandomSalt = Hex.decodeHex(oldRandomSaltStr);
        System.err.println("解析出的随机盐: " + oldRandomSaltStr);

        //重新组装，明文密码+随机盐 进行sha-1加密
        byte[] newRandomSaltHash = hashPassword(plainText, oldRandomSalt, ITERATION_COUNT);

        //使用 解析出的随机盐 和 新的 密码和随机盐的sha1 hash值 拼接出一个最终密码
        String newEncryptPassword = Hex.encodeHexString(oldRandomSalt) + Hex.encodeHexString(newRandomSaltHash);
        System.err.println("使用请求密码加密得到的最终密码: " + newEncryptPassword);
        return newEncryptPassword.equals(oldEncryptPassword);
    }

    /**
     * 校验密码
     * 主要是根据 加密后的密码字符串，解析出 加密时 使用的 随机盐
     * 然后 使用这个随机盐 + 请求密码  再次进行 相同的 sha1 hash运算
     * 因为两次加密使用的随机盐是相同的，如果密码相同，则会得到一个 和加密时的到的 相同的hash值
     * <p>
     * 自然，随机盐 + hash值 十六进制转码得到的字符串也是相同的
     *
     * @param plainText
     * @param encryptedPassword
     * @return
     * @throws DecoderException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static boolean verifyPassword2(String plainText, String encryptedPassword) throws DecoderException, InvalidKeySpecException, NoSuchAlgorithmException {
        //从数据库查出对应账户的密码，解析出 加密时使用的随机盐
        String oldRandomSalt = encryptedPassword.substring(0, 16 * 2);
        byte[] randomSaltHash = Hex.decodeHex(oldRandomSalt);
        System.err.println("解析出的随机盐：" + oldRandomSalt);

        String oldSha1HashStr = encryptedPassword.substring(16 * 2);
        byte[] oldSha1Hash = Hex.decodeHex(oldSha1HashStr);
        System.err.println("解析出的随机盐+密码的sha-1 hash值：" + oldSha1HashStr);

        //校验两个hash值是否相同即可
        byte[] newSaltHash = hashPassword(plainText, randomSaltHash, ITERATION_COUNT);

        return Arrays.equals(newSaltHash, oldSha1Hash);
    }


    private static byte[] hashPassword(String password, byte[] randomSalt, int iterationCount) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), randomSalt, iterationCount, KEY_LENGTH);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        return secretKeyFactory.generateSecret(spec).getEncoded();

    }

    private static byte[] generateRandomSalt(int byteLength) {
        Random ranGen = new SecureRandom();
        byte[] randomSalt = new byte[byteLength];
        ranGen.nextBytes(randomSalt);
        return randomSalt;
    }


    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, DecoderException {
        String encryptedPassword = encrypt("123456");
        System.err.println(verifyPassword("123456", encryptedPassword));
        System.err.println(verifyPassword2("123456", encryptedPassword));
    }
}
