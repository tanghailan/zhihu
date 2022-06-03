package com.coderman.zhihu.util;

import java.util.UUID;

/**
 * @author coderman
 * @Title: uuid 工具类
 * @Description: TOD
 * @date 2022/5/2812:52
 */
public class UUIDUtil {


    /**
     * 带 -  的UUID
     *
     * @return
     */
    public synchronized static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 不带 - 的UUID
     *
     * @return
     */
    public synchronized static String getUUIDv() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 不带 - 的UUID
     *
     * @return
     */
    public synchronized static String getNickname() {
        return "用户" + UUID.randomUUID().toString().replace("-", "");
    }
}
