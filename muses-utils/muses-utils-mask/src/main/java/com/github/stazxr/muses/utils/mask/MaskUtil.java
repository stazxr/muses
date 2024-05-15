package com.github.stazxr.muses.utils.mask;

import com.alibaba.fastjson.JSON;
import com.github.stazxr.muses.utils.log.LogUtil;
import com.github.stazxr.muses.utils.mask.filter.MaskFilter;

/**
 * 脱敏工具类
 *
 * @author SunTao
 * @since 2024-05-15
 */
public class MaskUtil {
    private static final MaskFilter MASK_FILTER = new MaskFilter();

    private static final String MASK_LOG_LABEL = "muses-utils-mask:mask data error";

    /**
     * 数据脱敏
     *
     * @param data 待脱敏数据
     * @return 脱敏后的字符串
     */
    public static String toMaskString(Object data) {
        try {
            return JSON.toJSONString(data, MASK_FILTER);
        } catch (Exception e) {
            LogUtil.sysError(MASK_LOG_LABEL, e);
            return "";
        }
    }

    /**
     * 字符串脱敏
     *
     * @param cha  待脱敏字符串
     * @param type 脱敏类型
     * @return 脱敏后的字符串
     */
    public static String desensitized(CharSequence cha, MaskType type) {
        return type.mask(String.valueOf(cha));
    }
}
