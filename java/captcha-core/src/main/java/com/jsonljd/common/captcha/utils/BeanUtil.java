package com.jsonljd.common.captcha.utils;

/**
 * @Classname BeanUtil
 * @Description TODO
 * @Date 2020/2/20 9:16
 * @Created by JSON.L
 */
public class BeanUtil {
    public static  <T> T getFactory(Class<T> tClass,String className) {
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
