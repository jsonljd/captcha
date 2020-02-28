package com.jsonljd.common.captcha.handler.bg.impl;

import com.jsonljd.common.captcha.handler.bg.IBackgroundHandler;
import com.jsonljd.common.captcha.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @Classname AbsBaseBgHandler
 * @Description TODO
 * @Date 2020/2/26 16:53
 * @Created by JSON.L
 */
@Slf4j
public abstract class AbsBaseBgHandler implements IBackgroundHandler {

    protected BufferedImage dealFitSize(Integer width, Integer height, Object src) {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2 = image.createGraphics();
            Color c = ImageUtil.getRandColor(200, 250);
            g2.setColor(c);// 设置背景色
            ImageUtil.addBgPoint(image);

            BufferedImage bgImg = null;
            if (src instanceof InputStream) {
                bgImg = Thumbnails.of((InputStream) src)
                        .sourceRegion(Positions.TOP_CENTER, width, height)
                        .scale(1)
                        .asBufferedImage();
            }

            if (src instanceof BufferedImage) {
                bgImg = Thumbnails.of((BufferedImage) src)
                        .sourceRegion(Positions.TOP_CENTER, width, height)
                        .scale(1)
                        .asBufferedImage();
            }

            g2.drawImage(bgImg, 0, 0, null);
            g2.dispose();

            return image;
        } catch (Exception e) {
            log.error("err", e);
            throw new IllegalArgumentException("taking gackground img err", e);
        }
    }
}
