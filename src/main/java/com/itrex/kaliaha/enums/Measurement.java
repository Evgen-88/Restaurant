package com.itrex.kaliaha.enums;

public enum Measurement {
    KILOGRAM(1000),
    GRAM(1),
    LITER(1000),
    MILLILITER(1);

    private int value;

    Measurement(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}