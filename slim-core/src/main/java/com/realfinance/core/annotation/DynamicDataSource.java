package com.realfinance.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义多数据源切换注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicDataSource {

    String DEFAULT_DATA_SOURCE = "MASTER";
    /**
     * 切换数据源名称
     */
    String value() default DEFAULT_DATA_SOURCE;
}
