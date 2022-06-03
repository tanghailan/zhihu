package com.coderman.zhihu.constant;

/**
 * @author coderman
 * @Title: 权限相关常量
 * @Description: TOD
 * @date 2022/5/2811:43
 */
public interface AuthConstant {


    /**
     * JWT 用户key
     */
    String JWT_HEADER_NAME = "Authorization";


    /**
     * JWT 秘钥
     */
    String JWT_SECRET_KEY = "cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";


    /**
     * JWT 过期时间 2小时
     */
    long JWT_TOKEN_EXPIRED = 1000 * 60 * 60 * 2;
}
