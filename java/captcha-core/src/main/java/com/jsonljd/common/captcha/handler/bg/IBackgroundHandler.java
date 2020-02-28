package com.jsonljd.common.captcha.handler.bg;

import java.awt.image.BufferedImage;

/**
 * @Classname IBackgroundHandler
 * @Description TODO
 * @Date 2020/2/23 20:55
 * @Created by JSON.L
 */
public interface IBackgroundHandler {
    /**
     * 获取背景图片
     *
     * @return
     */
    BufferedImage getBackground(Integer width,Integer height);
}
