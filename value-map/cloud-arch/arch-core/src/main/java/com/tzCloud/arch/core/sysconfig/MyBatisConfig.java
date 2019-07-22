//package com.tzCloud.arch.core.sysconfig;
//
//import com.tzCloud.arch.core.sysconfig.properties.SystemProperties;
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.alibaba.druid.support.http.WebStatFilter;
//import com.github.pagehelper.PageInterceptor;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.*;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.TransactionManagementConfigurer;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * MyBatis基础配置
// */
//@Configuration
//@EnableTransactionManagement
//public class MyBatisConfig implements TransactionManagementConfigurer {
//
//
//    @Autowired
//    private SystemProperties systemProperties;
//    /**
//     * 注册DruidServlet
//     *
//     * @return
//     */
//    @Bean
//    public ServletRegistrationBean druidServletRegistrationBean() {
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
//        servletRegistrationBean.setServlet(new StatViewServlet());
//        servletRegistrationBean.addUrlMappings("/druid/*");
//        return servletRegistrationBean;
//    }
//
//    /**
//     * 注册DruidFilter拦截
//     *
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean druidFilterRegistrationBean() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(new WebStatFilter());
//        Map<String, String> initParams = new HashMap<String, String>();
//        //设置忽略请求
//        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
//        filterRegistrationBean.setInitParameters(initParams);
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//    }
//
//    /**
//     * 配置DataSource
//     *
//     * @return
//     * @throws SQLException
//     */
//    @Bean(initMethod = "init", destroyMethod = "close")
//    @Primary
//    @DependsOn("systemProperties")
//    public DataSource dataSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDbType("mysql");
//        druidDataSource.setDriverClassName(systemProperties.getDriverClassName());
//        druidDataSource.setUsername(systemProperties.getUsername());
//        druidDataSource.setPassword(systemProperties.getPassword());
//        druidDataSource.setUrl(systemProperties.getUrl());
//        druidDataSource.setUseGlobalDataSourceStat(true);
//
//        //configuration
//        druidDataSource.setInitialSize(Integer.valueOf(systemProperties.getInitialSize()));
//        druidDataSource.setMinIdle(Integer.valueOf(systemProperties.getMinIdle()));
//        druidDataSource.setMaxActive(Integer.valueOf(systemProperties.getMaxActive()));
//        druidDataSource.setMaxWait(Integer.valueOf(systemProperties.getMaxWait()));
//        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(systemProperties.getTimeBetweenEvictionRunsMillis()));
//        druidDataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(systemProperties.getMinEvictableIdleTimeMillis()));
//        druidDataSource.setValidationQuery(systemProperties.getValidationQuery());
//        druidDataSource.setTestWhileIdle(Boolean.valueOf(systemProperties.getTestWhileIdle()));
//        druidDataSource.setTestOnBorrow(Boolean.valueOf(systemProperties.getTestOnBorrow()));
//        druidDataSource.setTestOnReturn(Boolean.valueOf(systemProperties.getTestOnReturn()));
//        try {
//            druidDataSource.setFilters(systemProperties.getFilters());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return druidDataSource;
//    }
//
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactoryBean() {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource());
//        bean.setTypeAliasesPackage("com.tzCloud.core.dao.model");
//
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setMapUnderscoreToCamelCase(true);
//        bean.setConfiguration(configuration);
//
//        //分页插件
////        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("returnPageInfo", "check");
//        properties.setProperty("params", "count=countSql");
//        PageInterceptor pageInterceptor = new PageInterceptor();
//        pageInterceptor.setProperties(properties);
//
//        //添加插件
//        bean.setPlugins(new Interceptor[]{pageInterceptor});
//
//        //添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            bean.setMapperLocations(resolver.getResources("classpath*:/mapper/**/*.xml"));
//            return bean.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    @Bean
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager(){
//            return new DataSourceTransactionManager(dataSource());
//    }
//}