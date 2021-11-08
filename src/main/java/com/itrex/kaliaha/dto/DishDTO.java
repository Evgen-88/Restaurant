package com.itrex.kaliaha.dto;

import com.itrex.kaliaha.enums.DishGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class DishDTO implements DTO {
    private String dishName;
    private int price;
    private DishGroup dishGroup;
    private String dishDescription;
    private String imagePath;
    private List<DishIngredientDTO> ingredientList;
}