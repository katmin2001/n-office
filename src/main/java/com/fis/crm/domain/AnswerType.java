package com.fis.crm.domain;

import java.util.HashMap;
import java.util.Map;

public enum AnswerType {
    ONE(0), MULTI(1), TEXT(2), RANK(3);

    static Map<Integer, AnswerType> map = new HashMap<>();

    static {
        for (AnswerType type : AnswerType.values()) {
            map.put(type.getNumber(), type);
        }
    }

    private final int number;

    AnswerType(int number) {
        this.number = number;
    }

    public static synchronized AnswerType fromNumber(int number) {
        return map.get(number);
    }


    public int getNumber() {
        return number;
    }
}
