package com.itrex.kaliaha.entity;

import com.itrex.kaliaha.enums.OrderStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_order")
public class Order extends BaseEntity<Long> {
    @Column(name = "price")
    private int price;

    @Column(name = "order_date")
    private LocalDate date;

    @Column(name = "address")
    private String address;

    @Column(name = "order_status")
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "order_dish_link",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Dish> dishes = new ArrayList<>();

    public Order() {}

    public Order(int price, LocalDate date, String address, OrderStatus orderStatus,  User user) {
        this.price = price;
        this.date = date;
        this.address = address;
        this.orderStatus = orderStatus;
        this.userId = user.getId();
        this.user = user;
    }

    public Order(Long id, int price, LocalDate date, String address, OrderStatus orderStatus, User user) {
        this(price, date, address, orderStatus, user);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.userId = user.getId();
        this.user = user;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
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

        if (getUserId() == null) {
            if (aThat.getUserId() != null) { return false;}
        } else if (!getUserId().equals(aThat.getUserId())) { return false;}

        if (getOrderStatus() == null) {
            return aThat.getOrderStatus() == null;
        } else return getOrderStatus().equals(aThat.getOrderStatus());
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
                ", userId=" + getUserId() +
                '}';
    }
}