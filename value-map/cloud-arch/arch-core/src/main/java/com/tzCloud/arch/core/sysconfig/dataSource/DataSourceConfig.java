package com.tzCloud.arch.core.sysconfig.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.github.pagehelper.PageInterceptor;
import com.tzCloud.arch.core.sysconfig.properties.SystemProperties;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zxiao
 * @Title: DataSourceConfig
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/25 10:27
 */
@Configuration
public class DataSourceConfig {


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


    @Bean(name = "mysqlCrawler")
    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.titan-master") // application.properteis中对应属性的前缀
    public DataSource dataSource1() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDbType("mysql");
        druidDataSource.setDriverClassName(systemProperties.getDriverClassName());
        druidDataSource.setUsername(systemProperties.getUsername());
        druidDataSource.setPassword(systemProperties.getPassword());
        druidDataSource.setUrl(systemProperties.getUrl());
        initSource(druidDataSource);

        return druidDataSource;
    }

    @Bean(name = "mycatCrawler")
//    @ConfigurationProperties(prefix = "spring.datasource.db2") // application.properteis中对应属性的前缀
    public DataSource dataSource2() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDbType("mysql");
        druidDataSource.setDriverClassName(systemProperties.getDriverClassName());
        druidDataSource.setUsername(systemProperties.getMycatUsername());
        druidDataSource.setPassword(systemProperties.getMycatPassword());
        druidDataSource.setUrl(systemProperties.getMycatUrl());
        initSource(druidDataSource);

        return druidDataSource;
    }

    private void initSource(DruidDataSource druidDataSource) throws SQLException {
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
        druidDataSource.setFilters(systemProperties.getFilters());
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws SQLException {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource());
        bean.setTypeAliasesPackage("com.tzCloud.core.dao.model");

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);

        //分页插件
//        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(properties);

        //添加插件
        Interceptor[] plugins = new Interceptor[]{
                pageInterceptor
        };
        bean.setPlugins(plugins);

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath*:/mapper/**/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     *
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() throws SQLException {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource1());

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap(5);
        dsMap.put("mysqlCrawler", dataSource1());
        dsMap.put("mycatCrawler", dataSource2());

        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
