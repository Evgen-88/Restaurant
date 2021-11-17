package com.itrex.kaliaha.entity;

import com.itrex.kaliaha.enums.Measurement;
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper=true)
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
    @Enumerated(EnumType.STRING)
    private Measurement measurement;

    @ToString.Exclude
    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private List<Composition> compositions;

    @Builder
    public Ingredient(Long id, String ingredientName, int price, int remainder, Measurement measurement, List<Composition> compositions) {
        setId(id);
        this.ingredientName = ingredientName;
        this.price = price;
        this.remainder = remainder;
        this.measurement = measurement;
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
}