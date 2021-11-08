package com.itrex.kaliaha.dto;

import com.itrex.kaliaha.enums.DishGroup;
import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class DishSaveDTO implements DTO {
    private Long id;
    private String dishName;
    private int price;
    private DishGroup dishGroup;
    private String dishDescription;
    private String imagePath;
    private Map<Long, Integer> ingredients;
}