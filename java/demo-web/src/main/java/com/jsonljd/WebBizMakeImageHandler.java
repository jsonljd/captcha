package com.jsonljd;

import com.jsonljd.common.captcha.api.biz.IBizMakeImageHandler;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @Classname WebBizMakeImageHandler
 * @Description TODO
 * @Date 2020/2/21 20:35
 * @Created by JSON.L
 */
public class WebBizMakeImageHandler implements IBizMakeImageHandler<String> {
    @Setter
    private Map<String, Object> params;
    @Getter
    private String storeForVerifyStr;
    @Getter
    private String outputForViewStr;

    @Override
    public void storeForVerify(String str) {
        this.storeForVerifyStr = str;
    }

    @Override
    public void outputForView(String str) {
        this.outputForViewStr = str;
    }

    @Override
    public Map<String, Object> getBizParams() {
        return params;
    }
}
