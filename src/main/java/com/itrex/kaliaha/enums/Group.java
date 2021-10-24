package com.itrex.kaliaha.enums;

public enum Group {
    HOT("Горячее"),
    SALAD("Салаты"),
    DRINK("Напитки");

    private String title;

    Group(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}