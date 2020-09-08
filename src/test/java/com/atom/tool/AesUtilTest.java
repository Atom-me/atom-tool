package com.atom.tool;

import com.atom.tool.encrypt.aes.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * EncryptUtilTest
 *
 * @author Atom
 */
@Slf4j
public class AesUtilTest {

    @Test
    public void testEncryptBase64() {
        String encryptedStr = AesUtil.encryptBase64("Atom", "mima");
        System.err.println("加密后:::" + encryptedStr);
        String clearText = AesUtil.decryptBase64(encryptedStr, "mima");
        System.err.println("解密后:::" + clearText);
    }

    @Test
    public void testEncryptHex() {
        String encryptedStr = AesUtil.encryptHex("Atom", "mima");
        System.err.println("加密后:::" + encryptedStr);
        String clearText = AesUtil.decryptHex(encryptedStr, "mima");
        System.err.println("解密后:::" + clearText);
    }


    @Test
    public void testEncryptForCampaign() throws Exception {
        String encryptedStr = AesUtil.encryptForCampaign("Atom", "mima");
        System.err.println("加密后:::" + encryptedStr);
        String clearText = AesUtil.decryptForCampaign(encryptedStr, "mima");
        System.err.println("解密后:::" + clearText);
    }


}

