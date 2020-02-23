package com.jsonljd.common.captcha.api.core;

import java.io.Serializable;
import java.util.Map;

/**
 * @Classname IVerifiFactory
 * @Description 验证工厂接口类
 * @Date 2020/2/18 11:26
 * @Created by JSON.L
 */
public interface IVerifiFactory<B extends Serializable, T extends Serializable> {
    /**
     * 验证
     *
     * @param toBeVerifyEntity
     * @return
     */
    boolean verifiCode(B toBeVerifyEntity);

    /**
     * 生产验证码
     *
     * @param buildParams
     * @return
     */
    T[] buildCaptcha(Map<String, Object> buildParams);

}
