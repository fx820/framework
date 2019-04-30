package com.realfinance.core.aspectj;

import cn.hutool.core.util.ObjectUtil;
import com.realfinance.core.annotation.DynamicDataSource;
import com.realfinance.core.datasource.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源处理
 */
@Aspect
@Order(1)
@Component
@Slf4j
public class DataSourceAspect {

    @Pointcut("@annotation(com.realfinance.core.annotation.DynamicDataSource)")
    public void dsPointCut() { }

    @Before("dsPointCut()")
    public void doBefore(JoinPoint  joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DynamicDataSource dataSource = method.getAnnotation(DynamicDataSource.class);
        if (ObjectUtil.isNull(dataSource)){
            dataSource = joinPoint.getTarget().getClass().getAnnotation(DynamicDataSource.class);
        }
        if (ObjectUtil.isNotNull(dataSource)) {
            DynamicDataSourceContextHolder.setDataSoureType(dataSource.value());
            log.info("动态切换数据源，className:" + joinPoint.getTarget().getClass().getName()
                    + ",methodName:" + method.getName()
                    + ";dataSource:" + dataSource.value());
        }
    }
    @After("dsPointCut()")
    public void after(JoinPoint joinPoint) {
        DynamicDataSourceContextHolder.clearDataSoureType();
    }

}
