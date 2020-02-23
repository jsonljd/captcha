package com.jsonljd.common.captcha.core;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * @Author <a href="mailto:liangjiandong@wxchina.com">JianDong.Liang</a>
 * @Description: iot project IOT_1.0.0.1
 * @Create: 2018-8-20
 */
public final class DefaultLoadPropFactory {

    public static final String SYSTEMPROPERTIES = "verifi.properties";
    public static final String BG_URL_ONCLICK_LIST = "bg.url.onclick.list";
    public static final String BG_URL_JIGSAW_LIST = "bg.url.jigsaw.list";
    public static final String DEFAULT_FONT = "default.font";
    public static final String BG_URL_PATH = "bg";
    public static final String FONT_URL_PATH = "font";
    public static final String BG_URL_SPLIT = ",";

    private static Logger logger = LoggerFactory.getLogger(DefaultLoadPropFactory.class);
    private static Properties prop = new Properties();
    private static Map<Integer,List<BufferedImage>> cacheImg = null;// new ArrayList<>();
    private static Font defaultFont = null;

    private static Object objSyn = new Object();

    static {
        loadProp();
    }

    private static void loadProp() {
        try {
            prop.clear();
            prop.load(DefaultLoadPropFactory.class.getClassLoader().getResourceAsStream(SYSTEMPROPERTIES));
        } catch (IOException e) {
            logger.error("load verifi.properties failed by : ", e);
        }
    }

    public static String getFontName(){
        return prop.getProperty(DEFAULT_FONT);
    }

    public static Font getFont(){
        if(defaultFont==null) {
            String fontName = prop.getProperty(DEFAULT_FONT);
            defaultFont = new Font(fontName, Font.BOLD, 30);
        }
        return defaultFont;
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }


    private static List<BufferedImage> getList(String urls){
        List<BufferedImage> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(urls)) {
            for (String item : urls.split(BG_URL_SPLIT)) {
                java.net.URL imageURL = DefaultLoadPropFactory.class.getClassLoader().getResource(BG_URL_PATH + "/" + item);
                //java.net.URL imageURL = ClassLoader.getSystemResource(BG_URL_PATH + "/" + item);
                try {
                    list.add(ImageIO.read(imageURL));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private synchronized static void initImg(){
        if(cacheImg==null) {
            synchronized(objSyn) {
                if(cacheImg!=null){
                    return;
                }
                cacheImg = new HashMap<>();
                String urls = getProperty(BG_URL_ONCLICK_LIST);
                cacheImg.put(1, getList(urls));
                urls = getProperty(BG_URL_JIGSAW_LIST);
                cacheImg.put(2, getList(urls));
            }
        }
    }

    public static BufferedImage getSpecify(int type,int k){
        initImg();
        List<BufferedImage> _cacheImg = cacheImg.get(type);
        int index = k % _cacheImg.size();
        BufferedImage ret = _cacheImg.get(index);
        return copyImage(ret);
    }

    public static BufferedImage getRandom(int type) {
        initImg();
        List<BufferedImage> _cacheImg = cacheImg.get(type);
        int index = Double.valueOf(Math.random()*_cacheImg.size()).intValue();
        BufferedImage ret = _cacheImg.get(index);
        return copyImage(ret);
    }

    private static BufferedImage copyImage(BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
}
