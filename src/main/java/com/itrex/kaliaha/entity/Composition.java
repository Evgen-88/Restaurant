package com.itrex.kaliaha.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper=true)
@Entity
@Table(name = "composition")
public class Composition extends BaseEntity<Long>{
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "quantity")
    private int quantity;

    @Builder
    public Composition(Long id, Dish dish, Ingredient ingredient, int quantity) {
        setId(id);
        this.dish = dish;
        this.ingredient = ingredient;
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

        if (getDish() == null) {
            if (aThat.getDish() != null) { return false;}
        } else if (getDish().getId() == null) {
            if (aThat.getDish() != null && aThat.getDish().getId() != null) { return false;}
        } else if (!getDish().getId().equals(aThat.getDish().getId())) { return false;}

        if (getIngredient() == null) {
            if (aThat.getIngredient() != null) { return false;}
        } else if (getIngredient().getId() == null) {
            if (aThat.getIngredient() != null && aThat.getIngredient().getId() != null) { return false;}
        } else if (!getIngredient().getId().equals(aThat.getIngredient().getId())) { return false;}

        return getQuantity() == aThat.getQuantity();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (getId() != null ? getId().hashCode() : 0);
        result = prime * result + (getDish() != null
                && getDish().getId() != null ? getDish().getId().hashCode() : 0);
        result = prime * result + (getIngredient() != null
                && getIngredient().getId() != null ? getIngredient().getId().hashCode() : 0);
        result = prime * result + getQuantity();
        return result;
    }
}