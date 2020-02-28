package com.jsonljd.common.captcha.handler.bg.impl;

import com.jsonljd.common.captcha.core.DefaultLoadPropFactory;
import com.jsonljd.common.captcha.handler.bg.IBackgroundHandler;
import com.jsonljd.common.captcha.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Classname DefaultBgHandler
 * @Description 默认背景图获取实现类
 * @Date 2020/2/23 21:09
 * @Created by JSON.L
 */
public class DefaultBgHandler extends AbsBaseBgHandler {
    private String handlerName;

    public DefaultBgHandler(String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    public BufferedImage getBackground(Integer width,Integer height) {
        return dealFitSize(width,height,DefaultLoadPropFactory.getRandom(this.handlerName));

    }
}
