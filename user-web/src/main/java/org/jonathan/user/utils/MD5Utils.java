package org.jonathan.user.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * @description: md5加密
 * @author: Jonathan.Hu
 * @since:
 * @create: 2021-03-28 22:03
 **/
public class MD5Utils {

    private static final Logger logger = Logger.getLogger(MD5Utils.class.getName());

    /**
     * Md 5 string.
     *
     * @param src the src
     * @return the string
     */
    public static String encode(final String src) {
        try {
            return md5(src, StandardCharsets.UTF_8.name());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.severe("md5 encode fail, " + e.getMessage());
        }
        return src;
    }

    /**
     * Md 5 string.
     *
     * @param src     the src
     * @param charset the charset
     * @return the string
     */
    private static String md5(final String src, final String charset) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5;
        StringBuilder hexValue = new StringBuilder(32);
        md5 = MessageDigest.getInstance("MD5");
        byte[] byteArray;
        byteArray = src.getBytes(charset);
        byte[] md5Bytes = md5.digest(byteArray);
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
