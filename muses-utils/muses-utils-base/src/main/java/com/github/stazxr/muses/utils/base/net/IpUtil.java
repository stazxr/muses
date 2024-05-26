package com.github.stazxr.muses.utils.base.net;

import com.github.stazxr.muses.utils.base.math.Base36Converter;
import com.github.stazxr.muses.utils.base.math.BigIntegerLongConversion;
import lombok.extern.slf4j.Slf4j;

/**
 * IP地址相关实用工具类。
 *
 * @author SunTao
 * @since 2022-05-31
 */
@Slf4j
public class IpUtil {
    /**
     * IP地址计数器最大值
     */
    public static final int MAX_IP_COUNT = 17;

    private static final int IP4_PART_NUM = 4;

    /**
     * IPv4地址的数值表示是一个32位的无符号整数 [0, 2^32-1]
     */
    private static final long IP_NUM_MAX_RANGE = (255L << 24) | (255L << 16) | (255L << 8) | 255L;

    private static final int IP4_STRING_LENGTH = 7;

    private static final long BYTE_MASK = 0xFF;

    private static final String LOG_PREFIX = "muses-utils-base[IpUtil]: ";

    /**
     * 将字符串格式的IPv4地址转换为其数值表示。
     *
     * <p>此方法接受一个以点分十进制形式表示的IPv4地址（例如 "192.168.0.1"），
     * 并将其转换为一个长整型数值。数值表示形式可以更方便地进行IP地址的存储和比较。
     *
     * @param ip 字符串格式的IPv4地址
     * @return IPv4地址的数值表示
     * @throws IllegalArgumentException 如果IP地址为null、空白、格式不正确或包含超出范围[0, 255]的部分
     */
    public static long formatIpToNum(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            throw new IllegalArgumentException(LOG_PREFIX + "IP is null or blank");
        }

        String[] ipArray = ip.split("\\.");
        if (ipArray.length != IP4_PART_NUM) {
            throw new IllegalArgumentException(LOG_PREFIX + "Invalid IP format: " + ip);
        }

        long ipNum = 0L;
        try {
            for (int i = 0; i < ipArray.length; i++) {
                int part = Integer.parseInt(ipArray[i]);
                if (part < 0 || part > 255) {
                    throw new IllegalArgumentException(LOG_PREFIX + "IP part out of range [0-255]: " + ipArray[i]);
                }

                // 将当前部分左移适当的位数（四部分依次左移 24 16 8 0 位），并用按位或操作合并到 ipNum 中
                ipNum |= (long) part << (8 * (3 - i));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(LOG_PREFIX + "Invalid IP part: " + ip, e);
        }

        return ipNum;
    }

    /**
     * 将数值表示的IPv4地址转换为字符串格式。
     *
     * <p>此方法接受一个长整型数值表示的IPv4地址，并将其转换为点分十进制形式的字符串表示。
     * 例如，将数值4294967295转换为字符串"255.255.255.255"。
     *
     * @param ipNum 数值表示的IPv4地址
     * @return 点分十进制形式的IPv4地址
     * @throws IllegalArgumentException 如果ipNum超出合法范围[0, 4294967295]
     */
    public static String parseNumToIp(long ipNum) {
        if (ipNum < 0 || ipNum > IP_NUM_MAX_RANGE) {
            throw new IllegalArgumentException(LOG_PREFIX + String.format("IP num out of range [0, %d]: %d", IP_NUM_MAX_RANGE, ipNum));
        }

        return String.valueOf((ipNum >> 24) & BYTE_MASK) + '.' +
                ((ipNum >> 16) & BYTE_MASK) + '.' +
                ((ipNum >> 8) & BYTE_MASK) + '.' +
                (ipNum & BYTE_MASK);
    }

    /**
     * 根据指定的IP地址和计数值生成一个7位长度的字符串表示。
     * 如果计数值未提供，则默认为0。
     *
     * @param ip    IP地址字符串
     * @return 生成的7位长度的字符串
     * @throws IllegalArgumentException 如果计数值超出有效范围 [0, 17]
     */
    public static String get7CharFromIpString(String ip) {
        return get7CharFromIpString(ip, 0);
    }

    /**
     * 根据指定的IP地址和计数值生成一个7位长度的字符串表示。
     *
     * @param ip    IP地址字符串
     * @param count 计数值，范围在 [0, 17]
     * @return 生成的7位长度的字符串
     * @throws IllegalArgumentException 如果计数值超出有效范围 [0, 17]
     */
    public static String get7CharFromIpString(String ip, int count) {
        if (count < 0 || count > MAX_IP_COUNT) {
            throw new IllegalArgumentException(LOG_PREFIX + "Count out of range [0, 17]: " + count);
        }

        // 将IP地址转换为长整型数值，并在高位拼接计数值
        long ipNum = formatIpToNum(ip);
        ipNum |= (long) count << 32;

        // 将IP地址和计数器转换为36进制表示，并保证结果长度为7位
        String parseStr = Base36Converter.decimalToBase36(BigIntegerLongConversion.longToBigInteger(ipNum));
        StringBuilder builder = new StringBuilder("0000000");
        builder.replace(IP4_STRING_LENGTH - parseStr.length(), IP4_STRING_LENGTH, parseStr);
        return builder.toString().toUpperCase();
    }

    /**
     * 解析7位长度的字符串为IP地址和计数值
     *
     * @param ipString IP和计数器的字符串标识(get7CharFromIpString 方法生成)
     * @return [ip, count]
     * @throws IllegalArgumentException 如果计数值超出有效范围 [0, 17]
     */
    public static String[] parse7CharToIpCountAry(String ipString) {
        if (ipString.length() != IP4_STRING_LENGTH) {
            throw new IllegalArgumentException(LOG_PREFIX + "Ip string length out of range [7]: " + ipString);
        }

        long ipCountNum = BigIntegerLongConversion.bigIntegerToLong(Base36Converter.base36ToDecimal(ipString));
        int count = (int) ((ipCountNum >> 32) & BYTE_MASK);
        String ip = String.valueOf((ipCountNum >> 24) & BYTE_MASK) + '.' +
                ((ipCountNum >> 16) & BYTE_MASK) + '.' +
                ((ipCountNum >> 8) & BYTE_MASK) + '.' +
                (ipCountNum & BYTE_MASK);
        return new String[] {ip, String.valueOf(count)};
    }
}
