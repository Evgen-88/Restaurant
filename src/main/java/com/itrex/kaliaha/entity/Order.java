package com.itrex.kaliaha.entity;

import com.itrex.kaliaha.enums.OrderStatus;

import java.time.LocalDate;

public class Order extends BaseEntity<Long> {
    private int price;
    private LocalDate date;
    private String address;
    private OrderStatus orderStatus;
    private Long userId;

    public Order(int price, LocalDate date, String address, OrderStatus orderStatus, Long userId) {
        this.price = price;
        this.date = date;
        this.address = address;
        this.orderStatus = orderStatus;
        this.userId = userId;
    }

    public Order(Long id, int price, LocalDate date, String address, OrderStatus orderStatus, Long userId) {
        this(price, date, address, orderStatus, userId);
        super.setId(id);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {return true;}
        if(object == null || getClass() != object.getClass()) {return false;}
        Order aThat = (Order) object;
        if (getId() == null) {
            if (aThat.getId() != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        if(getPrice() != aThat.getPrice()) {return false;}

        if (getDate() == null) {
            if (aThat.getDate() != null) { return false;}
        } else if (!getDate().equals(aThat.getDate())) { return false;}

        if (getAddress() == null) {
            if (aThat.getAddress() != null) { return false;}
        } else if (!getAddress().equals(aThat.getAddress())) { return false;}

        if (getOrderStatus() == null) {
            if (aThat.getOrderStatus() != null) { return false;}
        } else if (!getOrderStatus().equals(aThat.getOrderStatus())) { return false;}

        if (getUserId() == null) {
            return aThat.getUserId() == null;
        } else return getUserId().equals(aThat.getUserId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + getPrice();
        result = prime * result + (getDate() != null ? getDate().hashCode() : 0);
        result = prime * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = prime * result + (getOrderStatus() != null ? getOrderStatus().hashCode() : 0);
        result = prime * result + (getUserId() != null ? getUserId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + getId() +
                ", price=" + getPrice() +
                ", date=" + getDate() +
                ", address='" + getAddress() + '\'' +
                ", orderStatus=" + getOrderStatus() +
                ", user=" + getUserId() +
                '}';
    }
}