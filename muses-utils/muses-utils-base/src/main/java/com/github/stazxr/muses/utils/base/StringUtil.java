package com.github.stazxr.muses.utils.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类，提供了一系列字符串处理的静态方法。
 * <p>
 * 该类包含以下方法：
 * <ul>
 *     <li>{@link #isEmpty(String)}: 判断字符串是否为空</li>
 *     <li>{@link #isBlank(String)}: 判断字符串是否为空或者只包含空白字符</li>
 *     <li>{@link #isNotBlank(String)}: 判断字符串是否不为空且不只包含空白字符</li>
 *     <li>{@link #hasEmpty(String...)}: 判断字符串数组中是否存在为空的字符串</li>
 *     <li>{@link #hasBlank(String...)}: 判断字符串数组中是否存在为空或者只包含空白字符的字符串</li>
 *     <li>{@link #cameCaseToSubLine(String)}: 将驼峰风格的字符串转换为下划线风格</li>
 *     <li>{@link #hideFirstChar(String)}: 隐藏字符串的首个字符，用星号替换</li>
 * </ul>
 * </p>
 *
 * @author SunTao
 * @since 2024-05-05
 */
public final class StringUtil {

    /**
     * 判断字符串是否为空。
     *
     * @param str 待判断的字符串
     * @return 如果字符串为 {@code null} 或者空字符串则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    /**
     * 判断字符串是否为空或者只包含空白字符。
     * <p>
     * 字符串为空或者只包含空白字符时返回 {@code true}，否则返回 {@code false}。
     * </p>
     *
     * @param str 待判断的字符串
     * @return 如果字符串为 {@code null} 或者只包含空白字符则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isBlank(String str) {
        return null == str || "".equals(str.trim());
    }

    /**
     * 判断字符串是否不为空且不只包含空白字符。
     *
     * @param str 待判断的字符串
     * @return 如果字符串不为空且不只包含空白字符则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断字符串数组中是否存在为空的字符串。
     *
     * @param ss 字符串数组
     * @return 如果字符串数组中存在为空的字符串则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean hasEmpty(String... ss) {
        for (String s : ss) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串数组中是否存在为空或者只包含空白字符的字符串。
     *
     * @param ss 字符串数组
     * @return 如果字符串数组中存在为空或者只包含空白字符的字符串则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean hasBlank(String... ss) {
        for (String s : ss) {
            if (isEmpty(s.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将驼峰风格的字符串转换为下划线风格。
     *
     * @param camelCase 驼峰风格的字符串
     * @return 下划线风格的字符串
     */
    public static String cameCaseToSubLine(String camelCase) {
        String az = "[A-Z]";
        Pattern pattern = Pattern.compile(az);
        Matcher matcher = pattern.matcher(camelCase);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 隐藏字符串的首个字符，用星号替换。
     *
     * @param data 待脱敏的字符串
     * @return 脱敏后的字符串
     */
    public static String hideFirstChar(String data) {
        if (data != null && data.length() > 0) {
            return "*" + (data.length() < 2 ? "" : data.substring(1));
        }
        return data;
    }
}