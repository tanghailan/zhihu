package com.coderman.zhihu.controller.user;

import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import com.coderman.zhihu.service.user.UserService;
import com.coderman.zhihu.vo.user.AuthUserVO;
import com.coderman.zhihu.vo.user.LoginCaptchaVO;
import com.coderman.zhihu.vo.user.LoginParamVO;
import com.coderman.zhihu.vo.user.RegisterParamVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author coderman
 * @Title: 用户接口
 * @Description: 用户相关接口
 * @date 2022/5/2722:03
 */
@Api(tags = {"用户相关接口"})
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "用户登入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = SwaggerConstant.PARAM_FORM, dataType = SwaggerConstant.DATA_STRING, value = "用户名"),
            @ApiImplicitParam(name = "password", paramType = SwaggerConstant.PARAM_FORM, dataType = SwaggerConstant.DATA_STRING, value = "密码"),
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    @PostMapping(value = "/login")
    public ResultVO<String> login(@ApiIgnore LoginParamVO loginParamVO) {
        return this.userService.login(loginParamVO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "用户信息")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    @GetMapping(value = "/info")
    public ResultVO<AuthUserVO> info(@RequestHeader(value = "Authorization", required = false) String token) {
        return this.userService.info(token);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", paramType = SwaggerConstant.PARAM_QUERY, dataType = SwaggerConstant.DATA_STRING, value = "用户名"),
            @ApiImplicitParam(name = "password", paramType = SwaggerConstant.PARAM_QUERY, dataType = SwaggerConstant.DATA_STRING, value = "密码"),
            @ApiImplicitParam(name = "email", paramType = SwaggerConstant.PARAM_QUERY, dataType = SwaggerConstant.DATA_STRING, value = "邮箱"),
            @ApiImplicitParam(name = "emailCode", paramType = SwaggerConstant.PARAM_QUERY, dataType = SwaggerConstant.DATA_STRING, value = "邮箱验证码"),
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    @PostMapping(value = "/register")
    public ResultVO<Void> register(@ApiIgnore RegisterParamVO registerParamVO) {
        return this.userService.register(registerParamVO);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "用户注册发送邮验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = SwaggerConstant.PARAM_QUERY, dataType = SwaggerConstant.DATA_STRING, value = "邮箱"),
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    @PostMapping(value = "/register/send/email")
    public ResultVO<Void> registerSendEmail(String email) {
        return this.userService.registerSendEmail(email);
    }



    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "登入验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "captchaToken", paramType = SwaggerConstant.PARAM_QUERY, dataType = SwaggerConstant.DATA_STRING, value = "邮箱"),
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "LoginCaptchaVO",value = {"captchaBase64","captchaToken"})
    })
    @GetMapping(value = "/login/captcha")
    public ResultVO<LoginCaptchaVO> loginCaptcha(LoginCaptchaVO loginCaptchaVO) {
        return this.userService.loginCaptcha(loginCaptchaVO);
    }

}
























