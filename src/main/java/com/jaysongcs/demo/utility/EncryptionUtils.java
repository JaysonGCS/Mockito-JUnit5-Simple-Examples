package com.jaysongcs.demo.utility;

public class EncryptionUtils {
    public static String simpleEncrypt(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.reverse();
        return sb.toString();
    }

    public static String notSoSimpleEncrypt(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("123");
        sb.append(message);
        sb.reverse();
        sb.append("123");
        return sb.toString();
    }
}
