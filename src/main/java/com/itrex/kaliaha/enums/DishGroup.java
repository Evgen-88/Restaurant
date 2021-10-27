package com.itrex.kaliaha.enums;

public enum DishGroup {
    HOT("Горячее"),
    SALAD("Салаты"),
    DRINK("Напитки");

    private String title;

    DishGroup(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}