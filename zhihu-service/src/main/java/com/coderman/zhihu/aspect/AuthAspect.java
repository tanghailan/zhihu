package com.coderman.zhihu.aspect;

import com.alibaba.fastjson.JSON;
import com.coderman.api.constant.ResultConstant;
import com.coderman.zhihu.constant.AuthConstant;
import com.coderman.zhihu.util.AuthUtil;
import com.coderman.zhihu.util.JwtUtil;
import com.coderman.zhihu.vo.user.AuthUserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author coderman
 * @Title: 权限校验接口
 * @Description: TOD
 * @date 2022/5/2812:06
 */
@Aspect
@Component
@Slf4j
public class AuthAspect {


    private static final Set<String> unCheckPathSet = new HashSet<>();

    @PostConstruct
    public void init() {
        // 用户登入
        unCheckPathSet.add("/user/login");
        // 用户信息
        unCheckPathSet.add("/user/info");
        // 用户注册
        unCheckPathSet.add("/user/register");
        // 邮箱验证
        unCheckPathSet.add("/user/register/send/email");
        // 登入验证码
        unCheckPathSet.add("/user/login/captcha");

        // test
        unCheckPathSet.add("/question/page");
    }


    public AuthAspect() {
    }

    @Pointcut("(execution(* com.coderman..controller..*(..))))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(servletRequestAttributes).getRequest();
        HttpServletResponse response = Objects.requireNonNull(servletRequestAttributes).getResponse();

        assert response != null;

        // 白名单请求
        String path = request.getServletPath();
        if (unCheckPathSet.contains(path)) {

            return point.proceed();
        }

        // 未登入
        String authorization = request.getHeader(AuthConstant.JWT_HEADER_NAME);
        if (StringUtils.isBlank(authorization)) {

            log.error("用户未登入!");
            response.setStatus(ResultConstant.RESULT_CODE_401);
            return null;
        }


        String subject;

        try {

            // 校验token
            Claims claims = JwtUtil.parseJWT(authorization);
            subject = claims.getSubject();
        } catch (SignatureException e) {

            log.error("token令牌格式错误!");
            response.setStatus(ResultConstant.RESULT_CODE_401);
            return null;
        } catch (ExpiredJwtException e) {

            log.error("token令牌过期!");
            response.setStatus(ResultConstant.RESULT_CODE_401);
            return null;
        } catch (Exception exception) {
            log.error("解析错误:{}", exception.getMessage());
            response.setStatus(ResultConstant.RESULT_CODE_401);
            return null;
        }

        if (StringUtils.isEmpty(subject)) {

            log.error("用户信息不存在!");
            response.setStatus(ResultConstant.RESULT_CODE_401);
            return null;
        }

        // 保存用户信息
        AuthUserVO authUserVO = JSON.parseObject(subject, AuthUserVO.class);
        AuthUtil.setCurrent(authUserVO);

        // 执行目标方法
        Object proceed = point.proceed();


        // 请求会话信息
        AuthUtil.remove();
        return proceed;

    }

}
