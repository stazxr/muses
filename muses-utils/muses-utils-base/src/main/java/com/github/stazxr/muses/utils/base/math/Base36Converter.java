package com.github.stazxr.muses.utils.base.math;

import java.math.BigInteger;

/**
 * Base36Converter 是一个用于在 36 进制字符串和十进制数值之间进行转换的工具类。
 * 36 进制包括数字 0-9 和字母 A-Z，共计 36 个字符。
 *
 * @author SunTao
 * @since 2024-05-26
 */
public class Base36Converter {
    /**
     * 将 36 进制字符串转换为十进制数字。
     * 
     * @param base36 36 进制字符串，不能为 null 或空字符串
     * @return 对应的十进制数字
     * @throws NumberFormatException 如果 base36 不是有效的 36 进制字符串
     */
    public static BigInteger base36ToDecimal(String base36) {
        if (base36 == null || base36.isEmpty()) {
            throw new IllegalArgumentException("Input string must not be null or empty");
        }
        return new BigInteger(base36, 36);
    }

    /**
     * 将十进制数字转换为 36 进制字符串。
     * 
     * @param decimal 十进制数字，不能为 null
     * @return 对应的 36 进制字符串，使用大写字母表示
     * @throws IllegalArgumentException 如果 decimal 为 null
     */
    public static String decimalToBase36(BigInteger decimal) {
        if (decimal == null) {
            throw new IllegalArgumentException("Input decimal must not be null");
        }
        return decimal.toString(36).toUpperCase();
    }
}
