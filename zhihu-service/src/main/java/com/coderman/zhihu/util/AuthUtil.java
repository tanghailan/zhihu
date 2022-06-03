package com.coderman.zhihu.util;

import com.coderman.zhihu.vo.user.AuthUserVO;

/**
 * @author coderman
 * @Title: 用户会话工具类
 * @Description: TOD
 * @date 2022/5/2812:34
 */
public class AuthUtil {

    private static final ThreadLocal<AuthUserVO> authThreadLocal = new ThreadLocal<>();

    public static void setCurrent(AuthUserVO authUserVO) {
        assert authUserVO != null;
        authThreadLocal.set(authUserVO);
    }


    public static AuthUserVO getCurrent() {
        return authThreadLocal.get();
    }

    public static void remove() {
        authThreadLocal.remove();
    }
}
