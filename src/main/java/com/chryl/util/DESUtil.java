package com.chryl.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESUtil {

    private static Logger logger = LoggerFactory.getLogger(DESUtil.class);

    /**
     * 加密
     *
     * @param pin
     * @param key
     * @return
     */
    public static String encrypt(String pin, String key) {
        String src = "                  ";
        src = (pin + src).substring(0, 16);
        DES des = new DES();
        byte[] pwd1 = des.desAll(DES.EN0, src.getBytes(), key.getBytes());
        byte[] pwd2 = "                ".getBytes();
        byte[] pwd = new byte[32];
        System.arraycopy(pwd1, 0, pwd, 0, 16);
        System.arraycopy(pwd2, 0, pwd, 16, 16);
        BASE64Encoder en = new BASE64Encoder();
        String ret = en.encode(pwd);
        return ret;
    }

    /**
     * 解密
     *
     * @param pin
     * @param key
     * @return
     */
    public static String decrypt(String pin, String key) {
        BASE64Decoder de = new BASE64Decoder();
        byte[] src1 = null;
        try {
            src1 = de.decodeBuffer(pin);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        byte[] src = new byte[16];
        System.arraycopy(src1, 0, src, 0, 16);
        DES des = new DES();
        byte[] bTmp = des.desAll(DES.DE1, src, key.getBytes());
        String ret = new String(bTmp);
        return ret.trim();
    }

    public static boolean getKeyStr(String clearPwd, String key) {
        boolean back = false;
        if (key.length() < 8) {
        }
        key = key.substring(key.length() - 8);
        decrypt(clearPwd, key);
        return back;
    }

    public static void main(String[] args) {
        String encStr = encrypt("chryl", "chryl0527");
        System.out.println("umcp ret=" + encStr);
        System.out.println("dnc=" + decrypt(encStr, "chryl0527"));
    }

}
