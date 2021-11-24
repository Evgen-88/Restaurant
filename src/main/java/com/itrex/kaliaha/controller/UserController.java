package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<UserListDTO> getUsers() {
        return userService.findAll();
    }

    @PostMapping(params = {"lastName", "firstName", "login", "password", "address", "rolesId"})
    public UserSaveDTO addUser(UserSaveDTO userSaveDTO) {
        return userService.add(userSaveDTO);
    }

    @PutMapping(params = {"id", "lastName", "firstName", "login", "password", "address"})
    public UserUpdateDTO updateUser(UserUpdateDTO userUpdateDTO) throws ServiceException {
        return userService.update(userUpdateDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws InvalidIdParameterServiceException {
        userService.delete(id);
    }

    @PostMapping("/{userId}/{roleId}")
    public void addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) throws InvalidIdParameterServiceException {
        userService.addRoleToUser(userId, roleId);
    }

    @DeleteMapping("/{userId}/{roleId}")
    public void deleteRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) throws InvalidIdParameterServiceException {
        userService.deleteRoleFromUser(userId, roleId);
    }
}