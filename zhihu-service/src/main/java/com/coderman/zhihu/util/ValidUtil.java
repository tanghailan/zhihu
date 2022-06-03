package com.coderman.zhihu.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author coderman
 * @Title: 校验工具类
 * @Description: TOD
 * @date 2022/5/2812:57
 */
public class ValidUtil {

    /**
     * 校验邮箱
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
        }
        return false;
    }


    /**
     * 校验手机号
     *
     * @param phone 手机号
     * @return boolean true:是  false:否
     */
    public static boolean isPhone(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return Pattern.matches("((\\\\+86|0086)?\\\\s*)((134[0-8]\\\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\\\d{8})|(14(0|1|4)0\\\\d{7})|(1740([0-5]|[6-9]|[10-12])\\\\d{7}))" ,phone);
    }


}

