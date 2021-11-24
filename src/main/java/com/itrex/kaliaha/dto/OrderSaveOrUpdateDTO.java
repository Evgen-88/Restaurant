package com.itrex.kaliaha.dto;

import com.itrex.kaliaha.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class OrderSaveOrUpdateDTO implements DTO{
    private Long orderId;
    private int price;
    private Date date;
    private String address;
    private OrderStatus orderStatus;
    private Long userId;
}