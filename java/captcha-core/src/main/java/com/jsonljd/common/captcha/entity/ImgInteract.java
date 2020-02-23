package com.jsonljd.common.captcha.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname ImgInteract
 * @Description TODO
 * @Date 2020/2/20 10:17
 * @Created by JSON.L
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImgInteract implements Serializable {
    private List<ImgPostion> imgPostions;
    private byte[] imgByte;
}
