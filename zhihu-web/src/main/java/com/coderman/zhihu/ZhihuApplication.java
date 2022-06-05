package com.coderman.zhihu;

import com.coderman.api.constant.CommonConstant;
import com.coderman.service.aspect.ResultAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author coderman
 * @Title: 启动类
 * @Description: TOD
 * @date 2022/5/2720:57
 */
@SpringBootApplication
@ComponentScan(basePackages = {CommonConstant.BASE_PACKAGE}, excludeFilters = {@ComponentScan.Filter(value = {ResultAspect.class}, type = FilterType.ASSIGNABLE_TYPE)})
@MapperScan(basePackages = {CommonConstant.BASE_DAO_PACKAGE})
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class ZhihuApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhihuApplication.class, args);
    }
}
