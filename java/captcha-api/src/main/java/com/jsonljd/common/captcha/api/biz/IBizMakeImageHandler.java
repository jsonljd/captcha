package com.jsonljd.common.captcha.api.biz;

import java.io.Serializable;
import java.util.Map;

/**
 * @Classname IBizMakeImageHandler
 * @Description TODO
 * @Date 2020/2/19 9:36
 * @Created by JSON.L
 */
public interface IBizMakeImageHandler<DAT extends Serializable> extends IBizParams {

    /**
     * 存储原始的数据用于验证交互数据
     * @param dat
     */
    void storeForVerify(DAT dat);

    /**
     * 对外输出用于显示输出
     * @param dat
     */
    void outputForView(DAT dat);
}
