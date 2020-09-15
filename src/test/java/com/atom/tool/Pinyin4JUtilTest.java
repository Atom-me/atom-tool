package com.atom.tool;

import com.atom.tool.core.Pinyin4jUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * EncryptUtilTest
 *
 * @author Atom
 */
@Slf4j
public class Pinyin4JUtilTest {

    @Test
    public void testToPinYin() {
        System.err.println(Pinyin4jUtil.toPinyin("北京市"));
        System.err.println(Pinyin4jUtil.toPinyin("🌹"));
        System.err.println(Pinyin4jUtil.toPinyin("犇"));
        System.err.println(Pinyin4jUtil.toPinyin("猋"));
        System.err.println(Pinyin4jUtil.toPinyin("麤"));
        System.err.println(Pinyin4jUtil.toPinyin("毳"));
        System.err.println(Pinyin4jUtil.toPinyin("焺"));
        System.err.println(Pinyin4jUtil.toPinyin("翯"));
        System.err.println(Pinyin4jUtil.toPinyin("囍"));
        System.err.println(Pinyin4jUtil.toPinyin("匰"));
        System.err.println(Pinyin4jUtil.toPinyin("囕囖"));

    }

}

