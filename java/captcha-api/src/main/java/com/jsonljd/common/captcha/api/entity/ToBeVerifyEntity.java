package com.jsonljd.common.captcha.api.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @Classname ToBeVerifyEntity
 * @Description 待验证的实体类，用户交互的实体类
 * @Date 2020/2/18 13:05
 * @Created by JSON.L
 */
@Data
public class ToBeVerifyEntity implements Serializable {
    private byte[] originalByte; //原始的数据
    private byte[] verifyByte; //交互的数据
}
