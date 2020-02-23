package com.jsonljd.common.captcha.handler;

import com.jsonljd.common.captcha.api.core.AbsCaptchaBuildFactory;
import com.jsonljd.common.captcha.api.entity.CaptchaEntity;
import com.jsonljd.common.captcha.api.entity.ToBeVerifyEntity;

import java.io.Serializable;

/**
 * @Classname BaseHandler
 * @Description TODO
 * @Date 2020/2/19 12:10
 * @Created by JSON.L
 */
public abstract class BaseHandler<T extends Serializable> extends AbsCaptchaBuildFactory<String, String, ToBeVerifyEntity, CaptchaEntity<T>,T> {

}
