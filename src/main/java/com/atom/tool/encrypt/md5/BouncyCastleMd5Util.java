package com.atom.tool.encrypt.md5;

import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

/**
 * BouncyCastle 是一个开源的第三方算法提供商；
 * <p>
 * BouncyCastle 提供了很多Java标准库没有提供的哈希算法和加密算法；
 *
 * @author Atom
 */
public class BouncyCastleMd5Util {

    /**
     * 使用 BouncyCastle 实现的MD5加密算法
     *
     * @param plainText
     * @return
     */
    public static String md5Encrypt(String plainText) {
        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
        MD5Digest md5Digest = new MD5Digest();
        md5Digest.update(plainTextBytes, 0, plainTextBytes.length);
        byte[] digestedBuf = new byte[md5Digest.getDigestSize()];
        md5Digest.doFinal(digestedBuf, 0);
        return new String(Hex.encode(digestedBuf));
    }

    public static void main(String[] args) {
        System.err.println(md5Encrypt("123456"));
    }
}
