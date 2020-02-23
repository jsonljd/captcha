package com.jsonljd.common.captcha.api.core;

import java.io.OutputStream;

/**
 * @Classname IHandler
 * @Description TODO
 * @Date 2020/2/19 12:15
 * @Created by JSON.L
 */
public interface IHandler<VER, MAK> {
    boolean verifiCode(VER bizVerifiHandler);

    void makeImage(OutputStream outputStream,MAK bizVerifiHandler);
}
