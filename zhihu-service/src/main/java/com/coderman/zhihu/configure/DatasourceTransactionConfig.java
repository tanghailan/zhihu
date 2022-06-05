package com.coderman.zhihu.configure;

import com.coderman.service.configure.BasicTransactionConfig;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * @author coderman
 * @Title: 数据源配置
 * @Description: TOD
 * @date 2022/5/2818:12
 */
@Aspect
@Configuration
public class DatasourceTransactionConfig extends BasicTransactionConfig {

    @Autowired
    private PlatformTransactionManager transactionManager;

    private final static Logger logger = LoggerFactory.getLogger(DatasourceTransactionConfig.class);
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.coderman..service..*(..))))";

    @Bean
    public TransactionInterceptor txAdvice() {
        return super.transactionInterceptor(transactionManager);
    }


    @Bean
    public Advisor txAdviceAdvisor() {
        logger.info("===============================创建txAdviceAdvisor===================================");
        return super.advisor(txAdvice(), AOP_POINTCUT_EXPRESSION);
    }

}

















