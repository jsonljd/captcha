package com.jsonljd.common.captcha.core;

import com.alibaba.fastjson.JSON;
import com.jsonljd.common.captcha.api.biz.IBizMakeImageHandler;
import com.jsonljd.common.captcha.api.biz.IBizVerifiCodeHandler;
import com.jsonljd.common.captcha.api.core.AbsCaptchaBuildFactory;
import com.jsonljd.common.captcha.api.core.AbsCaptchaManage;
import com.jsonljd.common.captcha.handler.ImgHttpBaseHandler;
import com.jsonljd.common.captcha.utils.SecurityUtil;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @Classname BaseImgCaptchaManage
 * @Description 图形验证管理基类
 * @Date 2020/2/23 17:09
 * @Created by JSON.L
 */
public class BaseImgCaptchaManage extends AbsCaptchaManage<String, String, ImgHttpBaseHandler> {
    public static final String PARAMS_HANDLER_TYPE = "HANDLER_TYPE";


    @Override
    public boolean verifiCode(IBizVerifiCodeHandler<String, String> bizVerifiHandler) {
        Assert.notNull(bizVerifiHandler, "IBizVerifiCodeHandler is err");
        Assert.notNull(bizVerifiHandler.getOrgData(), "org byte arr is err");
        Assert.notNull(bizVerifiHandler.getIptData(), "ipt byte arr is err");
        String[] dataDecode = SecurityUtil.decode(new String(bizVerifiHandler.getOrgData()));
        Assert.notNull(dataDecode, "dataDecode is err");
        Map<String, Object> buildParams = JSON.parseObject(dataDecode[1]);
        String handlerTypeStr = (String) buildParams.get(PARAMS_HANDLER_TYPE);
        Assert.hasText(handlerTypeStr, "handlerType is empty!");
        AbsCaptchaBuildFactory handler = captchaHandler.get(handlerTypeStr);
        Assert.notNull(handler, "handlerType is err");
        return handler.verifiCode(bizVerifiHandler);
    }

    @Override
    public String buildCaptcha(IBizMakeImageHandler bizVerifiHandler) {
        Assert.notNull(bizVerifiHandler.getBizParams(), "params is err");
        Assert.notNull(bizVerifiHandler.getBizParams().get(PARAMS_HANDLER_TYPE), "params HANDLER_TYPE is err");
        String handlerTypeStr = (String) bizVerifiHandler.getBizParams().get(PARAMS_HANDLER_TYPE);
        Assert.hasText(handlerTypeStr, "handlerType is empty!");
        return captchaHandler.get(handlerTypeStr).buildCaptcha(bizVerifiHandler);
    }
}
