package com.coderman.zhihu.service.user;

import com.coderman.api.vo.ResultVO;
import com.coderman.zhihu.vo.user.AuthUserVO;
import com.coderman.zhihu.vo.user.LoginCaptchaVO;
import com.coderman.zhihu.vo.user.LoginParamVO;
import com.coderman.zhihu.vo.user.RegisterParamVO;

/**
 * @author coderman
 * @Title: 用户服务
 * @Description: TOD
 * @date 2022/5/2722:12
 */
public interface UserService {

    /**
     * 用户登入
     *
     * @param loginParamVO 登入参数
     * @return
     */
    ResultVO<String> login(LoginParamVO loginParamVO);


    /**
     * 用户注册
     * @param registerParamVO 注册参数
     * @return
     */
    ResultVO<Void> register(RegisterParamVO registerParamVO);


    /**
     * 用户注册发送邮箱验证码
     *
     * @param registerEmail 邮箱
     * @return
     */
    ResultVO<Void> registerSendEmail(String registerEmail);


    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    ResultVO<AuthUserVO> info(String token);


    /**
     * 登入验证码
     *
     * @param loginCaptchaVO
     * @return
     */
    ResultVO<LoginCaptchaVO> loginCaptcha(LoginCaptchaVO loginCaptchaVO);
}
