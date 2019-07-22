package com.tzCloud.arch.core.sysconfig.dataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author zxiao
 * @Title: DynamicDataSource
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/25 10:24
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        log.debug("数据源为{}", DataSourceContextHolder.getDB());
        if (DataSourceContextHolder.getDB() == null) {
            DataSourceContextHolder.setDB(DataSourceContextHolder.DEFAULT_DS);
        }
        return DataSourceContextHolder.getDB();
    }
}
