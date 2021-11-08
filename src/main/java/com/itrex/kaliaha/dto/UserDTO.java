package com.itrex.kaliaha.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UserDTO implements DTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String login;
    private String password;
    private String address;

    private List<RoleDTO> roles;
    private List<OrderDTO> orders;
}