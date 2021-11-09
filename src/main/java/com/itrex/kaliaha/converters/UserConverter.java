package com.itrex.kaliaha.converters;

import com.itrex.kaliaha.dto.UserDTO;
import com.itrex.kaliaha.dto.UserListDTO;
import com.itrex.kaliaha.dto.UserSaveDTO;
import com.itrex.kaliaha.dto.UserUpdateDTO;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
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

    public static UserDTO toDTO(User user, List<Role> roles, List<Order> orders) {
        return UserDTO.builder()
                .id(user.getId())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .login(user.getLogin())
                .password(user.getPassword())
                .address(user.getAddress())
                .roles(RoleConverter.toRoleListDTO(roles))
                .orders(OrderConverter.toOrderListDTO(orders))
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