package com.jsonljd;

import com.jsonljd.common.captcha.api.biz.IBizVerifiCodeHandler;
import lombok.Setter;

import java.util.Map;

/**
 * @Classname WebBizVerifiCodeHandler
 * @Description TODO
 * @Date 2020/2/21 21:27
 * @Created by JSON.L
 */
public class WebBizVerifiCodeHandler implements IBizVerifiCodeHandler<String, String> {
    @Setter
    private String orgData;
    @Setter
    private String iptData;

    @Override
    public String getOrgData() {
        return orgData;
    }

    @Override
    public String getIptData() {
        return iptData;
    }

    @Override
    public Map<String, Object> getBizParams() {
        return null;
    }
}
