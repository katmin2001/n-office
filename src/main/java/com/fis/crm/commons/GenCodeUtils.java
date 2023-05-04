package com.fis.crm.commons;

import java.util.Random;

public class GenCodeUtils {
    private  static final Random generator = new Random();
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String specials = "~=+%^*/()[]{}/!@#$?|";
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;
    private static final String ALL = alpha + alphaUpperCase + digits + specials;

    public static String genCodeForCampaignScript(){
        int a = generator.nextInt();
        while(a<0){
            a = generator.nextInt();
        }
        return "CS" + a;
    }
    public static String genCodeForQuestion(){
        int a = generator.nextInt();
        while(a<0){
            a = generator.nextInt();
        }
        return "MCH" + a;
    }
    public static String genCodeForAnswer(){
        int a = generator.nextInt();
        while(a<0){
            a = generator.nextInt();
        }
        return "MCTL" + a;
    }

    public static String genPassword(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int number = generator.nextInt(ALPHA_NUMERIC.length());
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    public static String genSessionId(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int number = generator.nextInt(ALPHA_NUMERIC.length());
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString().toLowerCase();
    }

    public static String genCodeTestSMS(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int number = generator.nextInt(ALPHA_NUMERIC.length());
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }
}
