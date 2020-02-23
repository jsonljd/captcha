package com.jsonljd.common.captcha.api.core;


import com.jsonljd.common.captcha.api.entity.CaptchaEntity;
import com.jsonljd.common.captcha.api.entity.ToBeVerifyEntity;

/**
 * @Classname ICaptchaFactory
 * @Description TODO
 * @Date 2020/2/18 14:50
 * @Created by JSON.L
 */
public interface ICaptchaFactory<B extends ToBeVerifyEntity,T extends CaptchaEntity> extends IImageFactory<T>,IVerifiFactory<B,T> {
}
