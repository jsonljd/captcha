package com.jsonljd.common.captcha.api.core;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * @Classname IImageFactory
 * @Description 图形工厂接口
 * @Date 2020/2/18 14:39
 * @Created by JSON.L
 */
public interface IImageFactory<T extends Serializable> {
    /**
     * 输出图形
     *
     * @param captchaEntity
     * @return
     */
    OutputStream outImageSimple(T captchaEntity, Map<String, Object> outParams);

    /**
     * 输出图形
     *
     * @param captchaEntitys
     * @return
     */
    OutputStream outImageArray(T[] captchaEntitys, Map<String, Object> outParams);

    /**
     * 输出图形
     *
     * @param captchaEntity
     * @param outParams
     */
    void setImage(OutputStream outputStream, T captchaEntity, Map<String, Object> outParams);
}
