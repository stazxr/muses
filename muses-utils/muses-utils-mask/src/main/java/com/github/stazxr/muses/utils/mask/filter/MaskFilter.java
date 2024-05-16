package com.github.stazxr.muses.utils.mask.filter;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.github.stazxr.muses.utils.base.collection.CollectionUtils;
import com.github.stazxr.muses.utils.mask.MaskUtil;
import com.github.stazxr.muses.utils.mask.core.FieldMask;

import java.lang.reflect.Field;
import java.util.*;

/**
 * JSON 脱敏过滤实现
 *
 * @author SunTao
 * @since 2024-05-16
 */
public class MaskFilter implements ValueFilter {
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

    private Object maskValue(Field field, Object value) {
        if (field.isAnnotationPresent(FieldMask.class)) {
            FieldMask fieldMask = field.getAnnotation(FieldMask.class);
            if (value != null && fieldMask != null) {
                return doMaskValue(fieldMask, value);
            }
        }

        return value;
    }

    private Object doMaskValue(FieldMask fieldMask, Object value) {
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
