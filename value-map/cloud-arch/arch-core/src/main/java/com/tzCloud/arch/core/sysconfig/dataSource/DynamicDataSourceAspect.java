package com.tzCloud.arch.core.sysconfig.dataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author zxiao
 * @Title: DynamicDataSourceAspect
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/25 10:49
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

    @Before("@annotation(DataType)")
    public void beforeSwitchDS(JoinPoint point) {
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        String dataSource = DataSourceContextHolder.DEFAULT_DS;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DataType.class)) {
                DataType annotation = method.getAnnotation(DataType.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 切换数据源
        DataSourceContextHolder.setDB(dataSource);

    }


    @After("@annotation(DataType)")
    public void afterSwitchDS(JoinPoint point) {
        DataSourceContextHolder.clearDB();
    }
}
