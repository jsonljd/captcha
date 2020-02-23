package com.jsonljd;

import com.jsonljd.common.captcha.api.biz.IBizMakeImageHandler;
import com.jsonljd.common.captcha.core.DefaultHttpServletBuildFactory;
import com.jsonljd.common.captcha.utils.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname HelloWorldController
 * @Description TODO
 * @Date 2020/2/21 20:15
 * @Created by JSON.L
 */
@Controller
public class CaptchaController {
    public static final String CAPTCHA_KEY = "captchaKey";
    @Autowired
    private DefaultHttpServletBuildFactory httpServletBuildFactory;

    @RequestMapping("/captcha")
    @ResponseBody
    public String captcha(HttpServletRequest request,String handler){
        WebBizMakeImageHandler webBizMakeImageHandler = new WebBizMakeImageHandler();
        Map<String, Object> params = new HashMap<>();
        params.put("HANDLER_TYPE","puzzle".equalsIgnoreCase(handler)?ConstUtil.CONS_PUZZLE_HANDLER:ConstUtil.CONS_POINT_CLICK_HANDLER);
        webBizMakeImageHandler.setParams(params);
        httpServletBuildFactory.buildCaptcha(webBizMakeImageHandler);
        HttpSession session =request.getSession();
        session.setAttribute(CAPTCHA_KEY,webBizMakeImageHandler.getStoreForVerifyStr());
        return webBizMakeImageHandler.getOutputForViewStr();
    }

    @RequestMapping(value = "/verifiCode",method = RequestMethod.POST)
    @ResponseBody
    public String verifiCode(HttpServletRequest request,@RequestBody String iptData){
        WebBizVerifiCodeHandler webBizMakeImageHandler = new WebBizVerifiCodeHandler();
        HttpSession session =request.getSession();
        webBizMakeImageHandler.setOrgData(session.getAttribute(CAPTCHA_KEY).toString());
        webBizMakeImageHandler.setIptData(iptData);
        Boolean verifiResult = httpServletBuildFactory.verifiCode(webBizMakeImageHandler);
        return verifiResult.toString();
    }

}
