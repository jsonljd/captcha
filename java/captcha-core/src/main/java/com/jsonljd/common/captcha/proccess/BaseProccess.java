package com.jsonljd.common.captcha.proccess;

import com.jsonljd.common.captcha.utils.ArrayUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname BaseProccess
 * @Description TODO
 * @Date 2020/2/21 10:31
 * @Created by JSON.L
 */
public class BaseProccess {
    protected static final Integer CUT_IMG_WIDTH = 2;
    protected final static String IMG_TYPE = "png";
    protected final static int SIM_PER = 100;

    protected static double getRandomPoint(Integer start, Integer end) {
        return Math.round(Math.random() * (end - start) + start);
    }

    protected static double toScale(double d) {
        BigDecimal b = new BigDecimal(d);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    protected List<Integer> orderWidth(int gbWidth) {
        int anther_w = gbWidth % CUT_IMG_WIDTH;
        int count_w = (gbWidth - anther_w) / CUT_IMG_WIDTH;
        Integer[] retArr = ArrayUtil.arrayRandom(ArrayUtil.arrayInit((count_w)));
        List<Integer> ret = new ArrayList<>();
        for (Integer item : retArr) {
            ret.add(item);
        }
        return ret;
    }
}
