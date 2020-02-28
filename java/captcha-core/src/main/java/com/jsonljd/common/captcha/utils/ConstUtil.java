package com.jsonljd.common.captcha.utils;

/**
 * @Classname ConstUtil
 * @Description TODO
 * @Date 2020/2/20 9:04
 * @Created by JSON.L
 */
public class ConstUtil {
    public static final String CONS_PUZZLE_HANDLER = "PUZZLE_HANDLER";
    public static final String IMG_Y_OFFSET = "i_y";
    public static final String IMG_X_OFFSET = "i_x";
    public static final String IMG_SIZE = "i_s";
    public static final String IMG_RANDOM_ARR = "i_arr";
    public static final String IMG_CUT_SPLIE = "i_c_s";
    public static final String IMG_CUT_WIDTH = "2";

    public static final String KEY_IMG_BG_WIDTH = "bgWidth";
    public static final String KEY_IMG_BG_HEIGHT = "bgHight";
    public static final String KEY_IMG_JIGSAW_WIDTH = "jigsawWidth";
    public static final String KEY_IMG_JIGSAW_HEIGHT = "jigsawHight";

    public static final String KEY_POINT_DIS_RADIUS = "disRadius";
    public static final String KEY_IMG_PUZZLE_RADIUS = "puzzleRadius";
    public static final String KEY_IMG_PUZZLE_SPLIT_LINE = "puzzleSplitLine";

    public static final int DEF_LONG_IMG_BG_WIDTH = 300; //背景图宽
    public static final int DEF_LONG_IMG_BG_HEIGHT = 130; //背景图高
    public static final Double DEF_POINT_DIS_RADIUS = 60D; //点击图交互冲突的圆半径
    //拼图块的宽高 = DEF_IMG_PUZZLE_RADIUS*3+DEF_IMG_PUZZLE_SPLIT_LINE*2
    public static final Float DEF_IMG_PUZZLE_RADIUS = 5F; //拼图的圆半径
    public static final Float DEF_IMG_PUZZLE_SPLIT_LINE = 15F; //拼图的线段长度


    public static final String CONS_POINT_CLICK_HANDLER = "POINT_CLICK_HANDLER";
    public static final String CONS_POINT_CLICK_TIP_FONT_SIZE = "CONS_POINT_CLICK_TIP_FONT_SIZE";

    public static final String IS_MIX_IMAGE = "b_m_i";
    public static final String CON_BG_HANDLER = "BG_HANDLER";
}
