package com.jsonljd.common.captcha.utils;

import java.util.Arrays;

/**
 * @Author <a href="mailto:liangjiandong@wxchina.com">JianDong.Liang</a>
 * @Description: iot project IOT_1.0.0.1
 * @Create: 2018-8-21
 */
public class SecurityUtil {
    public static String[] decode(String data) {
        String[] ret = new String[2];
        try {
            byte[] payload;
            payload = org.apache.commons.codec.binary.Base64.decodeBase64(data.substring(1, data.length() - 1).getBytes());

            switch (payload[0]) {
                case 1:
                    int dataLength = ((payload[1] & 0xff) << 24)
                            | ((payload[2] & 0xff) << 16)
                            | ((payload[3] & 0xff) << 8)
                            | (payload[4] & 0xff);
                    int paramsLength = ((payload[5] & 0xff) << 24)
                            | ((payload[6] & 0xff) << 16)
                            | ((payload[7] & 0xff) << 8)
                            | (payload[8] & 0xff);
                    if (payload.length != 1 + 8 + dataLength + paramsLength) {
                        return null;
                    }
                    byte[] dat = Arrays.copyOfRange(payload, 9, 9 + dataLength);

                    byte[] params = Arrays.copyOfRange(payload, 9 + dataLength, payload.length);

                    ret[0] = new String(dat);
                    ret[1] = new String(params);
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String encode(String encryptedStr, String paramsStr) {
        byte PAYLOAD_V1 = 1;
        byte[] encrypted = encryptedStr.getBytes();
        byte[] params = paramsStr.getBytes();
        byte[] payload = new byte[1 + 8 + encrypted.length + params.length];
        int pos = 0;
        payload[pos++] = PAYLOAD_V1;
        payload[pos++] = (byte) (encrypted.length >> 24);
        payload[pos++] = (byte) (encrypted.length >> 16);
        payload[pos++] = (byte) (encrypted.length >> 8);
        payload[pos++] = (byte) (encrypted.length);
        payload[pos++] = (byte) (params.length >> 24);
        payload[pos++] = (byte) (params.length >> 16);
        payload[pos++] = (byte) (params.length >> 8);
        payload[pos++] = (byte) (params.length);
        System.arraycopy(encrypted, 0, payload, pos, encrypted.length);
        pos += encrypted.length;
        System.arraycopy(params, 0, payload, pos, params.length);
        return "{" + new String(org.apache.commons.codec.binary.Base64.encodeBase64String(payload)) + "}";
    }
}
