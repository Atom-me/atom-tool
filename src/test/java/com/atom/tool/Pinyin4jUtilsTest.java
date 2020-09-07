package com.atom.tool;

import com.atom.tool.core.Pinyin4jUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * EncryptUtilTest
 *
 * @author Atom
 */
@Slf4j
public class Pinyin4jUtilsTest {

    @Test
    public void testToPinYin() {
        System.err.println(Pinyin4jUtils.toPinyin("北京市"));
        System.err.println(Pinyin4jUtils.toPinyin("🌹"));
        System.err.println(Pinyin4jUtils.toPinyin("犇"));
        System.err.println(Pinyin4jUtils.toPinyin("猋"));
        System.err.println(Pinyin4jUtils.toPinyin("麤"));
        System.err.println(Pinyin4jUtils.toPinyin("毳"));
        System.err.println(Pinyin4jUtils.toPinyin("焺"));
        System.err.println(Pinyin4jUtils.toPinyin("翯"));
        System.err.println(Pinyin4jUtils.toPinyin("囍"));
        System.err.println(Pinyin4jUtils.toPinyin("匰"));
        System.err.println(Pinyin4jUtils.toPinyin("囕囖"));

    }

}

