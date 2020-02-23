package com.jsonljd.common.captcha.utils;

import java.util.Random;

/**
 * @Author <a href="mailto:liangjiandong@wxchina.com">JianDong.Liang</a>
 * @Description: iot project IOT_1.0.0.1
 * @Create: 2018-9-3
 */
public class ArrayUtil {

    public static Integer[] arrayInit(int count){
        Integer[] ret = new Integer[count];
        for(int i = 0;i<count;i++){
            ret[i] = i;
        }
        return ret;
    }

    public static Integer[] arrayRandom(Integer[] src){
        Integer [] arr2 =new Integer[src.length];
        int count = src.length;
        int cbRandCount = 0;
        int cbPosition = 0;
        int k =0;
        do {
            Random rand = new Random();
            int r = count - cbRandCount;
            cbPosition = rand.nextInt(r);
            arr2[k++] = src[cbPosition];
            cbRandCount++;
            src[cbPosition] = src[r - 1];
        } while (cbRandCount < count);
        return arr2;
    }
}
