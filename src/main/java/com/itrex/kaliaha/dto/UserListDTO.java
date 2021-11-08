package com.itrex.kaliaha.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UserListDTO implements DTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String login;
    private String password;
    private String address;
}