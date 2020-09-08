package com.atom.tool.encoding;

import com.google.common.base.Preconditions;

/**
 * 实现Base64 编码 解码
 *
 * @author Atom
 */
public final class Base64 {

    private Base64() {
    }


    /**
     * Base64编码表
     */
    private static final String CODE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final char[] CODE_DICT = CODE_STRING.toCharArray();

    /**
     * 实现Base64 编码
     * <p>
     * Base64 编码 原理：比如编码小写 a
     * 1,先把把文本转换为其二进制形式：01100001
     * 2,然后把8个位按每6个去分组，后面不够的补位0： 011000 010000 （011000 转十进制是 24 ，010000转十进制是 16）
     * 3,然后查找Base64字符表 找到对应索引 24 和16 的对应字符 YQ
     * 4,后面补了4个0 ，我们补两个0是一个等号=，4个零就是 两个== ： YQ==
     */
    public static String encode(String plain) {
        Preconditions.checkNotNull(plain);
        StringBuilder result = new StringBuilder();
        StringBuilder binaryString = new StringBuilder(toBinary(plain));

        // 6位一组，分组的时候计算后面需要补位几个零(其实就是只补位最后一组)
        int delta = 6 - binaryString.length() % 6;
        for (int d = 0; d < delta && delta != 6; d++) {
            binaryString.append("0");
        }

        for (int index = 0; index < binaryString.length() / 6; index++) {
            int begin = index * 6;
            String encodingString = binaryString.substring(begin, begin + 6);
            char encodeChar = CODE_DICT[Integer.valueOf(encodingString, 2)];
            result.append(encodeChar);
        }

        // 处理最后的如果补位两个0 就替换一个等号
        if (delta != 6) {
            for (int i = 0; i < delta / 2; i++) {
                result.append("=");
            }
        }

        return result.toString();
    }

    /**
     * 转二进制形式
     *
     * @param source
     * @return
     */
    private static String toBinary(final String source) {
        final StringBuilder binaryResult = new StringBuilder();
        for (int index = 0; index < source.length(); index++) {
            StringBuilder charBinary = new StringBuilder(Integer.toBinaryString(source.charAt(index)));
            // 计算是否需要补齐8位
            int delta = 8 - charBinary.length();
            for (int d = 0; d < delta; d++) {
                charBinary.insert(0, "0");
            }
            binaryResult.append(charBinary);
        }
        return binaryResult.toString();
    }

    /**
     * base64 解码原理，比如解码 YQ== 就是按编码的原理反着来
     * 1,先把字符串后面的等号都删掉变为 YQ
     * 2,然后 根据base64 字符表 找到Y和Q对应的索引是 24 和 16
     * 3,然后 将 24 和16 转为 两个6位二进制数据 得到 11000 和 10000 不够6位 前面补0 就是  011000 和 010000
     * 4,然后 把他们合在一起 011000010000 按标准二进制（一字节8位 1byte ➡ 8位 ）合并之后的长度除以8（按每8位分割） 得到 01100001
     * 5,然后 把 01100001 换算位 十进制得到 97
     * 6,然后 查询ascii码表找到对应字符 小写 a
     *
     * @param encrypt
     * @return
     */
    public static String decode(final String encrypt) {
        StringBuilder resultBuilder = new StringBuilder();
        String temp = encrypt;
        int equalIndex = temp.indexOf("=");
        if (equalIndex > 0) {
            temp = temp.substring(0, equalIndex);
        }

        String base64Binary = toBase64Binary(temp);

        // 转为8位二进制 ，然后 换算为十进制数据
        for (int i = 0; i < base64Binary.length() / 8; i++) {
            int begin = i * 8;
            String str = base64Binary.substring(begin, begin + 8);
            // 将ascii码转为字符
            char c = Character.toChars(Integer.valueOf(str, 2))[0];
            resultBuilder.append(c);
        }
        return resultBuilder.toString();

    }

    private static String toBase64Binary(final String source) {
        final StringBuilder binaryResult = new StringBuilder();
        for (int index = 0; index < source.length(); index++) {
            // 查找字符在base64字符表中的索引
            int i = CODE_STRING.indexOf(source.charAt(index));
            StringBuilder charBinary = new StringBuilder(Integer.toBinaryString(i));
            int delta = 6 - charBinary.length();
            for (int d = 0; d < delta; d++) {
                charBinary.insert(0, "0");
            }
            binaryResult.append(charBinary);
        }
        return binaryResult.toString();
    }

    public static void main(String[] args) {
        System.out.println(encode("hello"));
        System.out.println(encode("a"));
        System.out.println(encode("12a"));
        System.out.println(encode("asdfasfasfasdfasfasdfasfdafasdfasfdasdfasfdasdfasdfdfasdfafasdfasdfasdfasdfasdfasdf"));
        System.out.println("===================");

        System.out.println(decode("aGVsbG8="));
        System.out.println(decode("YQ=="));
        System.out.println(decode("MTJh"));
        System.out.println(decode("YXNkZmFzZmFzZmFzZGZhc2Zhc2RmYXNmZGFmYXNkZmFzZmRhc2RmYXNmZGFzZGZhc2RmZGZhc2RmYWZhc2RmYXNkZmFzZGZhc2RmYXNkZmFzZGY"));

    }

}
