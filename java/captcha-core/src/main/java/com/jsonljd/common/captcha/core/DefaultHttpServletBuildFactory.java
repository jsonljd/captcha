package com.jsonljd.common.captcha.core;

import com.jsonljd.common.captcha.api.biz.IBizMakeImageHandler;
import com.jsonljd.common.captcha.api.biz.IBizVerifiCodeHandler;
import com.jsonljd.common.captcha.handler.ImgHttpBaseHandler;
import com.jsonljd.common.captcha.utils.BeanUtil;
import com.jsonljd.common.captcha.utils.ConstUtil;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname DefaultHttpServletBuildFactory
 * @Description TODO
 * @Date 2020/2/18 18:48
 * @Created by JSON.L
 */
@Service
public final class DefaultHttpServletBuildFactory extends BaseImgCaptchaManage {
    @PostConstruct
    public void init() {
        String[] handlerArr = {"com.jsonljd.common.captcha.handler.PuzzleHandler", "com.jsonljd.common.captcha.handler.PointClickHander"};
        for (String item : handlerArr) {
            ImgHttpBaseHandler baseHandler = BeanUtil.getFactory(ImgHttpBaseHandler.class, item);
            captchaHandler.put(baseHandler.getHandlerName(), baseHandler);
        }
    }

    public static void main(String[] args) {
        DefaultHttpServletBuildFactory httpServletBuildFactory = new DefaultHttpServletBuildFactory();
        httpServletBuildFactory.init();
        final String[] org = new String[1];
        httpServletBuildFactory.buildCaptcha(new IBizMakeImageHandler<String>() {

            @Override
            public Map<String, Object> getBizParams() {
                Map<String, Object> params = new HashMap<>();
                params.put(PARAMS_HANDLER_TYPE, ConstUtil.CONS_POINT_CLICK_HANDLER);
                return params;
            }

            @Override
            public void storeForVerify(String s) {
                System.out.println(s);
                org[0] = s;
            }

            @Override
            public void outputForView(String obj) {
                System.out.println(obj);
            }
        });

        boolean isoK = httpServletBuildFactory.verifiCode(new IBizVerifiCodeHandler<String, String>() {
            @Override
            public String getOrgData() {
                return "{AQAAAGMAAAHqW3sia2V5Ijoi5oCnIiwieCI6MTA1LjAsInkiOjMzLjB9LHsia2V5Ijoi5LmDIiwieCI6NDcuMCwieSI6NTIuMH0seyJrZXkiOiLov4EiLCJ4IjoxOTMuMCwieSI6MjcuMH1deyJIQU5ETEVSX1RZUEUiOiJQT0lOVF9DTElDSyIsImlfY19zIjoiMiIsIkJVSUxEX1RZUEUiOiJQT0lOVF9DTElDSyIsImlfcyI6MywiaV9hcnIiOls1MjIyMTgzOTAsODU4MTAxMTExLDEwMTU5NTk4MTgsMTgxMjI4NTcyNiwxOTAxMjY4ODI1LDEwNTkxNTA5MjUsMzI2OTg0Nzg4LDgxMzU3MTM0NSwxNjk5MjU0MTA2LDQ1NjI2ODg4MCwxMjQ3OTQyMjg5LDIwNjYxNzYxMTgsMTI3OTY1ODMzMywxMDQwNzQ2NTIyLDYwNjg4NjY1NiwtMjEyODQ1NjA1NywxNzc5NTc4NDgwLDIwMzA4NTg4MTYsLTE5NzEwMjQ4ODUsMTc0NzM0MTk0MSwyMTM1ODMzMjE2LDIzNTkyOTk3MSw0MDkwMzk5MzMsLTE4NzAwNTgxMTgsNTI2MzY0ODUsMTI0MjA1OTE5LDMzNTkzODYwOSw4NzU1MjM0NTgsMTY2NjY1MjQ1NiwzNTUwNDI2NjUsMTExMjg5ODcwNywtMTg0MTg4MzI1MSwxNjE5NzM5NjkwLDEzMzQxOTYyMjYsOTYzMDE0MjkyLDg5Mzg2MjI2NCwxMTMwMjY3NDM4LDcyOTM3MDldfQ==}";
            }

            @Override
            public String getIptData() {
                return "[{\"x\":104,\"y\":21},{\"x\":56,\"y\":54},{\"x\":168,\"y\":39}]";
            }

            @Override
            public Map<String, Object> getBizParams() {
                return null;
            }
        });
        System.out.println(isoK);
    }
}

