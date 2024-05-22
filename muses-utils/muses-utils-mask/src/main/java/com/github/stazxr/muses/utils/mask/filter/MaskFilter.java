package com.github.stazxr.muses.utils.mask.filter;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.github.stazxr.muses.utils.base.collection.CollectionUtils;
import com.github.stazxr.muses.utils.mask.MaskUtil;
import com.github.stazxr.muses.utils.mask.core.FieldMask;

import java.lang.reflect.Field;
import java.util.*;

/**
 * JSON 脱敏过滤实现。
 * <p>
 * 该类实现了 JSON 值过滤器接口 {@link com.alibaba.fastjson.serializer.ValueFilter}，用于对 JSON 中的敏感数据进行脱敏处理。
 * </p>
 *
 * @since 2024-05-16
 * @author SunTao
 */
public class MaskFilter implements ValueFilter {
    /**
     * 处理 JSON 中的字段值。
     *
     * @param obj   对象
     * @param name  字段名
     * @param value 字段值
     * @return 处理后的字段值
     */
    @Override
    public Object process(Object obj, String name, Object value) {
        Field field = null;
        Class<?> objClazz = obj.getClass();
        while (!Object.class.getName().equalsIgnoreCase(objClazz.getName())) {
            try {
                field = objClazz.getDeclaredField(name);
                break;
            } catch (NoSuchFieldException e) {
                objClazz = objClazz.getSuperclass();
            }
        }

        if (field != null && field.getName().equals(name)) {
            try {
                return maskValue(field, value);
            } catch (Exception e) {
                return value;
            }
        }

        return value;
    }

    /**
     * 对字段值进行脱敏处理。
     *
     * @param field 字段
     * @param value 字段值
     * @return 脱敏后的字段值
     */
    private Object maskValue(Field field, Object value) {
        if (field.isAnnotationPresent(FieldMask.class)) {
            FieldMask fieldMask = field.getAnnotation(FieldMask.class);
            if (value != null && fieldMask != null) {
                return doMaskValue(fieldMask, value);
            }
        }

        return value;
    }

    /**
     * 执行字段值的脱敏操作。
     *
     * @param fieldMask 字段脱敏注解
     * @param value     字段值
     * @return 脱敏后的字段值
     */
    protected Object doMaskValue(FieldMask fieldMask, Object value) {
        if (value instanceof String) {
            return MaskUtil.desensitized(String.valueOf(value), fieldMask.type());
        }
        if (value instanceof Collection) {
            List<Object> maskList = new ArrayList<>();
            ((Collection<?>) value).forEach(k -> {
                if (k instanceof String) {
                    maskList.add(MaskUtil.desensitized(String.valueOf(k), fieldMask.type()));
                } else {
                    maskList.add(doMaskValue(fieldMask, k));
                }
            });
            return maskList;
        }
        if (value instanceof Map) {
            Map<?, ?> newMap = (Map<?, ?>) value;
            Map<Object, Object> maskMap = new HashMap<>(CollectionUtils.mapSize(newMap.size()));
            newMap.forEach((k, v) -> {
                if (v instanceof String) {
                    maskMap.put(k, MaskUtil.desensitized((String) v, fieldMask.type()));
                } else {
                    maskMap.put(k, doMaskValue(fieldMask, v));
                }
            });
            return maskMap;
        }

        return value;
    }
}
