package com.itrex.kaliaha.dto;

import com.itrex.kaliaha.enums.Measurement;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class DishIngredientDTO implements DTO {
    private Long compositionId;
    private Long ingredientId;
    private String ingredientName;
    private Measurement measurement;
    private int quantity;
}