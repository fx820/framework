package com.realfinance.core.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.realfinance.core.datasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Configuration
@Slf4j
public class DruidConfig {

    @Value("${spring.datasource.druid}")
    String druidSetting;

    //@Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*,*.html");
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = MapUtil.newHashMap();
        initParameters.put("resetEnable", "false");
        initParameters.put("allow", "");
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    @Bean
    public DruidStatInterceptor getDruidStatInterceptor(){
        return new DruidStatInterceptor();
    }

    /**
     * 动态数据源,在application -> spring.datasource.druid配置数据源文件路径
     * @return
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource() {
        Setting setting = loadDataSourcesSetting();
        Map targetDataSources = createDataSources(setting);
        return new DynamicDataSource(findDefaultDataSource(targetDataSources), targetDataSources);
    }

    private Setting loadDataSourcesSetting(){
        if(StrUtil.isBlank(druidSetting)){
            log.warn("druid dataSource setting path is blank,please configure ${spring.datasource.druid}");
        }
        Setting setting = new Setting(druidSetting,true);
        if (setting.isEmpty()){
            log.warn("druid dataSource setting isEmpty");
        }
        return setting;
    }

    private Map createDataSources(Setting setting){
        Map dataSources = MapUtil.newHashMap(true);
        setting.getGroupedMap().forEach((group,props)->{
            DruidDataSource druidDataSource = new DruidDataSource();
            Properties druidProps = new Properties();
            props.forEach((k,v) -> druidProps.put(StrUtil.addPrefixIfNot(k, "druid."), v));
            druidDataSource.configFromPropety(druidProps);
            dataSources.put(group,druidDataSource);
        });
        return dataSources;
    }

    private Object findDefaultDataSource(Map targetDataSources){
        return targetDataSources.getOrDefault(com.realfinance.core.annotation.DynamicDataSource.DEFAULT_DATA_SOURCE,targetDataSources.values().stream().findFirst().get());
    }

}
