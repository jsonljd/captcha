package com.jsonljd.common.captcha.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author <a href="mailto:liangjiandong@wxchina.com">JianDong.Liang</a>
 * @Description: iot project IOT_1.0.0.1
 * @Create: 2018-8-20
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class ImgPostion {
    private String key;
    private double x;
    private double y;
}
