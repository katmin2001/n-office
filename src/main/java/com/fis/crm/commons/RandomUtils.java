package com.fis.crm.commons;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtils {
    public static void main(String[] args) {
        System.out.println(generateOtp());
    }
    public static String generateOtp() {
        return RandomStringUtils.random(6, true, true);
    }
}
