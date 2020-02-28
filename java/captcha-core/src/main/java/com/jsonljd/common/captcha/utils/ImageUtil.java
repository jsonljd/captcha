package com.jsonljd.common.captcha.utils;


import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Random;

/**
 * @Author <a href="mailto:liangjiandong@wxchina.com">JianDong.Liang</a>
 * @Description: iot project IOT_1.0.0.1
 * @Create: 2018-8-24
 */
public class ImageUtil {
    private static final float C = 0.55228474983f;
    private static Random random = new Random();
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
     *
     * @param input 图形输入流
     * @param height 高
     * @param width 宽
     * @param bb 是否白边
     * @return
     */
    public final static BufferedImage scale(BufferedImage input, int height, int width, boolean bb) {
        try {
            double ratio = 0.0; // 缩放比例
            BufferedImage bi = input;
            Image itemp = null;// // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);//bi.SCALE_SMOOTH  选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
                double   ratioHeight = (new Integer(height)).doubleValue()/ bi.getHeight();
                double   ratioWhidth = (new Integer(width)).doubleValue()/ bi.getWidth();
                if(ratioHeight>ratioWhidth){
                    ratio= ratioHeight;
                }else{
                    ratio= ratioWhidth;
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform//仿射转换
                        .getScaleInstance(ratio, ratio), null);//返回表示剪切变换的变换
                itemp = op.filter(bi, null);//转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。
            }else{
                itemp = input;
            }
            if (bb) {//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);//构造一个类型为预定义图像类型之一的 BufferedImage。
                Graphics2D g = image.createGraphics();//创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中。
                g.setColor(Color.white);//控制颜色
                g.fillRect(0, 0, width, height);// 使用 Graphics2D 上下文的设置，填充 Shape 的内部区域。
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            return (BufferedImage)itemp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param input 图形输入流
     * @param height 高
     * @param width 宽
     * @return
     */
    public final static BufferedImage scale(BufferedImage input, int height, int width){
        return scale(input,height,width,false);
    }

    /**
     *
     * @param input 图形输入流
     * @param height 高
     * @param width 宽
     * @param bb 是否白边
     * @return
     */
    public final static BufferedImage scale(InputStream input, int height, int width, boolean bb) {
        try {
            return scale(ImageIO.read(input),height,width,bb);
        } catch (IOException e) {
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
    public static void addShadowImage(BufferedImage srcImg,int gbW,int gbH ,GeneralPath path, Point translatePoint,GeneralPath pathObstruct, Point obstructPoint) {
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


            /*BasicStroke bs = new BasicStroke(1);
            graphics.setColor(new Color(255, 255, 255, 150));
            graphics.setStroke(bs);
            graphics.draw(pathNew);

            GeneralPath pathTriangle = (GeneralPath) ImageUtil.simpleTriangle().clone();
            pathTriangle.transform(AffineTransform.getTranslateInstance(Double.valueOf(gbW*0.1).intValue(), Double.valueOf(gbH*0.845).intValue()));

            graphics.setColor(new Color(255, 255, 255, 150));
            graphics.fill(pathTriangle);

            GeneralPath pathTriangle2 = (GeneralPath) ImageUtil.simpleTriangle().clone();
            pathTriangle2.transform(AffineTransform.getTranslateInstance(Double.valueOf(gbW*0.1).intValue(), Double.valueOf(gbH*0.9).intValue()));

            graphics.setColor(new Color(255, 255, 255, 255));
            graphics.fill(pathTriangle2);*/

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            graphics.dispose();
        }
    }

    public static void addBgPoint(BufferedImage image){
        Graphics2D g2 = image.createGraphics();
        // 绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(image.getWidth() - 1);
            int y = random.nextInt(image.getHeight() - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.02f;// 噪声率
        int area = (int) (yawpRate * image.getWidth() * image.getHeight());
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(image.getWidth());
            int y = random.nextInt(image.getHeight());
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }
        g2.dispose();
    }


    public static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    public static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
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
