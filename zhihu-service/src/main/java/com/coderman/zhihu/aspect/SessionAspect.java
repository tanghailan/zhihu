package com.coderman.zhihu.aspect;

import com.alibaba.fastjson.JSON;
import com.coderman.zhihu.constant.AuthConstant;
import com.coderman.zhihu.util.AuthUtil;
import com.coderman.zhihu.util.JwtUtil;
import com.coderman.zhihu.vo.user.AuthUserVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author coderman
 * @Title: 设置会话的切面
 * @Description: TOD
 * @date 2022/6/320:47
 */
@Aspect
@Component
@Slf4j
public class SessionAspect {


    public SessionAspect() {
    }

    @Pointcut("(execution(* com.coderman..controller..*(..))))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Object proceed;

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(servletRequestAttributes).getRequest();
        String authorization = request.getHeader(AuthConstant.JWT_HEADER_NAME);

        try {

            // 保存用户信息
            Claims claims = JwtUtil.parseJWT(authorization);
            AuthUserVO authUserVO = JSON.parseObject(claims.getSubject(), AuthUserVO.class);
            AuthUtil.setCurrent(authUserVO);

        } catch (Exception ignored) {

        } finally {

            proceed = point.proceed();
            AuthUtil.remove();
        }



        return proceed;
    }
}
