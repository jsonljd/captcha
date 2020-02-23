package com.jsonljd.common.captcha.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author <a href="mailto:liangjiandong@wxchina.com">JianDong.Liang</a>
 * @Description: iot project IOT_1.0.0.1
 * @Create: 2018-9-3
 */
public class ByteUtil {

    public static List<Integer> toLongList(List<Integer> src) {
        if (src == null) {
            return src;
        }
        if (src.size() == 0) {
            return src;
        }
        List<Integer> newRet = new ArrayList<>();
        int len = src.get(0) & 0xFF;
        int ind = 0;
        for (int i = 0; i < src.size(); i++) {
            Integer item = src.get(i);
            int dex = 0;
            do {
                if (len >= ind) {
                    if (ind != 0) {
                        newRet.add(item >> (dex * 8) & 0xFF);
                    }
                    ind++;
                } else {
                    break;
                }
                dex++;
            } while (dex < 4);
        }
        return newRet;
    }

    public static List<Integer> toShortList(List<Integer> src) {
        if (src == null) {
            return src;
        }
        if (src.size() == 0) {
            return src;
        }
        List<Integer> newii = new ArrayList<>();

        newii.add(src.size());
        newii.addAll(src);

        List<Integer> newRet = new ArrayList<>();
        int count = newii.size();
        int ind = 1;
        Integer i0;
        Integer i1;
        Integer i2;
        Integer i3;
        for (int i = 0; i < newii.size(); i += 4) {
            i0 = 0;i1 = 0;i2 = 0;i3 = 0;
            if (count >= ind) {
                i3 = newii.get(i);
                ind++;
            }
            if (count >= ind) {
                i2 = newii.get(i + 1);
                ind++;
            }
            if (count >= ind) {
                i1 = newii.get(i + 2);
                ind++;
            }
            if (count >= ind) {
                i0 = newii.get(i + 3);
                ind++;
            }
            newRet.add(
                    (i3 & 0xFF |
                            (i2 & 0xFF) << 8 |
                            (i1 & 0xFF) << 16 |
                            (i0 & 0xFF) << 24)
            );
            if (count < ind) {
                break;
            }
        }
        return newRet;
    }
}
