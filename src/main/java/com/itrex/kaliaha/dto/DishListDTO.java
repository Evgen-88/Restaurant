package com.itrex.kaliaha.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class DishListDTO implements DTO {
    private String dishName;
    private int price;
}