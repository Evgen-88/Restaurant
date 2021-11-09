package com.itrex.kaliaha.dto;

import com.itrex.kaliaha.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class OrderDTO implements DTO {
    private Long orderId;
    private int price;
    private LocalDate date;
    private String address;
    private OrderStatus orderStatus;

    private UserListDTO userListDTO;
    private List<DishListDTO> orderedDishes;
}