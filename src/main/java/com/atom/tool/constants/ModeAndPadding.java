package com.atom.tool.constants;

/**
 * AES算法在对明文加密的时候，并不是把整个明文一股脑加密成一整段密文，而是把明文拆分成一个个独立的明文块，每一个明文块长度128bit【AES的区块长度固定为128位】。
 * 这些明文块经过AES加密器的复杂处理，生成一个个独立的密文块，这些密文块拼接在一起，就是最终的AES加密结果。
 * <p>
 * 但是这里涉及到一个问题：
 * 假如一段明文长度是192bit，如果按每128bit一个明文块来拆分的话，第二个明文块只有64bit，不足128bit。这时候怎么办呢？就需要对明文块进行填充（Padding）
 * <p>
 * <p>
 * 1.把明文按照128bit拆分成若干个明文块。
 * 2.按照选择的填充方式来填充【最后一个】明文块。
 * 3.每一个明文块利用AES加密器和密钥，加密成密文块。
 * 4.拼接所有的密文块，成为最终的密文结果。
 *
 * @author Atom
 */
public class ModeAndPadding {

    //    算法/模式/填充                 16字节加密后数据长度       不满16字节加密后长度
//    AES/CBC/NoPadding                   16                          不支持
//    AES/CBC/PKCS5Padding                32                          16
//    AES/CBC/ISO10126Padding             32                          16
//    AES/CFB/NoPadding                   16                          原始数据长度
//    AES/CFB/PKCS5Padding                32                          16
//    AES/CFB/ISO10126Padding             32                          16
//    AES/ECB/NoPadding                   16                          不支持
//    AES/ECB/PKCS5Padding                32                          16
//    AES/ECB/ISO10126Padding             32                          16
//    AES/OFB/NoPadding                   16                          原始数据长度
//    AES/OFB/PKCS5Padding                32                          16
//    AES/OFB/ISO10126Padding             32                          16
//    AES/PCBC/NoPadding                  16                          不支持
//    AES/PCBC/PKCS5Padding               32                          16
//    AES/PCBC/ISO10126Padding            32                          16
    /**
     * 默认为 ECB/PKCS5Padding
     * 【ECB】：AES默认工作模式
     * 【PKCS5Padding】：AES默认填充类型
     * <p>
     * 加密的时候使用了一种模式，解密的时候也必须采用同样的模式
     */
    public final static String AES_DEFAULT = "AES";
    /**
     * AES/CBC/NoPadding：
     * 【CBC】：AES的工作模式
     * 【NoPadding】：不做任何填充，但是要求明文必须是16字节的整数倍。
     */
    public final static String AES_CBC_NOPADDING = "AES/CBC/NoPadding";
    /**
     * AES/CBC/PKCS5Padding：（默认）
     * 【CBC】：AES的工作模式
     * 【PKCS5Padding】：如果明文块少于16个字节（128bit），在明文块末尾补足相应数量的字符，且每个字节的值等于缺少的字符数。
     * 比如明文：{1,2,3,4,5,a,b,c,d,e},缺少6个字节，则补全为{1,2,3,4,5,a,b,c,d,e,6,6,6,6,6,6}
     */
    public final static String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";
    /**
     * AES/CBC/ISO10126Padding
     * 【CBC】：AES的工作模式
     * 【ISO10126Padding】：如果明文块少于16个字节（128bit），在明文块末尾补足相应数量的字节，最后一个字符值等于缺少的字符数，其他字符填充随机数。
     * 比如明文：{1,2,3,4,5,a,b,c,d,e},缺少6个字节，则可能补全为{1,2,3,4,5,a,b,c,d,e,5,c,3,G,$,6}
     */
    public final static String AES_CBC_ISO10126PADDING = "AES/CBC/ISO10126Padding";
    /**
     * AES/CFB/NoPadding
     */
    public final static String AES_CFB_NOPADDING = "AES/CFB/NoPadding";
    /**
     * AES/CFB/PKCS5Padding
     */
    public final static String AES_CFB_PKCS5PADDING = "AES/CFB/PKCS5Padding";
    /**
     * AES/CFB/ISO10126Padding
     */
    public final static String AES_CFB_ISO10126PADDING = "AES/CFB/ISO10126Padding";
    /**
     * AES/ECB/NoPadding
     */
    public final static String AES_ECB_NOPADDING = "AES/ECB/NoPadding";
    /**
     * AES/ECB/PKCS5Padding
     */
    public final static String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";
    /**
     * AES/ECB/ISO10126Padding
     */
    public final static String AES_ECB_ISO10126PADDING = "AES/ECB/ISO10126Padding";
    /**
     * AES/OFB/NoPadding
     */
    public final static String AES_OFB_NOPADDING = "AES/OFB/NoPadding";
    /**
     * AES/OFB/PKCS5Padding
     */
    public final static String AES_OFB_PKCS5PADDING = "AES/OFB/PKCS5Padding";
    /**
     * AES/OFB/ISO10126Padding
     */
    public final static String AES_OFB_ISO10126PADDING = "AES/OFB/ISO10126Padding";
    /**
     * AES/PCBC/NoPadding
     */
    public final static String AES_PCBC_NOPADDING = "AES/PCBC/NoPadding";
    /**
     * AES/PCBC/PKCS5Padding
     */
    public final static String AES_PCBC_PKCS5PADDING = "AES/PCBC/PKCS5Padding";
    /**
     * AES/PCBC/ISO10126Padding
     */
    public final static String AES_PCBC_ISO10126PADDING = "AES/PCBC/ISO10126Padding";
}
