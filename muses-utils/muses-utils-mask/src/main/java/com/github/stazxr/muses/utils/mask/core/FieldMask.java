package com.github.stazxr.muses.utils.mask.core;

import com.github.stazxr.muses.utils.mask.MaskType;

import java.lang.annotation.*;

/**
 * 字段脱敏注解
 *
 * @author SunTao
 * @since 2024-05-15
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMask {
    /**
     * 数据脱敏类型
     *
     * @return MaskType
     */
    MaskType type() default MaskType.FIRST_MASK;
}
