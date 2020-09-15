package com.atom.tool.mail.util;


import lombok.extern.slf4j.Slf4j;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Atom
 */
@Slf4j
public class MailUtil {

    /**
     * 定义一个邮箱地址的正则表达式：姓名<邮箱>
     */
    private static final Pattern PATTERN = Pattern.compile("(.+)(<.+@126.com>)");

    private enum CodecType {
        ENCODE, DECODE
    }

    /**
     * 编码邮箱地址
     *
     * @param address
     * @return
     */
    public static String encodeAddress(String address) {
        return codec(CodecType.ENCODE, address);
    }

    /**
     * 解码邮箱地址
     *
     * @param address
     * @return
     */
    public static String decodeAddress(String address) {
        return codec(CodecType.DECODE, address);
    }

    private static String codec(CodecType codecType, String address) {
        // 需要对满足匹配条件的邮箱地址进行 UTF-8 编码，否则姓名将出现中文乱码
        Matcher addressMatcher = PATTERN.matcher(address);
        if (addressMatcher.find()) {
            try {
                if (codecType == CodecType.ENCODE) {
                    address = MimeUtility.encodeText(addressMatcher.group(1), "UTF-8", "B") + addressMatcher.group(2);
                } else {
                    address = MimeUtility.decodeText(addressMatcher.group(1)) + addressMatcher.group(2);
                }
            } catch (UnsupportedEncodingException e) {
                log.error("错误：邮箱地址编解码出错！", e);
            }
        }
        return address;
    }
}