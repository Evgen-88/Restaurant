package com.itrex.kaliaha.entity;

import com.itrex.kaliaha.enums.Measurement;

public class Ingredient extends BaseEntity<Long> {
    private String ingredientName;
    private int price;
    private int remainder;
    private Measurement measurement;

    public Ingredient(String ingredientName, int price, int remainder, Measurement measurement) {
        this.ingredientName = ingredientName;
        this.price = price;
        this.remainder = remainder;
        this.measurement = measurement;
    }

    public Ingredient(Long id, String ingredientName, int price, int remainder, Measurement measurement) {
        this(ingredientName, price, remainder, measurement);
        super.setId(id);
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
                ", ingredientName='" + ingredientName + '\'' +
                ", price=" + price +
                ", remainder=" + remainder +
                ", measurement=" + measurement +
                '}';
    }
}