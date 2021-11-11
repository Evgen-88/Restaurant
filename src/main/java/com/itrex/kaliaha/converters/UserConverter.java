package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.BaseEntity;
import com.itrex.kaliaha.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    public static List<UserListDTO> toUserListDTO(List<User> users) {
        return users.stream().map(UserConverter::toDTO).collect(Collectors.toList());
    }

    public static UserListDTO toDTO(User user) {
        return UserListDTO.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .login(user.getLogin())
                .address(user.getAddress())
                .build();
    }

    public static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .login(user.getLogin())
                .password(user.getPassword())
                .address(user.getAddress())
                .roles(RoleConverter.toRoleListDTO(user.getRoles()))
                .orders(OrderConverter.toOrderListDTO(user.getOrders()))
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
        User user = new User();
        user.setId(userSaveDTO.getId());
        user.setFirstName(userSaveDTO.getFirstName());
        user.setLastName(userSaveDTO.getLastName());
        user.setLogin(userSaveDTO.getLogin());
        user.setPassword(userSaveDTO.getPassword());
        user.setAddress(userSaveDTO.getAddress());
        return user;
    }

    public static User fromDTO(UserUpdateDTO userUpdateDTO) {
        User user = new User();
        user.setId(userUpdateDTO.getId());
        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setLogin(userUpdateDTO.getLogin());
        user.setPassword(userUpdateDTO.getPassword());
        user.setAddress(userUpdateDTO.getAddress());
        return user;
    }
}