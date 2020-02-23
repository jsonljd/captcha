package com.jsonljd.common.captcha.proccess;

import com.alibaba.fastjson.JSON;
import com.jsonljd.common.captcha.api.core.ICaptchaFactory;
import com.jsonljd.common.captcha.api.entity.CaptchaEntity;
import com.jsonljd.common.captcha.api.entity.ToBeVerifyEntity;
import com.jsonljd.common.captcha.core.DefaultLoadPropFactory;
import com.jsonljd.common.captcha.entity.ImgInteract;
import com.jsonljd.common.captcha.entity.ImgPostion;
import com.jsonljd.common.captcha.utils.ByteUtil;
import com.jsonljd.common.captcha.utils.ConstUtil;
import com.jsonljd.common.captcha.utils.DistanceUtil;
import com.jsonljd.common.captcha.utils.ImageUtil;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname PointClickProccess
 * @Description TODO
 * @Date 2020/2/21 10:21
 * @Created by JSON.L
 */
public class PointClickProccess extends BaseProccess implements ICaptchaFactory<ToBeVerifyEntity, CaptchaEntity<ImgInteract>> {
    private final static double RADIUS = 60D;
    private final static double PER = 75D;
    private final static double PER_OFFSET = 5D;
    private final static double SIM_THRESHOLD = 30D;
    @Setter
    private List<String> keyNames;
    private static Random random = new Random();
    public PointClickProccess(List<String> keyNames) {
        this.keyNames = keyNames;
    }

    @Override
    public OutputStream outImageSimple(CaptchaEntity<ImgInteract> captchaEntity, Map<String, Object> outParams) {
        return null;
    }

    @Override
    public OutputStream outImageArray(CaptchaEntity<ImgInteract>[] captchaEntitys, Map<String, Object> outParams) {
        return null;
    }

    @Override
    public void setImage(OutputStream outputStream, CaptchaEntity<ImgInteract> captchaEntity, Map<String, Object> outParams) {

    }

    @Override
    public boolean verifiCode(ToBeVerifyEntity toBeVerifyEntity) {
        List<ImgPostion> data = JSON.parseArray(new String(toBeVerifyEntity.getOriginalByte()), ImgPostion.class);
        List<ImgPostion> chkPoints = JSON.parseArray(new String(toBeVerifyEntity.getVerifyByte()), ImgPostion.class);

        Double[][] aa = new Double[data.size()][2];
        Double[][] bb = new Double[chkPoints.size()][2];

        if (aa.length != bb.length) {
            return false;
        }

        int index = 0;
        for (ImgPostion pos : data) {
            aa[index][0] = pos.getX();
            aa[index][1] = pos.getY();
            index++;
        }

        index = 0;
        for (ImgPostion pos : chkPoints) {
            bb[index][0] = pos.getX();
            bb[index][1] = pos.getY();
            index++;
        }

        double ret = DistanceUtil.simDistance(aa, bb);
        return ret < SIM_THRESHOLD;
    }

    @Override
    public CaptchaEntity<ImgInteract>[] buildCaptcha(Map<String, Object> buildParams) {
        List<ImgPostion> ret = new LinkedList<>();
        char[] data = getKeyData().toCharArray();
        int count = data.length;
        while (count > 0) {
            ImgPostion _tmp = getRandomPostion(String.valueOf(data[data.length - count]));
            boolean isInCir = false;
            for (ImgPostion item : ret) {
                if (isInCircle(_tmp.getX(), _tmp.getY(), item.getX(), item.getY(), RADIUS)) {
                    isInCir = true;
                    break;
                }
            }
            if (isInCir) {
                continue;
            }
            ret.add(_tmp);
            count--;
        }
        buildParams.put(ConstUtil.IMG_CUT_SPLIE, ConstUtil.IMG_CUT_WIDTH);
        buildParams.put(ConstUtil.IMG_RANDOM_ARR, ByteUtil.toShortList(orderWidth()));
        buildParams.put(ConstUtil.IMG_SIZE, 3);
        if(buildParams!=null && buildParams.containsKey(ConstUtil.IS_MIX_IMAGE)){
            buildParams.put(ConstUtil.IS_MIX_IMAGE,buildParams.get(ConstUtil.IS_MIX_IMAGE));
        }
        CaptchaEntity<ImgInteract>[] retCap = new CaptchaEntity[2];
        CaptchaEntity<ImgInteract> paimary = new CaptchaEntity<>();
        ImgInteract imgInteract = new ImgInteract();
        try {
            imgInteract.setImgByte(clickImage(ret, buildParams));

        } catch (Exception e) {

        }
        imgInteract.setImgPostions(ret);
        paimary.setCaptchaType(CaptchaEntity.CaptchaType.PRIMARY_IMG);
        paimary.setOriginalObj(imgInteract);
        paimary.setParams(buildParams);
        retCap[0] = paimary;

        paimary = new CaptchaEntity<>();
        imgInteract = new ImgInteract();
        try {
            imgInteract.setImgByte(tipImage(ret, buildParams));

        } catch (Exception e) {

        }
        paimary.setCaptchaType(CaptchaEntity.CaptchaType.SECONDARY_IMG);
        paimary.setOriginalObj(imgInteract);
        paimary.setParams(buildParams);
        retCap[1] = paimary;
        return retCap;
    }

    private byte[] clickImage(List<ImgPostion> postionLists, Map<String, Object> params) throws IOException {
        BufferedImage d = DefaultLoadPropFactory.getRandom(1);
        BufferedImage ret = null;
        for (ImgPostion item : postionLists) {
            ret = setContent(d, item.getKey(), (float) item.getX(), (float) item.getY());
        }
        Object arr = params.get(ConstUtil.IMG_RANDOM_ARR);
        Object isMixImg = params.get(ConstUtil.IS_MIX_IMAGE);
        boolean boolIsMixImg = isMixImg != null ? (boolean) isMixImg : true;
        java.util.List<Integer> poxList = JSON.parseArray(JSON.toJSONString(arr), Integer.class);
        Integer weightSplit = Integer.valueOf(ConstUtil.IMG_CUT_WIDTH);
        if (boolIsMixImg) {
            ret = ImageUtil.imgRandom(ret, ByteUtil.toLongList(poxList), weightSplit);
        }
        return ImageUtil.img2Byte(ret, IMG_TYPE);
    }

    private byte[] tipImage(List<ImgPostion> postionLists, Map<String, Object> params) throws IOException {
        StringBuilder dataStr = new StringBuilder();
        dataStr.append("请依次点击:");
        dataStr.append(postionLists.stream().map(i -> i.getKey()).collect(Collectors.joining("")));
        int sizeFont = params.get(ConstUtil.CONS_POINT_CLICK_TIP_FONT_SIZE)!=null?Integer.parseInt(params.get(ConstUtil.CONS_POINT_CLICK_TIP_FONT_SIZE).toString()):28;
        return generateBufferedImage(BG_IMG_WIDTH, sizeFont,dataStr.toString());
    }

    private String getKeyData() {
        return keyNames.get((int) (Math.random() * keyNames.size()));
    }

    private static double getRandomPoint() {
        return Math.random() * PER + PER_OFFSET;
    }

    private ImgPostion getRandomPostion(String key) {
        Double xD = toScale(getRandomPoint());
        Double yD = toScale(getRandomPoint(20, 65));
        int x = Double.valueOf(BG_IMG_WIDTH * (xD / SIM_PER)).intValue();
        int y = Double.valueOf(BG_IMG_HEIGHT * (yD / SIM_PER)).intValue();
        return new ImgPostion(key, x, y);
    }

    /**
     * 判断一个点是否在圆形区域内
     *
     * @param pointLon 要判断的点的纵坐标
     * @param pointLat 要判断的点的横坐标
     * @param lon      圆心纵坐标
     * @param lat      圆心横坐标
     * @param radius   圆的半径
     * @return
     */
    private static boolean isInCircle(double pointLon, double pointLat, double lon, double lat,
                                      double radius) {
        double distance = Math.hypot((pointLon - lon), (pointLat - lat));
        if (distance > radius) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     */
    private static BufferedImage setContent(BufferedImage img, String content, float xPer, float yPer) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            Graphics2D g = img.createGraphics();
            g.setBackground(Color.WHITE);
            //Color c = getRandColor(220, 250);

//            Color c = new Color(255,(new Double(Math.random() * 128)).intValue() + 128,160);

            Color c = Color.WHITE;

            Font loadFont = DefaultLoadPropFactory.getFont();

            loadFont.deriveFont(16);

            Random rand = new Random();
            g.setColor(c);//设置字体颜色
            g.setStroke(new BasicStroke(3));
            g.setFont(loadFont);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            if (content != null) {

                AffineTransform affine = new AffineTransform();
                affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
                        xPer - 12, yPer + 10);
                g.setTransform(affine);
                g.drawString(content, xPer - 12, yPer + 10);
            }
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return img;
    }

    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static byte[] generateBufferedImage(int w, int h,String code) throws IOException {
        int verifySize = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
                Color.ORANGE, Color.PINK, Color.YELLOW};
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
            fractions[i] = rand.nextFloat();
        }
        Arrays.sort(fractions);

        // g2.setColor(Color.GRAY);// 设置边框色
        g2.fillRect(0, 0, w, h);

        Color c = getRandColor(200, 250);
        g2.setColor(c);// 设置背景色
        g2.fillRect(0, 0, w, h);

        // 绘制干扰线
        Random random = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 添加噪点
        float yawpRate = 0.02f;// 噪声率
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        // shear(g2, w, h, c);// 使图片扭曲

        g2.setColor(getRandColor(100, 160));
        int fontSize = h - 4;
        Font font = new Font(DefaultLoadPropFactory.getFontName(), Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < verifySize; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1),
                        (w / verifySize) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 5, h / 2 + fontSize / 2 - 5);
        }
        g2.dispose();
        return ImageUtil.img2Byte(image, IMG_TYPE);// 输出png图片
    }

    private static int getRandomIntColor() {
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
}
