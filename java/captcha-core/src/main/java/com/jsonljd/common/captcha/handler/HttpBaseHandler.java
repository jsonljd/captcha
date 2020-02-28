package com.jsonljd.common.captcha.handler;

import com.alibaba.fastjson.JSON;
import com.jsonljd.common.captcha.api.entity.CaptchaEntity;
import com.jsonljd.common.captcha.api.entity.ToBeVerifyEntity;
import com.jsonljd.common.captcha.utils.SecurityUtil;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname HttpBaseHandler
 * @Description TODO
 * @Date 2020/2/19 16:20
 * @Created by JSON.L
 */
public abstract class HttpBaseHandler<T extends Serializable> extends BaseHandler<T> {
    protected Map<String, Object> childParams = new HashMap<>();

    public void setChildParams(String key, Object val) {
        childParams.put(key, val);
    }

    public abstract void initBuildParams();

    @Override
    protected ToBeVerifyEntity convertForInnerVerify(String orgData, String iptData) {
        Assert.hasText(orgData, "orgData is empty str");
        Assert.hasText(iptData, "iptData is empty str");
        ToBeVerifyEntity toBeVerifyEntity = new ToBeVerifyEntity();
        toBeVerifyEntity.setOriginalByte(orgData.getBytes());
        toBeVerifyEntity.setVerifyByte(iptData.getBytes());
        return toBeVerifyEntity;
    }

    @Override
    protected String convertForView(String buildFactoryName, CaptchaEntity<T>[] captchaArr, Map<String, Object> buildParams) {
        Map<String, Object> retAll = new HashMap<>();
        retAll.put("factory", buildFactoryName);
        retAll.put("params", JSON.parseObject(JSON.toJSONString(buildParams)));
        List<Map<String, Object>> ret = new ArrayList<>();
        Map<String, Object> objectMap;
        for (CaptchaEntity<T> item : captchaArr) {
            objectMap = new HashMap<>();
            objectMap.put("handler", getHandlerName());
            objectMap.put("type", item.getCaptchaType());
            objectMap.put("originalObj", item.getOriginalObj());
            ret.add(objectMap);
        }
        retAll.put("data", ret);
        return JSON.toJSONString(retAll);
    }

    @Override
    protected String convertForStoreVerify(CaptchaEntity<T>[] captchaArr, Map<String, Object> buildParams) {
        String data = JSON.toJSONString(captchaArr);
        String parms = JSON.toJSONString(buildParams);
        Assert.hasText(data, "data is empty str");
        Assert.hasText(parms, "parms is empty str");
        return SecurityUtil.encode(data, parms);
    }

    @Override
    protected void beforeBuildParams(Map<String, Object> bizParams) {
        if (bizParams == null) {
            bizParams = new HashMap<>();
        }
        this.initBuildParams();
        for (Map.Entry<String, Object> entry : this.childParams.entrySet()) {
            if (!bizParams.containsKey(entry.getKey()))
                bizParams.put(entry.getKey(), entry.getValue());
        }
        bizParams.put("BUILD_TYPE", getHandlerName());
    }

    @Override
    protected void afterBuildParams(Map<String, Object> bizParams) {

    }
}
