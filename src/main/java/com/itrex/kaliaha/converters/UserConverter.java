package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.BaseEntity;
import com.itrex.kaliaha.entity.User;

import java.util.stream.Collectors;

public class UserConverter {
    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .login(user.getLogin())
                .password(user.getPassword())
                .address(user.getAddress())
                .roles(RoleConverter.toRoleSetDTO(user.getRoles()))
                .orders(OrderConverter.toOrderListDTO(user.getOrders()))
                .build();
    }

    public static UserListDTO toListDTO(User user) {
        return UserListDTO.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .login(user.getLogin())
                .address(user.getAddress())
                .build();
    }

    public static UserSaveDTO toSaveDTO(User user) {
        return UserSaveDTO.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .login(user.getLogin())
                .password(user.getPassword())
                .address(user.getAddress())
                .rolesId(user.getRoles().stream().map(BaseEntity::getId).collect(Collectors.toList()))
                .build();
    }

    public static UserUpdateDTO toUpdateDTO(User user) {
        return UserUpdateDTO.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .login(user.getLogin())
                .password(user.getPassword())
                .address(user.getAddress())
                .build();
    }

    public static User fromDTO(UserSaveDTO userSaveDTO) {
        return User.builder()
                .id(userSaveDTO.getId())
                .firstName(userSaveDTO.getFirstName())
                .lastName(userSaveDTO.getLastName())
                .login(userSaveDTO.getLogin())
                .password(userSaveDTO.getPassword())
                .address(userSaveDTO.getAddress())
                .build();
    }

    public static User fromDTO(UserUpdateDTO userUpdateDTO) {
        return User.builder()
                .id(userUpdateDTO.getId())
                .firstName(userUpdateDTO.getFirstName())
                .lastName(userUpdateDTO.getLastName())
                .login(userUpdateDTO.getLogin())
                .password(userUpdateDTO.getPassword())
                .address(userUpdateDTO.getAddress())
                .build();
    }
}