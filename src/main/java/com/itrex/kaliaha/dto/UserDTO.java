package com.itrex.kaliaha.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Set;

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

    private Set<RoleDTO> roles;
    private List<OrderListDTO> orders;
}