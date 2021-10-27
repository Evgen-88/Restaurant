package com.itrex.kaliaha.entity;

import com.itrex.kaliaha.enums.Measurement;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
@Table(name = "ingredient")
public class Ingredient extends BaseEntity<Long> {
    @Column(name = "ingredient_name")
    private String ingredientName;

    @Column(name = "price")
    private int price;

    @Column(name = "remainder")
    private int remainder;

    @Column(name = "measurement")
    @Enumerated(EnumType.ORDINAL)
    private Measurement measurement;

    @OneToMany(mappedBy = "ingredient")
    @Fetch(FetchMode.SUBSELECT)
    private List<Composition> compositions;

    public Ingredient() {}

    public Ingredient(Long id) {
        setId(id);
    }

    public Ingredient(String ingredientName, int price, int remainder, Measurement measurement) {
        this.ingredientName = ingredientName;
        this.price = price;
        this.remainder = remainder;
        this.measurement = measurement;
    }

    public Ingredient(Long id, String ingredientName, int price, int remainder, Measurement measurement) {
        this(ingredientName, price, remainder, measurement);
        setId(id);
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRemainder() {
        return remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
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
        Ingredient aThat = (Ingredient) object;
        if(getPrice() != aThat.getPrice()) {return false;}
        if(getRemainder() != aThat.getRemainder()) {return false;}

        if (getId() == null) {
            if (aThat.getId() != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        if (getIngredientName() == null) {
            if (aThat.getIngredientName() != null) { return false;}
        } else if (!getIngredientName().equals(aThat.getIngredientName())) { return false;}

        if (getMeasurement() == null) {
            return aThat.getMeasurement() == null;
        } else return getMeasurement().equals(aThat.getMeasurement());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + getPrice();
        result = prime * result + getRemainder();
        result = prime * result + (getIngredientName() != null ? getIngredientName().hashCode() : 0);
        result = prime * result + (getMeasurement() != null ? getMeasurement().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + getId() +
                ", ingredientName='" + getIngredientName() + '\'' +
                ", price=" + getPrice() +
                ", remainder=" + getRemainder() +
                ", measurement=" + getMeasurement() +
                '}';
    }
}