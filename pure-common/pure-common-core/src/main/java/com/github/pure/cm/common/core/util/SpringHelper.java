package com.github.pure.cm.common.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>
 * spring 辅助类
 *
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-07-30 11:39
 **/
@Component
public class SpringHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringHelper.applicationContext = applicationContext;
    }


    public static <T> T getBean(Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

    public static <T> T getBean(String beanName, Class<T> beanType) {
        return applicationContext.getBean(beanName, beanType);
    }
}
