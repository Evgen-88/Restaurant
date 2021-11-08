package com.itrex.kaliaha.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class RoleDTO implements DTO {
    private Long id;
    private String roleName;
}