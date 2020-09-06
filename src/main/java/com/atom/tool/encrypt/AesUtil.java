package com.atom.tool.encrypt;

import com.atom.tool.constants.ModeAndPadding;
import com.atom.tool.enums.AesTypeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;

import static com.atom.tool.constants.ModeAndPadding.AES_CBC_ISO10126PADDING;
import static com.atom.tool.constants.ModeAndPadding.AES_CBC_PKCS5PADDING;
import static com.atom.tool.enums.AesTypeEnum.AES_256;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

/**
 * AES加密工具
 *
 * @author Atom
 */
@Slf4j
public final class AesUtil {

    /**
     * 加密算法
     */
    private static final String ENCRY_ALGORITHM = "AES";

    /**
     * AES key 字节长度
     */
    public static final Integer AES_KEY_LENGTH = 16;


    /**
     * 【密钥偏移量】：必须是16个字节，密钥默认偏移，可更改
     * 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     */
    private static final String IV_PARAMETER = "126hsb6sduwwduc8";


    private AesUtil() {
    }


    private static byte[] encrypt(String modeAndPadding, SecretKey sk, IvParameterSpec iv, byte[] src) {
        try {
            Cipher c = Cipher.getInstance(modeAndPadding);
            c.init(Cipher.ENCRYPT_MODE, sk, iv);
            return c.doFinal(src);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException
                | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            log.warn(e.getLocalizedMessage(), e);
        }
        return null;

    }

    private static byte[] encrypt(byte[] clearTextBytes, byte[] pwdBytes) throws Exception {
        byte[] aesKey = new byte[AES_KEY_LENGTH];
        if (pwdBytes.length < AES_KEY_LENGTH) {
            throw new Exception("Length of AES key SHOULD BE 16 bytes");
        } else {
            if (AES_KEY_LENGTH >= 0) {
                System.arraycopy(pwdBytes, 0, aesKey, 0, AES_KEY_LENGTH);
            }
        }
        SecretKeySpec keySpec = new SecretKeySpec(aesKey, ENCRY_ALGORITHM);
        return encrypt(AES_CBC_PKCS5PADDING, keySpec, generateIv(IV_PARAMETER.getBytes(UTF_8)), clearTextBytes);
    }

    /**
     * BASE64加密
     *
     * @param clearText 明文，待加密的内容
     * @param password  密码，加密的密码
     * @return 返回密文，加密后得到的内容。加密错误返回null
     */
    public static String encryptBase64(String clearText, String password) {
        try {
            byte[] passwordByte = passwordToBytes(password);
            // 1 获取加密密文字节数组
            byte[] cipherTextBytes = encrypt(clearText.getBytes(UTF_8), passwordByte);
            // 2 对密文字节数组进行BASE64 encoder 得到 BASE6输出的密文,返回BASE64输出的密文
            return Base64.encodeBase64URLSafeString(cipherTextBytes);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        // 加密错误 返回null
        return null;
    }

    /**
     * HEX加密
     *
     * @param clearText 明文，待加密的内容
     * @param password  密码，加密的密码
     * @return 返回密文，加密后得到的内容。加密错误返回null
     */
    public static String encryptHex(String clearText, String password) {
        try {
            byte[] passwordByte = passwordToBytes(password);
            // 1 获取加密密文字节数组
            byte[] cipherTextBytes = encrypt(clearText.getBytes(UTF_8), passwordByte);
            // 2 对密文字节数组进行 转换为 HEX输出密文,返回 HEX输出密文
            return encodeHexString(cipherTextBytes);
        } catch (Exception e) {
            log.warn("encryptHex error:[{}]", Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    private static byte[] decrypt(String modeAndPadding, SecretKey sk, IvParameterSpec iv, byte[] cipherText) {
        try {
            Cipher c = Cipher.getInstance(modeAndPadding);
            c.init(Cipher.DECRYPT_MODE, sk, iv);
            return c.doFinal(cipherText);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException
                | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            log.warn("decrypt error:[{}]", Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    public static byte[] decrypt(byte[] cipherTextBytes, byte[] aesKeyByte) throws Exception {
        byte[] aesKey = new byte[AES_KEY_LENGTH];
        if (aesKeyByte.length < AES_KEY_LENGTH) {
            throw new Exception("Length of AES key SHOULD BE 16 bytes");
        } else {
            for (int i = 0; i < AES_KEY_LENGTH; i++) {
                aesKey[i] = aesKeyByte[i];
            }
        }
        SecretKeySpec keySpec = new SecretKeySpec(aesKey, ENCRY_ALGORITHM);
        return decrypt(AES_CBC_PKCS5PADDING, keySpec, generateIv(IV_PARAMETER.getBytes(UTF_8)), cipherTextBytes);
    }

    public static String decryptBase64(String cipherText, String aesKey) {
        try {
            byte[] aesKeyByte = passwordToBytes(aesKey);
            // 1 对 BASE64输出的密文进行 BASE64 编码 得到密文字节数组
            byte[] cipherTextBytes = Base64.decodeBase64(cipherText);
            // 2 对密文字节数组进行解密 得到明文字节数组
            byte[] clearTextBytes = decrypt(cipherTextBytes, aesKeyByte);
            // 3 根据 CHARACTER 转码，返回明文字符串
            return new String(clearTextBytes, UTF_8);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        // 解密错误返回null
        return null;
    }

    /**
     * HEX解密
     *
     * @param cipherText 密文，带解密的内容
     * @param password   密码，解密的密码
     * @return 返回明文，解密后得到的内容。解密错误返回null
     */
    public static String decryptHex(String cipherText, String password) {
        try {
            byte[] passwordByte = passwordToBytes(password);
            // 1 将HEX输出密文 转为密文字节数组
            byte[] cipherTextBytes = decodeHex(cipherText);
            // 2 将密文字节数组进行解密 得到明文字节数组
            byte[] clearTextBytes = decrypt(cipherTextBytes, passwordByte);
            // 3 根据 CHARACTER 转码，返回明文字符串
            return new String(clearTextBytes, UTF_8);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 密码处理方法 如果加解密出问题， 请先查看本方法，排除密码长度不足补"0",导致密码不一致
     *
     * @param password 待处理的密码
     * @return
     */
    private static byte[] passwordToBytes(String password) {
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Argument sKey is null.");
        }
        StringBuilder sb = new StringBuilder(AES_KEY_LENGTH);
        sb.append(password);
        while (sb.length() < AES_KEY_LENGTH) {
            sb.append("0");
        }
        if (sb.length() > AES_KEY_LENGTH) {
            sb.setLength(AES_KEY_LENGTH);
        }
        return sb.toString().getBytes(UTF_8);
    }

    /**
     * 生成iv,密钥偏移量
     */
    public static IvParameterSpec generateIv(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance(ENCRY_ALGORITHM);
        byte[] ivByte = new byte[16];
        new SecureRandom().nextBytes(ivByte);
        params.init(new IvParameterSpec(iv));
        return params.getParameterSpec(IvParameterSpec.class);
    }


    /**
     * 加密/解密
     *
     * @param mode           {@link Cipher#ENCRYPT_MODE}  {@link Cipher#DECRYPT_MODE}
     * @param byteContent    the context bytes for encrypt or decrypt
     * @param key            encrypt/decrypt key
     * @param iv             iv
     * @param type           {@link AesTypeEnum}
     * @param modeAndPadding {@link ModeAndPadding}
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     */
    private static byte[] encryptOrDecrypt(int mode, byte[] byteContent, String key, IvParameterSpec iv, AesTypeEnum type, String modeAndPadding) throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        KeyGenerator kGen = KeyGenerator.getInstance(ENCRY_ALGORITHM);
        //此处解决mac，linux报错
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes(UTF_8));
        kGen.init(type.getValue(), random);
        SecretKey secretKey = kGen.generateKey();
        byte[] encodedSecretKey = secretKey.getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(encodedSecretKey, ENCRY_ALGORITHM);
        // 创建密码器
        Cipher cipher = Cipher.getInstance(modeAndPadding);
        cipher.init(mode, keySpec, iv);
        return cipher.doFinal(byteContent);
    }

    /**
     * 加密/解密
     *
     * @param isEncrypt      true: encrypt  false: decrypt
     * @param source         the context bytes for encrypt or decrypt
     * @param key            encrypt/decrypt key
     * @param iv             iv
     * @param type           {@link AesTypeEnum}
     * @param modeAndPadding {@link ModeAndPadding}
     * @return
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static byte[] encryptOrDecrypt(boolean isEncrypt, byte[] source, String key, IvParameterSpec iv, AesTypeEnum type, String modeAndPadding) throws UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        if (isEncrypt) {
            return encryptOrDecrypt(Cipher.ENCRYPT_MODE, source, key, iv, type, modeAndPadding);
        } else {
            return encryptOrDecrypt(Cipher.DECRYPT_MODE, source, key, iv, type, modeAndPadding);
        }
    }

    /**
     * 加密/解密
     *
     * @param isEncrypt      true: encrypt  false: decrypt
     * @param source         the context bytes for encrypt or decrypt
     * @param key            encrypt/decrypt key
     * @param type           {@link AesTypeEnum}
     * @param modeAndPadding
     * @return {@link ModeAndPadding}
     * @throws Exception
     */
    public static byte[] encryptOrDecrypt(boolean isEncrypt, byte[] source, String key, AesTypeEnum type, String modeAndPadding) throws Exception {
        return encryptOrDecrypt(isEncrypt, source, key, generateIv(IV_PARAMETER.getBytes(UTF_8)), type, modeAndPadding);
    }


    public static String encryptForCampaign(String src, String aesKeys) throws Exception {
        byte[] encodeByte = encryptOrDecrypt(true, src.getBytes(UTF_8), aesKeys, AES_256, AES_CBC_ISO10126PADDING);
        return encodeHexString(encodeByte);
    }

    public static String decryptForCampaign(String src, String aesKeys) throws Exception {
        byte[] decodeByte = encryptOrDecrypt(false, decodeHex(src), aesKeys, AES_256, AES_CBC_ISO10126PADDING);
        return new String(decodeByte, UTF_8);
    }
}
