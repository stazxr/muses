package com.github.stazxr.muses.utils.mask;

import com.github.stazxr.muses.utils.base.StringUtil;
import com.github.stazxr.muses.utils.mask.constants.MaskConstants;
import com.github.stazxr.muses.utils.mask.core.MaskStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 脱敏类型枚举。
 * <p>
 * 该枚举定义了常见数据脱敏类型及其对应的脱敏规则，用于对敏感数据进行脱敏处理。
 * </p>
 *
 * @since 2024-05-15
 * @author SunTao
 */
public enum MaskType implements MaskStrategy {
    /**
     * 首字符脱敏，脱敏规则：若字符串长度大于等于一，则对第一个字符进行脱敏，ABC => *BC
     */
    FIRST_MASK(StringUtil::hideFirstChar),

    /**
     * 密码，脱敏规则：123456 => ******; 123456789 => ******
     */
    PASSWORD(data -> MaskConstants.PASSWORD_MASK),

    /**
     * 手机号，脱敏规则：隐藏手机号中间四位
     */
    MOBILE_NUMBER(data -> {
        if (data.length() == MaskConstants.MOBILE_NUMBER_LEN) {
            // 标准手机号
            return data.substring(0, 3) + MaskConstants.OTHER_MASK + data.substring(7);
        } else {
            // 非标准手机号
            return StringUtil.hideFirstChar(data);
        }
    }),

    /**
     * 座机号，脱敏规则：隐藏中间部分或尾缀
     */
    LANDLINE_NUMBER(data -> {
        int landlineNumPart = 3;
        int minLandlineNumLen = 6;
        if (data.length() > minLandlineNumLen) {
            // 标准做记号 000 + 000 + 0000 或 000-000-0000
            String[] landParts = data.split("-");
            if (landParts.length == landlineNumPart) {
                return landParts[0] + "-" + MaskConstants.OTHER_MASK + "-" + landParts[2];
            } else {
                return data.substring(0, data.length() - 4) + MaskConstants.OTHER_MASK;
            }
        } else {
            // 非标准座机号
            return StringUtil.hideFirstChar(data);
        }
    }),

    /**
     * 用户名，脱敏规则：隐藏中甲部分
     */
    USERNAME(data -> {
        int minUsernameLen = 3;
        if (data.length() > minUsernameLen) {
            return data.substring(0, 2) + MaskConstants.OTHER_MASK + data.substring(data.length() - 1);
        } else {
            return StringUtil.hideFirstChar(data);
        }
    }),

    /**
     * 邮箱，强脱敏，脱敏规则：隐藏用户名部分
     */
    EMAIL(data -> {
        int atIndex = data.indexOf("@");
        if (atIndex != -1) {
            // 标准邮箱
            return MaskConstants.OTHER_MASK + data.substring(atIndex);
        } else {
            // 邮箱地址有误，不需要脱敏
            return data;
        }
    }),

    /**
     * 邮箱，低脱敏，脱敏规则：对用户名部分二次脱敏
     */
    EMAIL_WEAK(data -> {
        int atIndex = data.indexOf("@");
        if (atIndex != -1) {
            // 标准邮箱
            String maskUsername = USERNAME.maskFun(data.substring(0, atIndex));
            return maskUsername + data.substring(atIndex);
        } else {
            // 邮箱地址有误，不需要脱敏
            return data;
        }
    }),

    /**
     * 地址，脱敏规则：
     */
    ADDRESS(data -> {
        // TODO 暂不处理
        return data;
    }),

    /**
     * 身份证号，脱敏规则：对标准身份证好进行脱敏处理，隐藏个人信息
     */
    ID_CARD(data -> {
        if (data.length() == MaskConstants.ID_CARD_LEN) {
            return data.substring(0, 6) + MaskConstants.ID_CARD_MASK + data.substring(14);
        }
        return data;
    }),

    /**
     * 企鹅号，脱敏规则：
     */
    QQ_NUMBER(data -> {
        if (data.length() >= MaskConstants.MIN_QQ_LEN) {
            return data.substring(0, 2) + MaskConstants.OTHER_MASK + data.substring(data.length() - 2);
        }
        return StringUtil.hideFirstChar(data);
    });

    private static final Logger log = LoggerFactory.getLogger(MaskType.class);

    private final MaskStrategy strategy;

    /**
     * 构造一个脱敏类型枚举对象。
     *
     * @param strategy 脱敏策略
     */
    MaskType(MaskStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 对给定数据执行脱敏操作。
     *
     * @param data 待脱敏的数据
     * @return 脱敏后的值
     */
    @Override
    public String maskFun(String data) {
        try {
            return strategy.maskFun(data);
        } catch (Exception e) {
            log.error("data mask error", e);
            return data;
        }
    }
}
