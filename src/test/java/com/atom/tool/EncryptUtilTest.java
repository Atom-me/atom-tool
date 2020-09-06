package com.atom.tool;

import com.atom.tool.encrypt.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * EncryptUtilTest
 */
@Slf4j
public class EncryptUtilTest {

    @Test
    public void checkEncrypt() {
        String encryptPassword = EncryptUtil.encrypt("atom");
        System.err.println(encryptPassword);
        boolean isCorrect = EncryptUtil.checkEncrypt(encryptPassword, "atom");
        System.err.println(isCorrect);
    }

    @Test
    public void encrypt() {
    }

    @Test
    public void strToByte() {
    }

    @Test
    public void verifyHash() {
    }


}

