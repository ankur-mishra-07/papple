package com.ifocus.papple.helpers;

import android.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Abhishek on 12-01-2016.
 */
public class StringHelpers {
    public static final String HASH_METHOD_MD5 = "MD5";
    public static final String HASH_METHOD_SHA1 = "SHA-1";
    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static String hashString(String method, String string) {

        if (method == HASH_METHOD_SHA1) {
            return computeSHA1(string);
        }
        return computeMD5(string);
    }

    public static String computeSHA1(String string) {
        MessageDigest messageDigest = null;
        String sha1 = string;
        try {
            messageDigest = MessageDigest.getInstance(HASH_METHOD_SHA1);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            if (messageDigest != null) {
                messageDigest.update(string.getBytes("ASCII"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] data = new byte[0];
        if (messageDigest != null) {
            data = messageDigest.digest();
        }

        try {
            sha1 = convertToHex(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sha1;
    }

    public static String computeMD5(String string) {
        StringBuffer MD5 = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(string.getBytes());
            byte messageDigest[] = digest.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5.append(h);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return MD5.toString();

    }

    public static String convertToHex(byte[] data) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        String hex = Base64.encodeToString(data, 0, data.length, 0);

        stringBuilder.append(hex);

        return stringBuilder.toString();
    }

    public static boolean isValidEmail(String string) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }


    public static boolean hasMinLength(String string, int minLength) {
        return string.length() > minLength;
    }

    public static boolean hasMaxLength(String string, int maxLength) {
        return string.length() < maxLength;
    }

    public static boolean hasExactLength(String string, int length) {
        return string.length() == length;
    }

    public static boolean isValidWord(String string, int minLength, int maxLength) {
        Pattern pattern = Pattern.compile("^[a-z0-9_-]{" + minLength + "," + maxLength + "}$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

}

