package com.jsonljd.common.captcha.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @Classname BeanUtil
 * @Description TODO
 * @Date 2020/2/20 9:16
 * @Created by JSON.L
 */
public class BeanUtil {
    private static final Logger LOG = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null) {
            return true;
        }
        return "".equals(str.trim()) || "null".equalsIgnoreCase(str);
    }

    /**
     * 根据beanID查找BEAN
     *
     * @param beanID String
     * @return Object
     */
    public static Object findBean(String beanID) {
        if (isNull(beanID)) {
            return null;
        }
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        if (wac != null) {
            WebApplicationContext rwac = WebApplicationContextUtils.getRequiredWebApplicationContext(wac.getServletContext());
            if (rwac != null) {
                return rwac.getBean(beanID);
            }
        }
        LOG.info(String.format("Not found bean %s", beanID));
        return null;
    }

    /**
     * 获得BENA的实例
     *
     * @param flag ：类名，或BeanID
     * @return
     */
    public static <T> T getBean(Class<T> tClass, String flag) {
        if (flag == null) {
            return null;
        } else if (flag.indexOf('.') > 0) {
            try {
                return getFactory(tClass, flag);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        try {
            return (T) findBean(flag);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    public static <T> T getFactory(Class<T> tClass, String className) {
        T captchaFactory = null;
        try {
            captchaFactory = (T) Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("没有找到 " + className + " 类。");
        }
        return captchaFactory;
    }
}
