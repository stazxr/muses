package com.github.stazxr.muses.utils.mask.core;

import com.github.stazxr.muses.utils.base.StringUtil;

/**
 * 脱敏策略
 *
 * @author SunTao
 * @since 2024-05-15
 */
@FunctionalInterface
public interface MaskStrategy {
    /**
     * 数据脱敏
     *
     * @param data 待脱敏的数据
     * @return 脱敏后的值
     */
    default String mask(String data) {
        if (StringUtil.isBlank(data)) {
            return data;
        }
        return maskFun(data);
    }

    /**
     * 脱敏函数
     *
     * @param data 待脱敏的数据
     * @return 脱敏后的值
     */
    String maskFun(String data);
}
