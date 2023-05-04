package com.fis.crm.domain;

import java.util.HashMap;
import java.util.Map;

public enum Display {
    NONE(0), SHOW(1);

    static Map<Integer, Display> map = new HashMap<>();

    static {
        for (Display a : Display.values()) {
            map.put(a.getNumber(), a);
        }
    }

    private final int number;

    Display(int number) {
        this.number = number;
    }

    public static synchronized Display fromNumber(int number) {
        return map.get(number);
    }


    public int getNumber() {
        return number;
    }
}
