package com.itrex.kaliaha.enums;

public enum OrderStatus {
    NEW("Новый"),
    ACCEPTED("Подтверждён"),
    COOKING("Готовится"),
    COMPLETED("Завершён");

    private String title;

    OrderStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}