package com.github.stazxr.muses.utils.base.math;

import java.math.BigInteger;

/**
 * BigIntegerLongConversion 是一个用于在 BigInteger 和 long 之间进行转换的工具类。
 *
 * @author SunTao
 * @since 2024-05-26
 */
public class BigIntegerLongConversion {
    /**
     * 将 BigInteger 转换为 long。
     * 如果 BigInteger 的值超出了 long 的范围，会抛出 ArithmeticException。
     * 
     * @param bigInteger 要转换的 BigInteger，不能为 null
     * @return 转换后的 long 值
     * @throws ArithmeticException 如果 BigInteger 的值超出了 long 的范围
     * @throws IllegalArgumentException 如果 bigInteger 为 null
     */
    public static long bigIntegerToLong(BigInteger bigInteger) {
        if (bigInteger == null) {
            throw new IllegalArgumentException("BigInteger must not be null");
        }
        if (bigInteger.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0 ||
            bigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
            throw new ArithmeticException("BigInteger out of long range");
        }
        return bigInteger.longValue();
    }

    /**
     * 将 long 转换为 BigInteger。
     * 
     * @param value 要转换的 long 值
     * @return 转换后的 BigInteger
     */
    public static BigInteger longToBigInteger(long value) {
        return BigInteger.valueOf(value);
    }
}
