package com.atom.tool.core;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Atom
 */
@Slf4j
public class Pinyin4jUtil {

    /**
     * 中文转拼音
     *
     * @param chinese
     * @return
     */
    public static String toPinyin(String chinese) {
        StringBuilder pinyinStr = new StringBuilder();
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultOutputFormat = new HanyuPinyinOutputFormat();
        defaultOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : newChar) {
            if (c > 128) {
                try {
                    String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(c, defaultOutputFormat);
                    if (ArrayUtils.isEmpty(pinyinArr)) {
                        pinyinStr.append(c);
                    } else {
                        pinyinStr.append(PinyinHelper.toHanyuPinyinStringArray(c, defaultOutputFormat)[0]);
                    }
                } catch (Exception e) {
                    log.warn("'{}' of '{}' toPinyin error.", c, chinese, e);
                }
            } else {
                pinyinStr.append(c);
            }
        }
        return pinyinStr.toString();
    }

}
