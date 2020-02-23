package com.jsonljd.common.captcha.api.biz;

import java.io.Serializable;
import java.util.Map;

/**
 * @Classname IBizVerifiCodeHandler
 * @Description TODO
 * @Date 2020/2/19 8:36
 * @Created by JSON.L
 */
public interface IBizVerifiCodeHandler<DAT extends Serializable,IPT extends Serializable>  extends IBizParams {

    /**
     * 获取验证的原始数据
     * @return
     */
    DAT getOrgData();

    /**
     * 获取交互输入的数据
     * @return
     */
    IPT getIptData();
}
