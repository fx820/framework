package com.realfinance.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Arrays;
import java.util.Properties;

/**
 * 事务配置
 */
@Configuration
public class TransactionalConfig {
    private static final String PROPAGATION_REQUIRED = "PROPAGATION_REQUIRED,-Throwable";
    private static final String PROPAGATION_REQUIRED_READ = "PROPAGATION_REQUIRED,-Throwable,readOnly";
    private static final String[] REQUIRED_RULE_TRANSACTION = {"insert*", "add*", "update*", "del*", "create*"};
    private static final String[] READ_RULE_TRANSACTION = {"select*", "get*", "count*", "find*"};

    @Bean(name = "transactionInterceptor")
    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager platformTransactionManager) {
        TransactionInterceptor interceptor = new TransactionInterceptor();
        Properties properties = new Properties();
        Arrays.stream(REQUIRED_RULE_TRANSACTION).forEach(e -> properties.setProperty(e, PROPAGATION_REQUIRED));
        Arrays.stream(READ_RULE_TRANSACTION).forEach(e -> properties.setProperty(e, PROPAGATION_REQUIRED_READ));
        interceptor.setTransactionManager(platformTransactionManager);
        interceptor.setTransactionAttributes(properties);
        return interceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator getBeanNameAutoProxyCreator() {
        BeanNameAutoProxyCreator proxyCreator = new BeanNameAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        proxyCreator.setBeanNames("*ServiceImpl", "*Controller");
        proxyCreator.setInterceptorNames("transactionInterceptor");
        return proxyCreator;
    }


}
