package com.winback.arch.core.sysconfig;

import com.winback.arch.core.sysconfig.properties.SystemProperties;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig implements InitializingBean, DisposableBean {
    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Autowired
    private SystemProperties systemProperties;
    /**
     * 注册DruidServlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        return servletRegistrationBean;
    }

    /**
     * 注册DruidFilter拦截
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean druidFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<String, String>();
        //设置忽略请求
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    /**
     * 配置DataSource
     *
     * @return
     * @throws SQLException
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    @Primary
    public DataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDbType("mysql");
        druidDataSource.setDriverClassName(systemProperties.getDriverClassName());
        druidDataSource.setUsername(systemProperties.getUsername());
        druidDataSource.setPassword(systemProperties.getPassword());
        druidDataSource.setUrl(systemProperties.getUrl());
        druidDataSource.setFilters("stat,wall");
        druidDataSource.setUseGlobalDataSourceStat(true);

        //configuration
        druidDataSource.setInitialSize(Integer.valueOf(systemProperties.getInitialSize()));
        druidDataSource.setMinIdle(Integer.valueOf(systemProperties.getMinIdle()));
        druidDataSource.setMaxActive(Integer.valueOf(systemProperties.getMaxActive()));
        druidDataSource.setMaxWait(Integer.valueOf(systemProperties.getMaxWait()));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(systemProperties.getTimeBetweenEvictionRunsMillis()));
        druidDataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(systemProperties.getMinEvictableIdleTimeMillis()));
        druidDataSource.setValidationQuery(systemProperties.getValidationQuery());
        druidDataSource.setTestWhileIdle(Boolean.valueOf(systemProperties.getTestWhileIdle()));
        druidDataSource.setTestOnBorrow(Boolean.valueOf(systemProperties.getTestOnBorrow()));
        druidDataSource.setTestOnReturn(Boolean.valueOf(systemProperties.getTestOnReturn()));
        try {
            druidDataSource.setFilters(systemProperties.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }
}
