package com.github.stazxr.muses.utils.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String utility class providing a series of static methods for string handling.
 *
 * @author SunTao
 * @since 2024-05-05
 */
public final class StringUtil {

    /**
     * Checks if a string is empty.
     *
     * @param str The string to check
     * @return {@code true} if the string is {@code null} or empty, {@code false} otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks if a string is empty or contains only whitespace characters.
     *
     * @param str The string to check
     * @return {@code true} if the string is {@code null} or contains only whitespace characters, {@code false} otherwise
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Checks if a string is not empty and does not contain only whitespace characters.
     *
     * @param str The string to check
     * @return {@code true} if the string is not {@code null} and contains at least one non-whitespace character, {@code false} otherwise
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * Checks if any string in the string array is empty.
     *
     * @param ss The string array to check
     * @return {@code true} if any string in the array is empty, {@code false} otherwise
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
     * Checks if any string in the string array is empty or contains only whitespace characters.
     *
     * @param ss The string array to check
     * @return {@code true} if any string in the array is empty or contains only whitespace characters, {@code false} otherwise
     */
    public static boolean hasBlank(String... ss) {
        for (String s : ss) {
            if (isBlank(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts a camel case string to underscore style.
     *
     * @param camelCase The camel case string
     * @return The underscore style string
     */
    public static String camelCaseToUnderscore(String camelCase) {
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
     * Masks the first character of a string with an asterisk.
     *
     * @param data The string to mask
     * @return The masked string
     */
    public static String hideFirstChar(String data) {
        if (data != null && data.length() > 0) {
            return "*" + (data.length() < 2 ? "" : data.substring(1));
        }
        return data;
    }
}
