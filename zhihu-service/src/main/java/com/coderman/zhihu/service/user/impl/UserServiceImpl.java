package com.coderman.zhihu.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.zhihu.constant.AuthConstant;
import com.coderman.zhihu.constant.RedisKeyConstant;
import com.coderman.zhihu.constant.UserConstant;
import com.coderman.zhihu.dao.user.UserDAO;
import com.coderman.zhihu.model.user.UserModel;
import com.coderman.zhihu.service.redis.RedisService;
import com.coderman.zhihu.service.user.UserService;
import com.coderman.zhihu.util.JwtUtil;
import com.coderman.zhihu.util.UUIDUtil;
import com.coderman.zhihu.util.ValidUtil;
import com.coderman.zhihu.vo.user.AuthUserVO;
import com.coderman.zhihu.vo.user.LoginCaptchaVO;
import com.coderman.zhihu.vo.user.LoginParamVO;
import com.coderman.zhihu.vo.user.RegisterParamVO;
import com.wf.captcha.ArithmeticCaptcha;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author coderman
 * @Title: 用户服务实现
 * @Description: TOD
 * @date 2022/5/2722:14
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    @Resource
    private RedisService redisService;


    @Override
    public ResultVO<String> login(LoginParamVO loginParamVO) {

        // 参数校验
        String username = loginParamVO.getUsername();
        String password = loginParamVO.getPassword();
        String captchaToken = loginParamVO.getCaptchaToken();
        String captchaCode = loginParamVO.getCaptchaCode();

        if (StringUtils.isBlank(username) || StringUtils.length(username) > 16) {
            return ResultUtil.getWarn("用户名不能为空！且小于15个字符");
        }

        if (StringUtils.isBlank(password) && StringUtils.length(password) > 21) {
            return ResultUtil.getWarn("登入密码不能为空！且小于20个字符");
        }

        if (StringUtils.isBlank(captchaToken) || StringUtils.isBlank(captchaCode)) {
            return ResultUtil.getWarn("验证码不能为空");
        }

        // 验证码校验
        String redisCaptchaCode = (String) this.redisService.get(RedisKeyConstant.LOGIN_CAPTCHA_KEY + ":" + captchaToken);
        if (StringUtils.isBlank(redisCaptchaCode)) {
            return ResultUtil.getWarn("验证码已过期");
        }

        if (!StringUtils.equalsIgnoreCase(redisCaptchaCode, captchaCode)) {
            return ResultUtil.getWarn("验证码错误");
        }

        // 查询用户
        UserModel dbUser = this.userDAO.selectByUsername(username);
        if (null == dbUser) {
            return ResultUtil.getFail("用户名或密码错误！");
        }

        // 密码比对
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!StringUtils.equals(md5DigestAsHex, dbUser.getPassword())) {
            return ResultUtil.getFail("用户名或密码错误！");
        }

        // 签发jwt
        AuthUserVO authUser = new AuthUserVO();
        authUser.setUserId(dbUser.getUserId()).setUsername(dbUser.getUsername())
                .setEmail(dbUser.getEmail()).setGender(dbUser.getGender());

        String token = JwtUtil.createJWT(dbUser.getUserId().toString(), JSON.toJSONString(authUser), AuthConstant.JWT_TOKEN_EXPIRED);
        return ResultUtil.getSuccess(String.class, token);
    }

    @Override
    public ResultVO<Void> register(RegisterParamVO registerParamVO) {

        // 参数校验
        String email = registerParamVO.getEmail();
        String username = registerParamVO.getUsername();
        String password = registerParamVO.getPassword();
        String emailCode = registerParamVO.getEmailCode();

        if (StringUtils.isBlank(email)) {
            return ResultUtil.getWarn("邮箱不能为空！");
        }

        if (StringUtils.isBlank(emailCode)) {
            return ResultUtil.getWarn("邮箱验证码不能为空!");
        }

        if (!ValidUtil.isValidEmail(email)) {

            return ResultUtil.getFail("邮箱格式错误！");
        }

        if (StringUtils.isBlank(password) || StringUtils.length(password) > 21) {
            return ResultUtil.getWarn("登入密码不能为空！且小于20个字符");
        }

        if (StringUtils.isBlank(username) || StringUtils.length(username) > 16) {
            return ResultUtil.getWarn("用户名不能为空！且小于15个字符");
        }


        // 校验邮箱是否存在
        UserModel selectByEmail = this.userDAO.selectByEmail(email);
        if (null != selectByEmail) {

            throw new BusinessException("该邮箱已被注册！");
        }

        // 校验用户名是否存在
        UserModel selectByUsername = this.userDAO.selectByUsername(username);
        if (null != selectByUsername) {
            throw new BusinessException("该用户名已被注册！");
        }

        // 插入用户信息
        UserModel registerModel = new UserModel();
        registerModel.setCreateTime(new Date());
        registerModel.setEmail(email);
        registerModel.setUsername(username);
        registerModel.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        registerModel.setNickname(StringUtils.left(UUIDUtil.getNickname(), 5));
        registerModel.setGender(UserConstant.USER_GENDER_BOY);

        this.userDAO.insertSelective(registerModel);
        return ResultUtil.getSuccess();
    }

    @Override
    public ResultVO<Void> registerSendEmail(String email) {


        if (StringUtils.isBlank(email)) {
            return ResultUtil.getWarn("邮箱不能为空！");
        }

        if (!ValidUtil.isValidEmail(email)) {
            return ResultUtil.getWarn("邮箱格式不正确");
        }
        return null;
    }

    @Override
    public ResultVO<AuthUserVO> info(String token) {
        AuthUserVO authUserVO = null;

        if (StringUtils.isBlank(token)) {
            return ResultUtil.getSuccess(AuthUserVO.class, null);
        }

        try {
            Claims claims = JwtUtil.parseJWT(token);
            authUserVO = JSON.parseObject(claims.getSubject(), AuthUserVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultUtil.getSuccess(AuthUserVO.class, authUserVO);
    }

    @Override
    public ResultVO<LoginCaptchaVO> loginCaptcha(LoginCaptchaVO loginCaptchaVO) {

        String captchaToken = loginCaptchaVO.getCaptchaToken();
        ArithmeticCaptcha specCaptcha = new ArithmeticCaptcha(130, 48, 2);
        String captchaCode = specCaptcha.text();

        if (StringUtils.isBlank(captchaToken)) {
            captchaToken = UUIDUtil.getUUIDv();
        }

        LoginCaptchaVO response = new LoginCaptchaVO();
        response.setCaptchaToken(captchaToken);
        response.setCaptchaBase64(specCaptcha.toBase64());


        // 写入redis
        Boolean success = this.redisService.set(RedisKeyConstant.LOGIN_CAPTCHA_KEY + ":" + response.getCaptchaToken(), captchaCode, 60L);
        if (BooleanUtils.isNotTrue(success)) {
            throw new BusinessException("获取验证码失败");
        }

        return ResultUtil.getSuccess(LoginCaptchaVO.class, response);
    }
}
