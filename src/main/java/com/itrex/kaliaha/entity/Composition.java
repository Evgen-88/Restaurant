package com.itrex.kaliaha.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "composition")
public class Composition extends BaseEntity<Long>{
    @Column(name = "dish_id", insertable = false, updatable = false)
    private Long dishId;

    @Column(name = "ingredient_id", insertable = false, updatable = false)
    private Long ingredientId;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    public Composition() {}

    public Composition(Long dishId, Long ingredientId, int quantity) {
        this.dishId = dishId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
    }

    public Composition(Long id, Long dishId, Long ingredientId, int quantity) {
        this(dishId, ingredientId, quantity);
        super.setId(id);
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
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
        if(getQuantity() != aThat.getQuantity()){return false;}

        if (getId() == null) {
            if (aThat.getId() != null) { return false;}
        } else if (!getId().equals(aThat.getId())) { return false;}

        if (getDishId() == null) {
            if (aThat.getDishId() != null) { return false;}
        } else if (!getDishId().equals(aThat.getDishId())) { return false;}

        if (getIngredientId() == null) {
            return aThat.getIngredientId() == null;
        } else return getIngredientId().equals(aThat.getIngredientId());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + (getDishId() != null ? getDishId().hashCode() : 0);
        result = prime * result + (getIngredientId() != null ? getIngredientId().hashCode() : 0);
        result = prime * result + getQuantity();
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + getId() +
                ", dish=" + getDishId() +
                ", ingredient=" + getIngredientId() +
                ", quantity=" + getQuantity() +
                "}";
    }
}