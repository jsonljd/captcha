package com.jsonljd.common.captcha.api.core;

import com.jsonljd.common.captcha.api.biz.IBizMakeImageHandler;
import com.jsonljd.common.captcha.api.biz.IBizVerifiCodeHandler;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AbsCaptchaManage
 * @Description TODO
 * @Date 2020/2/23 16:47
 * @Created by JSON.L
 */
public abstract class AbsCaptchaManage<DAT extends Serializable, IPT extends Serializable, FACT extends AbsCaptchaBuildFactory> implements IHandler<DAT, IBizVerifiCodeHandler<DAT, IPT>, IBizMakeImageHandler> {
    @Setter
    @Getter
    protected Map<String, FACT> captchaHandler = new HashMap<>();
}
