package com.tzCloud.arch.core.sysconfig.dataSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zxiao
 * @Title: data
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/25 11:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD
})
public @interface DataType {
    String value() default "mysqlCrawler";
}