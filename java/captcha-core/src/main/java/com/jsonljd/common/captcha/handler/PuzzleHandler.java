package com.jsonljd.common.captcha.handler;

import com.jsonljd.common.captcha.proccess.PuzzleProccess;
import com.jsonljd.common.captcha.utils.ConstUtil;

/**
 * @Classname PuzzleHandler
 * @Description 拼图方式验证处理类
 * @Date 2020/2/19 12:10
 * @Created by JSON.L
 */
public class PuzzleHandler extends ImgHttpBaseHandler {

    public PuzzleHandler() {
        this.setCaptchaFactory(new PuzzleProccess());
    }


    @Override
    public String getHandlerName() {
        return ConstUtil.CONS_PUZZLE_HANDLER;
    }


    @Override
    public void initBuildParams() {

    }


}
