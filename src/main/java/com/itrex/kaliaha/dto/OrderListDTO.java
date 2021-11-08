package com.itrex.kaliaha.dto;

import com.itrex.kaliaha.enums.OrderStatus;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class OrderListDTO {
    private Long orderId;
    private int price;
    private LocalDate date;
    private String address;
    private OrderStatus orderStatus;
    private Long userId;
}