package com.itrex.kaliaha.entity;

import com.itrex.kaliaha.enums.DishGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper=true)
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

    @ToString.Exclude
    @ManyToMany(mappedBy = "dishes", fetch = FetchType.LAZY)
    private List<Order> orders;

    @ToString.Exclude
    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY)
    private List<Composition> compositions;

    @Builder
    public Dish(Long id, String dishName, int price, DishGroup dishGroup, String dishDescription, String imagePath, List<Order> orders, List<Composition> compositions) {
        setId(id);
        this.dishName = dishName;
        this.price = price;
        this.dishGroup = dishGroup;
        this.dishDescription = dishDescription;
        this.imagePath = imagePath;
        this.orders = orders;
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
}