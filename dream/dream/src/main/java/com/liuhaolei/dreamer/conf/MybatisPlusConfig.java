package com.liuhaolei.dreamer.conf;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

@Configuration
@MapperScan("com.liuhaolei.dreamer.mapper*")
public class MybatisPlusConfig {
	
	/**
	 * 数据库连接池
	 * @return
	 */
	@Bean(name="dataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource(){
        return new DruidDataSource();
    }

	
	//配置事物管理器
    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }
	
    //配置sqlSessionFactory
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
      MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
      
      mybatisPlus.setDataSource(dataSource());
      PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
      mybatisPlus.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
      return mybatisPlus.getObject();
    }
    
    
    /**
     * 分页插件，自动识别数据库类型
     * 多租户，请参考官网【插件扩展】
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
    
}
