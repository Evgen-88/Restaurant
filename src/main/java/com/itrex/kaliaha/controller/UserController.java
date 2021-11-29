package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends AbstractController{
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @GetMapping
    public ResponseEntity<Page<UserListDTO>> findAll(Pageable pageable) throws ServiceException {
        Page<UserListDTO> users = userService.findAll(pageable);
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserSaveDTO user) {
        try {
            return new ResponseEntity<>(userService.add(user), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            return new ResponseEntity<>(userService.update(userUpdateDTO), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<?> deleteRoleFromUser(@PathVariable long userId, @PathVariable long roleId) {
        try {
            return new ResponseEntity<>(userService.deleteRoleFromUser(userId, roleId), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }

    @PutMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<?> addRoleToUser(@PathVariable long userId, @PathVariable long roleId) {
        try {
            return new ResponseEntity<>(userService.addRoleToUser(userId, roleId), HttpStatus.OK);
        } catch (ServiceException ex) {
            return getResponseEntityForException(ex);
        }
    }
}