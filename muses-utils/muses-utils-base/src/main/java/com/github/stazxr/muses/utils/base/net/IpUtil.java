package com.github.stazxr.muses.utils.base.net;

import com.github.stazxr.muses.utils.base.math.Base36Converter;
import com.github.stazxr.muses.utils.base.math.BigIntegerLongConversion;
import lombok.extern.slf4j.Slf4j;

/**
 * IP address utility class.
 *
 * IPv4 addresses are represented as 32-bit unsigned integers [0, 2^32-1].
 * Each octet (segment) of the IP address is represented by numbers ranging from 0 to 255.
 *
 * @author SunTao
 * @since 2022-05-31
 */
@Slf4j
public class IpUtil {
    /**
     * Maximum value for IP address counter.
     */
    public static final int MAX_IP_COUNT = 17;

    private static final int IP4_PART_NUM = 4;

    /**
     * Numeric representation of IPv4 addresses ranges from 0 to 2^32-1.
     */
    private static final long IP_NUM_MAX_RANGE = (255L << 24) | (255L << 16) | (255L << 8) | 255L;

    private static final int IP4_STRING_LENGTH = 7;

    private static final long BYTE_MASK = 0xFF;

    private static final String LOG_PREFIX = "muses-utils-base[IpUtil]: ";

    /**
     * Converts a string format IPv4 address to its numeric representation.
     *
     * This method accepts an IPv4 address in dotted-decimal format (e.g., "192.168.0.1"),
     * and converts it into a long integer. The numeric representation facilitates storage
     * and comparison of IP addresses.
     *
     * @param ip string format IPv4 address
     * @return numeric representation of IPv4 address
     * @throws IllegalArgumentException if the IP address is null, blank, malformed, or contains parts out of range [0, 255]
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

                // Shift the current part left by the appropriate number of bits (24, 16, 8, 0 respectively) and merge into ipNum using bitwise OR
                ipNum |= (long) part << (8 * (3 - i));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(LOG_PREFIX + "Invalid IP part: " + ip, e);
        }

        return ipNum;
    }

    /**
     * Converts a numeric representation of IPv4 address to string format.
     *
     * This method accepts a long integer representing an IPv4 address,
     * and converts it into dotted-decimal string format.
     * For example, converts numeric value 4294967295 to string "255.255.255.255".
     *
     * @param ipNum numeric representation of IPv4 address
     * @return dotted-decimal string format of IPv4 address
     * @throws IllegalArgumentException if ipNum is out of valid range [0, 4294967295]
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
     * Generates a 7-character string representation based on the provided IP address and count.
     * If count is not provided, defaults to 0.
     *
     * @param ip IP address string
     * @return generated 7-character string
     * @throws IllegalArgumentException if count is out of valid range [0, 17]
     */
    public static String get7CharFromIpString(String ip) {
        return get7CharFromIpString(ip, 0);
    }

    /**
     * Generates a 7-character string representation based on the provided IP address and count.
     *
     * @param ip IP address string
     * @param count count value, range [0, 17]
     * @return generated 7-character string
     * @throws IllegalArgumentException if count is out of valid range [0, 17]
     */
    public static String get7CharFromIpString(String ip, int count) {
        if (count < 0 || count > MAX_IP_COUNT) {
            throw new IllegalArgumentException(LOG_PREFIX + "Count out of range [0, 17]: " + count);
        }

        // Convert IP address to long numeric value and concatenate count in high bits
        long ipNum = formatIpToNum(ip);
        ipNum |= (long) count << 32;

        // Convert IP address and count to Base36 representation ensuring result length is 7 characters
        String parseStr = Base36Converter.decimalToBase36(BigIntegerLongConversion.longToBigInteger(ipNum));
        StringBuilder builder = new StringBuilder("0000000");
        builder.replace(IP4_STRING_LENGTH - parseStr.length(), IP4_STRING_LENGTH, parseStr);
        return builder.toString().toUpperCase();
    }

    /**
     * Parses a 7-character string representation back into IP address and count.
     *
     * @param ipString string representation of IP and count (generated by get7CharFromIpString method)
     * @return [ip, count]
     * @throws IllegalArgumentException if count is out of valid range [0, 17]
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
        return new String[]{ip, String.valueOf(count)};
    }
}
