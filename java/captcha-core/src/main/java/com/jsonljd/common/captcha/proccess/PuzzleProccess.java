package com.jsonljd.common.captcha.proccess;

import com.alibaba.fastjson.JSON;
import com.jsonljd.common.captcha.api.core.ICaptchaFactory;
import com.jsonljd.common.captcha.api.entity.CaptchaEntity;
import com.jsonljd.common.captcha.api.entity.ToBeVerifyEntity;
import com.jsonljd.common.captcha.core.DefaultLoadPropFactory;
import com.jsonljd.common.captcha.entity.ImgInteract;
import com.jsonljd.common.captcha.entity.ImgPostion;
import com.jsonljd.common.captcha.handler.bg.IBackgroundHandler;
import com.jsonljd.common.captcha.utils.ByteUtil;
import com.jsonljd.common.captcha.utils.ConstUtil;
import com.jsonljd.common.captcha.utils.DistanceUtil;
import com.jsonljd.common.captcha.utils.ImageUtil;
import org.apache.commons.codec.digest.DigestUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Classname PuzzleProccess
 * @Description 拼图交互
 * @Date 2020/2/20 9:56
 * @Created by JSON.L
 */
public class PuzzleProccess extends BaseProccess implements ICaptchaFactory<ToBeVerifyEntity, CaptchaEntity<ImgInteract>> {
    private static final Integer X_OFFSET = 0; //X坐标的偏移量
    private static final Integer Y_OFFSET = 0; //Y坐标的偏移量
    private final static double SIM_THRESHOLD = 5D;

    @Override
    public boolean verifiCode(ToBeVerifyEntity toBeVerifyEntity) {
        List<ImgPostion> data = JSON.parseArray(new String(toBeVerifyEntity.getOriginalByte()), ImgPostion.class);
        Double chkX = Double.valueOf(new String(toBeVerifyEntity.getVerifyByte()));
        Double[][] aa = new Double[1][2];
        Double[][] bb = new Double[1][2];
        aa[0][0] = data.get(0).getX();
        aa[0][1] = data.get(0).getY();

        bb[0][0] = chkX;
        bb[0][1] = data.get(0).getY();
        double ret = DistanceUtil.simDistance(aa, bb);
        return ret < SIM_THRESHOLD;
    }


    @Override
    public CaptchaEntity<ImgInteract>[] buildCaptcha(Map<String, Object> buildParams) {

        CaptchaEntity<ImgInteract>[] ret = new CaptchaEntity[2];
        CaptchaEntity<ImgInteract> paimary = new CaptchaEntity<>();
        ImgPostion temp = getRandomPostion((Integer)(buildParams.get(ConstUtil.KEY_IMG_BG_WIDTH)),(Integer)(buildParams.get(ConstUtil.KEY_IMG_BG_HEIGHT)));
        ImgInteract imgInteract = new ImgInteract();
        int key = getKey(temp.getKey());
        int ind = (key % 4);
        buildParams.put(ConstUtil.IMG_CUT_SPLIE, ConstUtil.IMG_CUT_WIDTH);
        buildParams.put(ConstUtil.IMG_RANDOM_ARR, ByteUtil.toShortList(orderWidth((Integer)buildParams.get(ConstUtil.KEY_IMG_BG_WIDTH))));
        buildParams.put(ConstUtil.IMG_Y_OFFSET, temp.getY());
//        buildParams.put(ConstUtil.IMG_X_OFFSET, (ind == 1 || ind == 2) ? 15 : 20);
        buildParams.put(ConstUtil.IMG_X_OFFSET,0);
        if(buildParams!=null && buildParams.containsKey(ConstUtil.IS_MIX_IMAGE)){
            buildParams.put(ConstUtil.IS_MIX_IMAGE,buildParams.get(ConstUtil.IS_MIX_IMAGE));
        }

        IBackgroundHandler backgroundHandler = (IBackgroundHandler)buildParams.get(ConstUtil.CON_BG_HANDLER);
        BufferedImage bg = backgroundHandler.getBackground((Integer)buildParams.get(ConstUtil.KEY_IMG_BG_WIDTH),(Integer)buildParams.get(ConstUtil.KEY_IMG_BG_HEIGHT));

        try {
            imgInteract.setImgByte(toImage(bg,buildParams,temp, 1));

        } catch (Exception e) {
            e.printStackTrace();
        }


        imgInteract.setImgPostions(Arrays.asList(temp));
        paimary.setCaptchaType(CaptchaEntity.CaptchaType.PRIMARY_IMG);
        paimary.setOriginalObj(imgInteract);
        paimary.setParams(buildParams);

        ret[0] = paimary;

        CaptchaEntity<ImgInteract> second = new CaptchaEntity<>();
        second.setCaptchaType(CaptchaEntity.CaptchaType.SECONDARY_IMG);
        ImgInteract imgInteractSecond = new ImgInteract();
        try {
            imgInteractSecond.setImgByte(toImage(bg,buildParams,temp, 2));

        } catch (Exception e) {

        }
        second.setOriginalObj(imgInteractSecond);

        second.setParams(buildParams);
        ret[1] = second;
        return ret;
    }

    private byte[] toImage(BufferedImage bg,Map<String, Object> buildParams,ImgPostion temp, int types) throws IOException {
        ImgPostion point = temp;
        Point pointXy = getPoint(point);
        bg = ImageUtil.copyImage(bg);
        int key = getKey(point.getKey());
        BufferedImage retImg = null;
        Float puzzleRadius = (Float)(buildParams.get(ConstUtil.KEY_IMG_PUZZLE_RADIUS));
        Float puzzleSplitLine = (Float)(buildParams.get(ConstUtil.KEY_IMG_PUZZLE_SPLIT_LINE));
        if (types == 1) {
            /**干扰图 **/
            Integer bgW = (Integer)(buildParams.get(ConstUtil.KEY_IMG_BG_WIDTH));
            Integer bgH = (Integer)(buildParams.get(ConstUtil.KEY_IMG_BG_HEIGHT));
            ImgPostion obstruct = getRandomPostion(40,60,5,80,bgW,bgH);
            Point obstructPointXy = getPoint(obstruct);
            int obstructKey = getKey(obstruct.getKey());
            GeneralPath obstructGeneralPath = getPath(puzzleRadius,puzzleSplitLine,obstructKey);
            /**干扰图 **/
            ImageUtil.addShadowImage(bg,bgW,bgH, getPath(puzzleRadius,puzzleSplitLine,key), pointXy, obstructGeneralPath, obstructPointXy);

            Object arr = buildParams.get(ConstUtil.IMG_RANDOM_ARR);
            java.util.List<Integer> poxList = JSON.parseArray(JSON.toJSONString(arr), Integer.class);
            Integer weightSplit = Integer.valueOf(ConstUtil.IMG_CUT_WIDTH);

            Object isMixImg = buildParams.get(ConstUtil.IS_MIX_IMAGE);
            boolean boolIsMixImg = isMixImg != null ? (boolean) isMixImg : true;
            if (boolIsMixImg) {
                bg = ImageUtil.imgRandom(bg, ByteUtil.toLongList(poxList), weightSplit);
            }

            retImg = bg;
        } else {
            retImg = ImageUtil.cutImage(bg, getPath(puzzleRadius,puzzleSplitLine,key), pointXy);
        }

        return ImageUtil.img2Byte(retImg, IMG_TYPE);
    }

    private GeneralPath getPath(float radius,float splitLine,int key) {
        return ImageUtil.jigsawPath(radius, splitLine, X_OFFSET, Y_OFFSET, key);
    }

    private int getKey(String str) {
        byte[] md5 = DigestUtils.md5Hex(str).getBytes();
        int k = (md5[3] & 0xFF |
                (md5[2] & 0xFF) << 8 |
                (md5[1] & 0xFF) << 16 |
                (md5[0] & 0xFF) << 24);
        return k;
    }

    private ImgPostion getRandomPostion(int xS, int xE, int yS, int yE,int bgWidth,int bgHeigth) {
        Double xD = toScale(getRandomPoint(xS, xE));
        Double yD = toScale(getRandomPoint(yS, yE));
        int x = Double.valueOf(bgWidth * (xD / SIM_PER)).intValue();
        int y = Double.valueOf(bgHeigth * (yD / SIM_PER)).intValue();
        return new ImgPostion(UUID.randomUUID().toString(), x, y);
    }

    private ImgPostion getRandomPostion(int bgWidth,int bgHeigth) {
        return getRandomPostion(65,80,20,50,
                bgWidth,
                bgHeigth);
    }

    private Point getPoint(ImgPostion point) {
        int x = Double.valueOf(point.getX()).intValue();
        int y = Double.valueOf(point.getY()).intValue();
        return new Point(x, y);
    }
}
