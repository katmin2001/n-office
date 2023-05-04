package com.fis.crm.commons;

import java.util.regex.Pattern;

public class CheckCharacterUtil {
    public static boolean hasUppercaseCharacter(String s) {
        Pattern p = Pattern.compile("[A-Z]");
        return p.matcher(s).find();
    }

    public static boolean hasLowercaseCharacter(String s) {
        Pattern p = Pattern.compile("[a-z]");
        return p.matcher(s).find();
    }

    public static boolean hasNumber(String s) {
        Pattern p = Pattern.compile("[0-9]");
        return p.matcher(s).find();
    }

    public static boolean checkCharacterNumber(String s) {
        if (!s.equals("0") && !s.equals("1") && !s.equals("2") && !s.equals("3")
            && !s.equals("4") && !s.equals("5") && !s.equals("6")
            && !s.equals("7") && !s.equals("8") && !s.equals("9")) return false;
        return true;
    }

    public static boolean checkNumber(String s){
        Pattern p = Pattern.compile("^[0-9]+$");
        return p.matcher(s).find();
    }

    public static boolean hasSpecialCharacter(String s) {
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        return p.matcher(s).find();
    }

    public static boolean checkEmail(String s){
        Pattern p = Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        return p.matcher(s).find();
    }
}
