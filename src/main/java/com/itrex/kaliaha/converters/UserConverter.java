package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.BaseEntity;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;

import java.util.ArrayList;
import java.util.List;
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
                .orders(OrderConverter.toListUserOrderListDTO(new ArrayList<>(user.getOrders())))
                .build();
    }

    public static List<UserListDTO> toUserListDTO(List<User> users) {
        return users.stream().map(UserConverter::toListDTO).collect(Collectors.toList());
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
                .rolesId(user.getRoles().stream().map(BaseEntity::getId).collect(Collectors.toSet()))
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
                .roles(userSaveDTO.getRolesId().stream().map(roleId -> Role.builder().id(roleId).build()).collect(Collectors.toSet()))
                .build();
    }
}