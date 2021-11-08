package com.itrex.kaliaha.dto;

import com.itrex.kaliaha.enums.OrderStatus;
import lombok.*;

import java.time.LocalDate;

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
    private Long userId;
}