package com.jsonljd.common.captcha.api.core;

import com.jsonljd.common.captcha.api.biz.IBizMakeImageHandler;
import com.jsonljd.common.captcha.api.biz.IBizVerifiCodeHandler;
import com.jsonljd.common.captcha.api.entity.CaptchaEntity;
import com.jsonljd.common.captcha.api.entity.ToBeVerifyEntity;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * @Classname AbsCaptchaBuildFactory
 * @Description TODO
 * @Date 2020/2/18 17:00
 * @Created by JSON.L
 */
public abstract class AbsCaptchaBuildFactory<DAT extends Serializable, IPT extends Serializable, B extends ToBeVerifyEntity, T extends CaptchaEntity<TO>, TO extends Serializable> implements IHandler<IBizVerifiCodeHandler<DAT, IPT>, IBizMakeImageHandler> {
    private ICaptchaFactory<B, T> captchaFactory = null;

    public abstract String getHandlerName();

    public void setCaptchaFactory(ICaptchaFactory<B, T> captchaFactory) {
        this.captchaFactory = captchaFactory;
    }

    /**
     * 处理转换验证方法的对象数据
     * 业务数据->内部处理对象
     *
     * @param orgData 业务存储的待转换原始数据
     * @param iptData 业务输入的待转换数据
     * @return 内部处理的验证对象
     */
    protected abstract B convertForInnerVerify(DAT orgData, IPT iptData);

    /**
     * 处理已生成内部验证对象的业务数据
     *
     *
     * @param buildFactoryName 处理的对象名称
     * @param captchaArr 内部验证的对象数组
     * @param buildParams 生产的参数
     * @return 业务存储的原始数据
     */
    protected abstract DAT convertForView(String buildFactoryName, T[] captchaArr, Map<String, Object> buildParams);

    /**
     * 处理已生成内部验证对象的业务数据 用于验证
     * @param captchaArr
     * @param buildParams
     * @return
     */
    protected abstract DAT convertForStoreVerify(T[] captchaArr, Map<String, Object> buildParams);

    protected abstract Map<String, Object> dealBuildParams(Map<String, Object> bizParams);

    @Override
    public boolean verifiCode(IBizVerifiCodeHandler<DAT, IPT> bizVerifiHandler) {

        return this.captchaFactory.verifiCode(convertForInnerVerify(bizVerifiHandler.getOrgData(), bizVerifiHandler.getIptData()));
    }

    @Override
    public void makeImage(OutputStream outputStream, IBizMakeImageHandler bizVerifiHandler) {
        Map<String, Object> stringObjectMap = bizVerifiHandler.getBizParams();
        stringObjectMap = dealBuildParams(stringObjectMap);
        T[] captchaArr = this.captchaFactory.buildCaptcha(stringObjectMap);
        bizVerifiHandler.outputForView(convertForView(getHandlerName(), captchaArr, stringObjectMap));
        bizVerifiHandler.storeForVerify(convertForStoreVerify(captchaArr, stringObjectMap));
        for (int i = 0; i < captchaArr.length; i++) {
            this.captchaFactory.setImage(outputStream, captchaArr[i], stringObjectMap);
        }
    }

    public DAT makeImage(IBizMakeImageHandler bizVerifiHandler) {
        Map<String, Object> stringObjectMap = bizVerifiHandler.getBizParams();
        stringObjectMap = dealBuildParams(stringObjectMap);
        T[] captchaArr = this.captchaFactory.buildCaptcha(stringObjectMap);
        DAT ret = convertForView(getHandlerName(), captchaArr, stringObjectMap);
        bizVerifiHandler.outputForView(ret);
        bizVerifiHandler.storeForVerify(convertForStoreVerify(captchaArr,stringObjectMap ));
        return ret;
    }
}
