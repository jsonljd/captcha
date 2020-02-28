package com.jsonljd.common.captcha.api.core;

/**
 * @Classname IHandler
 * @Description TODO
 * @Date 2020/2/19 12:15
 * @Created by JSON.L
 */
public interface IHandler<OUT, VER, MAK> {
    boolean verifiCode(VER bizVerifiHandler);

    OUT buildCaptcha(MAK bizVerifiHandler);
}
