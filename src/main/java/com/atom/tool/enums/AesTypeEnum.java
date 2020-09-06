package com.atom.tool.enums;

import lombok.Getter;

/**
 * AES支持三种长度的密钥：128位，192位，256位
 * 密钥长度可以为16，24或者32字节(128，192，256位)。根据密钥的长度，算法被称为AES-128，AES-192或者AES-256
 * 平时大家所说的AES128，AES192，AES256，实际上就是指的AES算法对不同长度密钥的使用。
 * 从安全性来说：AES256 安全性最高
 * 从性能来说：AES128 性能最高
 * 本质原因是他们的加密处理轮数不同
 *
 * @author Atom
 */
public enum AesTypeEnum {
    /**
     * AES_128
     */
    AES_128(128),
    AES_192(192),
    AES_256(256);

    /**
     * AES TYPE
     */
    @Getter
    private int value;

    AesTypeEnum(int value) {
        this.value = value;
    }

}
