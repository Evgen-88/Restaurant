package com.itrex.kaliaha.enums;

public enum Measurement {
    KILOGRAM("Килограмм"),
    GRAM("Грамм"),
    LITER("Литр"),
    MILLILITER("Миллилитр");

    private String title;

    Measurement(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}