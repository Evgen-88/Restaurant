package com.itrex.kaliaha.entity;

import com.itrex.kaliaha.enums.DishGroup;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

import java.util.List;

@Entity
@Table(name = "dish")
public class Dish extends BaseEntity<Long> {
    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "price")
    private int price;

    @Column(name = "dish_group")
    @Enumerated(EnumType.STRING)
    private DishGroup dishGroup;

    @Column(name = "dish_description")
    private String dishDescription;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToMany(mappedBy = "dishes")
    private List<Order> orders;

    @OneToMany(mappedBy = "dish")
    @Fetch(FetchMode.SUBSELECT)
    private List<Composition> compositions;

    public Dish() {}

    public Dish(Long id) {
        super.setId(id);
    }

    public Dish (String dishName, int price, DishGroup dishGroup, String dishDescription, String imagePath) {
        this.dishName = dishName;
        this.price = price;
        this.dishGroup = dishGroup;
        this.dishDescription = dishDescription;
        this.imagePath = imagePath;
    }

    public Dish(Long id, String dishName, int price, DishGroup dishGroup, String dishDescription, String imagePath) {
        this(dishName, price, dishGroup, dishDescription, imagePath);
        setId(id);
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public DishGroup getDishGroup() {
        return dishGroup;
    }

    public void setDishGroup(DishGroup dishGroup) {
        this.dishGroup = dishGroup;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String description) {
        this.dishDescription = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Composition> getCompositions() {
        return compositions;
    }

    public void setCompositions(List<Composition> compositions) {
        this.compositions = compositions;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {return true;}
        if(object == null || getClass() != object.getClass()) {return false;}
        Dish aThat = (Dish) object;
        if(getPrice() != aThat.getPrice()) {return false;}

        if (getId() == null) {
            if (aThat.getId() != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        if (getDishName() == null) {
            if (aThat.getDishName() != null) { return false;}
        } else if (!getDishName().equals(aThat.getDishName())) { return false;}

        if (getDishGroup() == null) {
            if (aThat.getDishGroup() != null) { return false;}
        } else if (!getDishGroup().equals(aThat.getDishGroup())) { return false;}

        if (getDishDescription() == null) {
            if (aThat.getDishDescription() != null) { return false;}
        } else if (!getDishDescription().equals(aThat.getDishDescription())) { return false;}

        if (getImagePath() == null) {
            return aThat.getImagePath() == null;
        } else return getImagePath().equals(aThat.getImagePath());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + getPrice();
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + (getDishName() != null ? getDishName().hashCode() : 0);
        result = prime * result + (getDishGroup() != null ? getDishGroup().hashCode() : 0);
        result = prime * result + (getDishDescription() != null ? getDishDescription().hashCode() : 0);
        result = prime * result + (getImagePath() != null ? getImagePath().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + getId() +
                ", dishName='" + getDishName() + '\'' +
                ", price=" + getPrice() +
                ", group=" + getDishGroup() +
                ", description='" + getDishDescription() + '\'' +
                ", imagePath='" + getImagePath() + '\'' +
                '}';
    }
}