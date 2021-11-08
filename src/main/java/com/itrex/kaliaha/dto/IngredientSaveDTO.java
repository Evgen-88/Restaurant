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
public class IngredientSaveDTO implements DTO {
    private Long id;
    private String ingredientName;
    private int price;
    private int remainder;
    private Measurement measurement;
}