package com.jsonljd.common.captcha.handler;

import com.alibaba.fastjson.JSON;
import com.jsonljd.common.captcha.api.entity.CaptchaEntity;
import com.jsonljd.common.captcha.api.entity.ToBeVerifyEntity;
import com.jsonljd.common.captcha.entity.ImgInteract;
import com.jsonljd.common.captcha.utils.ImageUtil;
import com.jsonljd.common.captcha.utils.SecurityUtil;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ImgHttpBaseHandler
 * @Description TODO
 * @Date 2020/2/21 11:20
 * @Created by JSON.L
 */
public abstract class ImgHttpBaseHandler extends HttpBaseHandler<ImgInteract> {

    @Override
    protected String convertForView(String buildFactoryName, CaptchaEntity<ImgInteract>[] captchaArr, Map<String, Object> buildParams) {
        Map<String,Object> retAll = new HashMap<>();
        retAll.put("factory",buildFactoryName);
        retAll.put("params",JSON.parseObject(JSON.toJSONString(buildParams)));
        List<Map<String,Object>> ret = new ArrayList<>();
        Map<String,Object> objectMap;
        for(CaptchaEntity<ImgInteract> item:captchaArr){
            objectMap = new HashMap<>();
            objectMap.put("handler",getHandlerName());
            objectMap.put("type",item.getCaptchaType());
            objectMap.put("img", ImageUtil.imgBase64(item.getOriginalObj().getImgByte()));
            ret.add(objectMap);
        }
        retAll.put("data",ret);
        return JSON.toJSONString(retAll);
    }

    @Override
    protected ToBeVerifyEntity convertForInnerVerify(String orgData, String iptData) {
        orgData = SecurityUtil.decode(orgData)[0];
        Assert.notNull(orgData,"orgData is null");
        return super.convertForInnerVerify(orgData, iptData);
    }

    @Override
    protected String convertForStoreVerify(CaptchaEntity<ImgInteract>[] captchaArr, Map<String, Object> buildParams) {
        for(CaptchaEntity<ImgInteract> item:captchaArr){
            if(CaptchaEntity.CaptchaType.PRIMARY_IMG.equals(item.getCaptchaType())){
                String data = JSON.toJSONString(item.getOriginalObj().getImgPostions());
                String parms = JSON.toJSONString(buildParams);
                String ret = SecurityUtil.encode(data,parms);
                return ret;
            }
        }
        return null;
    }
}
