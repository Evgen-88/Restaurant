package com.itrex.kaliaha.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "composition")
public class Composition extends BaseEntity<Long>{
    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "quantity")
    private int quantity;

    public Composition() {}

    public Composition(Dish dish, Ingredient ingredient, int quantity) {
        this.dish = dish;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public Composition(Long id, Dish dish, Ingredient ingredient, int quantity) {
        this(dish, ingredient, quantity);
        super.setId(id);
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {return true;}
        if(object == null || getClass() != object.getClass()) {return false;}
        Composition aThat = (Composition) object;

        if (getId() == null) {
            if (aThat.getId() != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        return getQuantity() == aThat.getQuantity();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + getQuantity();
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + getId() +
                ", quantity=" + getQuantity() +
                "}";
    }
}