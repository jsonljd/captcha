package com.jsonljd.common.captcha.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Classname CaptchaEntity
 * @Description TODO
 * @Date 2020/2/18 14:34
 * @Created by JSON.L
 */
@Data
public class CaptchaEntity<T extends Serializable> implements Serializable {
    private CaptchaType captchaType;//验证码类型
    private T originalObj; //原始的数据
    private Map<String,Object> params; //辅助参数

    public enum CaptchaType{
        /**
         * 主图片
         */
        PRIMARY_IMG(0),
        /**
         * 辅助图片
         */
        SECONDARY_IMG(1);
        private  int index;
        CaptchaType(int idx){
            this.index = idx;
        }
    }
}
