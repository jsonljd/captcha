package com.jsonljd.common.captcha.utils;


import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @Author <a href="mailto:liangjiandong@wxchina.com">JianDong.Liang</a>
 * @Description: iot project IOT_1.0.0.1
 * @Create: 2018-8-24
 */
public class ImageUtil {
    private static final float C = 0.55228474983f;

    /**
     * 复制图像
     *
     * @param srcImg
     * @return
     */
    public static BufferedImage copyImage(BufferedImage srcImg) {
        try {
            BufferedImage image = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), srcImg.getType());
            Graphics graphics = image.getGraphics();
            graphics.drawImage(srcImg, 0, 0, srcImg.getWidth(), srcImg.getHeight(), null);
            graphics.dispose();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按照路径添加图片阴影
     *
     * @param srcImg
     * @param path
     * @param translatePoint
     */
    public static void addShadowImage(BufferedImage srcImg, GeneralPath path, Point translatePoint) {
        addShadowImage(srcImg, path, translatePoint,null,null);
    }

    /**
     * 按照路径添加图片阴影
     *
     * @param srcImg
     * @param path
     * @param translatePoint
     */
    public static void addShadowImage(BufferedImage srcImg, GeneralPath path, Point translatePoint,GeneralPath pathObstruct, Point obstructPoint) {
        Graphics2D graphics = srcImg.createGraphics();
        try {
            graphics.setColor(new Color(0, 0, 0, 140));
            GeneralPath pathNew = (GeneralPath) path.clone();
            pathNew.transform(AffineTransform.getTranslateInstance(translatePoint.x, translatePoint.y));
            graphics.fill(pathNew);


            if(pathObstruct!=null && obstructPoint!=null){
                graphics.setColor(new Color(0, 0, 0, 90));
                GeneralPath pathObstructNew = (GeneralPath) pathObstruct.clone();
                pathObstructNew.transform(AffineTransform.getTranslateInstance(obstructPoint.x,obstructPoint.y));
                graphics.fill(pathObstructNew);
            }


            BasicStroke bs = new BasicStroke(1);
            graphics.setColor(new Color(255, 255, 255, 150));
            graphics.setStroke(bs);
            graphics.draw(pathNew);

            GeneralPath pathTriangle = (GeneralPath) ImageUtil.simpleTriangle().clone();
            pathTriangle.transform(AffineTransform.getTranslateInstance(29, 110));

            graphics.setColor(new Color(255, 255, 255, 150));
            graphics.fill(pathTriangle);

            GeneralPath pathTriangle2 = (GeneralPath) ImageUtil.simpleTriangle().clone();
            pathTriangle2.transform(AffineTransform.getTranslateInstance(29, 117));

            graphics.setColor(new Color(255, 255, 255, 255));
            graphics.fill(pathTriangle2);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            graphics.dispose();
        }
    }

    /**
     * 按照路径裁剪图片
     *
     * @param srcImg
     * @param path
     * @param translatePoint
     * @return
     */
    public static BufferedImage cutImage(BufferedImage srcImg, GeneralPath path, Point translatePoint) {
        try {
            //图片大小
            int width = srcImg.getWidth();
            int height = srcImg.getHeight();

            //创建一个图片缓冲区
            BufferedImage image = new BufferedImage(path.getBounds().width, path.getBounds().height, BufferedImage.TYPE_INT_RGB);

            //获取图片处理对象
            Graphics2D graphics = image.createGraphics();
            image = graphics.getDeviceConfiguration().createCompatibleImage(path.getBounds().width, path.getBounds().height, Transparency.TRANSLUCENT);
            graphics.dispose();
            graphics = image.createGraphics();
            graphics.clip(path);
            graphics.drawImage(srcImg, -translatePoint.x, -translatePoint.y, width, height, null);
            graphics.dispose();

            graphics = image.createGraphics();
            BasicStroke bs = new BasicStroke(1);
            graphics.setColor(new Color(255, 255, 0, 100));
            graphics.setStroke(bs);
            graphics.draw(path);
            graphics.dispose();

            return image;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /****/

    /**
     * @param radius
     * @param splitLine
     * @param xOffset
     * @param yOffset
     * @return
     */
    public static GeneralPath jigsawPath(float radius, float splitLine, int xOffset, int yOffset, int key) {
        int xy = Double.valueOf(2 * radius + 2 * splitLine).intValue();
        Point[] allPoint = {
                new Point(0 + xOffset, 0 + yOffset),
                new Point(xy + xOffset, 0 + yOffset),
                new Point(xy + xOffset, xy + yOffset),
                new Point(0 + xOffset, xy + yOffset),
        };

        GeneralPath gp = new GeneralPath(GeneralPath.WIND_NON_ZERO, 2);
        GeneralPath topPath = halfPartPath(true, radius, splitLine, allPoint[0]);
        gp.append(topPath, true);

        GeneralPath rightPath = halfPartPath(false, radius, splitLine, allPoint[1], 90, allPoint[1]);
        gp.append(rightPath, true);

        GeneralPath bottomPath = halfPartPath(false, radius, splitLine, allPoint[2], 180, allPoint[2]);
        gp.append(bottomPath, true);

        GeneralPath leftPath = halfPartPath(true, radius, splitLine, allPoint[3], 270, allPoint[3]);
        gp.append(leftPath, true);

        int ind = (key % 4);
        int angdeg = ind * 90;
        double oneRadian = Math.toRadians(angdeg);
        AffineTransform identify = new AffineTransform();
        identify.rotate(oneRadian, xy / 2, xy / 2);
        gp.transform(identify);

        switch (ind) {
            case 1:
                gp.transform(AffineTransform.getTranslateInstance(radius-1, 0));
                break;
            case 2:
                gp.transform(AffineTransform.getTranslateInstance(radius-1, radius-1));
                break;
            case 3:
                gp.transform(AffineTransform.getTranslateInstance(0, radius-1));
                break;
        }
        return gp;
    }

    /**
     * @param isdown    半圆开口方向  true下  false上
     * @param radius    圆半径
     * @param splitLine 线长度
     * @param point0    开始点
     * @return
     */
    private static GeneralPath halfPartPath(boolean isdown, float radius, float splitLine, Point point0) {
        return halfPartPath(isdown, radius, splitLine, point0, null);
    }

    /**
     * @param isdown      半圆开口方向  true下  false上
     * @param radius      圆半径
     * @param splitLine   线长度
     * @param point0      开始点
     * @param angdeg      旋转角度  3点钟为起点 ，顺时针反向转的角度 360度一周
     * @param pointAnchor 旋转端点
     * @return
     */
    private static GeneralPath halfPartPath(boolean isdown, float radius, float splitLine, Point point0, double angdeg, Point pointAnchor) {
        double oneRadian = Math.toRadians(angdeg);
        AffineTransform identify = new AffineTransform();
        identify.rotate(oneRadian, pointAnchor.x, pointAnchor.y);
        return halfPartPath(isdown, radius, splitLine, point0, identify);
    }

    public static GeneralPath simpleTriangle() {
        GeneralPath gp = new GeneralPath();
        gp.moveTo(0, 0);
        gp.lineTo(20, 0);
        gp.lineTo(10, 10);
        gp.lineTo(0, 0);
        return gp;
    }

    /**
     * @param isdown    半圆开口方向  true下  false上
     * @param radius    圆半径
     * @param splitLine 线长度
     * @param point0    开始点
     * @param identify  变换对象
     * @return
     */
    private static GeneralPath halfPartPath(boolean isdown, float radius, float splitLine, Point point0, AffineTransform identify) {
        float diff = radius * C;
        GeneralPath gp = new GeneralPath();
        gp.moveTo(point0.x, point0.y);
        gp.lineTo(point0.x + splitLine, point0.y);
        if (isdown) {
            gp.curveTo(point0.x + splitLine, point0.y + diff,
                    point0.x + splitLine + radius - diff, point0.y + radius,
                    point0.x + splitLine + radius, point0.y + radius);

            gp.curveTo(point0.x + splitLine + radius + diff, point0.y + radius,
                    point0.x + splitLine + radius * 2, point0.y + diff,
                    point0.x + splitLine + radius * 2, point0.y);
        } else {
            gp.curveTo(point0.x + splitLine, point0.y - diff,
                    point0.x + splitLine + radius - diff, point0.y - radius,
                    point0.x + splitLine + radius, point0.y - radius);

            gp.curveTo(point0.x + splitLine + radius + diff, point0.y - radius,
                    point0.x + splitLine + radius * 2, point0.y - diff,
                    point0.x + splitLine + radius * 2, point0.y);
        }

        gp.lineTo(point0.x + splitLine * 2 + radius * 2, point0.y);
        if (identify != null)
            gp.transform(identify);
        return gp;
    }

    /****/

    public static BufferedImage cutImage(BufferedImage img, int x, int y, int w, int h) {
        try {
            BufferedImage bi = img.getSubimage(x, y, w, h);
            return bi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer[] imgRandomOrderWidth(BufferedImage src, int splitWeidth) {
        int w = src.getWidth();
        int anther_w = w % splitWeidth;
        int count_w = (w - anther_w) / splitWeidth;
        Integer[] ret = ArrayUtil.arrayRandom(ArrayUtil.arrayInit((count_w)));
        return ret;
    }


    public static BufferedImage imgRandom(BufferedImage src, java.util.List<Integer> ret, int splitWeidth) {

        int w = src.getWidth();
        int h = src.getHeight();
        int count_w = ret.size();
        int half_h = h / 2;
        BufferedImage imageNew = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2D = imageNew.createGraphics();
        g2D.drawImage(src, 0, 0, null);
        for (int i = 0; i < count_w; i++) {
            BufferedImage sub = src.getSubimage((ret.get(i)) * splitWeidth, 0, splitWeidth, h);
            g2D.drawImage(sub.getSubimage(0, 0, splitWeidth, half_h), i * splitWeidth, half_h, null);
            g2D.drawImage(sub.getSubimage(0, half_h, splitWeidth, half_h), i * splitWeidth, 0, null);
        }
        g2D.dispose();
        return imageNew;
    }


    /**
     * 图像rgb反向处理
     *
     * @param src
     */
    public static void imgRgbReverse(BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int pixel = src.getRGB(i, j);
                int alpha = (pixel >> 24) & 0xFF;
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                int redX = rgbReverse(red);
                int greenX = rgbReverse(green);
                int blueX = rgbReverse(blue);

                int pixelNew = ((alpha & 0xFF) << 24)
                        | ((redX & 0xFF) << 16)
                        | ((greenX & 0xFF) << 8)
                        | (blueX & 0xFF);

                src.setRGB(i, j, pixelNew);
            }
        }
    }

    /**
     * RGB转向
     *
     * @param rgb
     * @return
     */
    public static int rgbReverse(int rgb) {
        String bin = fillZero(8, Integer.toBinaryString(rgb));
        String binX = exChangeString(bin);
        return binaryToDecimal(binX);
    }

    private static int binaryToDecimal(String binarySource) {
        BigInteger bi = new BigInteger(binarySource, 2);
        return Integer.parseInt(bi.toString());
    }


    private static String fillZero(int len, String str) {
        return String.format("%0" + len + "d", Integer.parseInt(str));
    }

    private static String exChangeString(String str) {
        StringBuffer sb = new StringBuffer(str);
        return sb.reverse().toString();
    }

    public static String imgBase64(byte[] imgByte){
        return Base64.encodeBase64String(imgByte);
    }

    public static byte[] img2Byte(BufferedImage image,String imgFormatType) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, imgFormatType, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e1) {
            throw e1;
        } finally {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }
    }
}
