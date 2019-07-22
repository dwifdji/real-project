package com.tzCloud.arch.core.sysconfig.dataSource;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zxiao
 * @Title: DataSourceContextHolder
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/25 10:23
 */
public class DataSourceContextHolder {

    public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);
    /**
     * 默认数据源
     */
    public static final String DEFAULT_DS = "mysqlCrawler";
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源名
    public static void setDB(String dbType) {
        log.info("切换到{}数据源", dbType);
        contextHolder.set(dbType);
    }

    // 获取数据源名
    public static String getDB() {
        return (contextHolder.get());
    }

    // 清除数据源名
    public static void clearDB() {
        log.info("清除数据源名");
        contextHolder.remove();
    }
}
