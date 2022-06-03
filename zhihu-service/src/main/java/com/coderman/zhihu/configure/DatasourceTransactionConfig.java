package com.coderman.zhihu.configure;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.coderman.service.configure.BasicTransactionConfig;
import org.aopalliance.aop.Advice;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

/**
 * @author coderman
 * @Title: 数据源配置
 * @Description: TOD
 * @date 2022/5/2818:12
 */
@Configuration
@Aspect
public class DatasourceTransactionConfig extends BasicTransactionConfig {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.coderman..service..*(..))))";

    @Bean(value = "zhihuDatasource")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(value = "zhihuJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier(value = "zhihuDatasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(value = "zhihuTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier(value = "zhihuDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(value = "zhihuTxAdvice")
    public TransactionInterceptor transactionInterceptor(@Qualifier(value = "zhihuTransactionManager") PlatformTransactionManager transactionManager) {
        return super.transactionInterceptor(transactionManager);
    }

    @Bean(value = "zhihuTxAdvisor")
    public Advisor advisor(@Qualifier(value = "zhihuTxAdvice") Advice advisor) {

        return super.advisor(advisor,AOP_POINTCUT_EXPRESSION);
    }

}

















