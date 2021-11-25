package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.UserDTO;
import com.itrex.kaliaha.dto.UserListDTO;
import com.itrex.kaliaha.dto.UserSaveDTO;
import com.itrex.kaliaha.dto.UserUpdateDTO;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface UserService {
    UserDTO findById(Long id) throws ServiceException;
    List<UserListDTO> findAll() throws ServiceException;
    UserSaveDTO add(UserSaveDTO userSaveDTO) throws ServiceException;
    UserUpdateDTO update(UserUpdateDTO userUpdateDTO) throws ServiceException;
    boolean delete(Long id) throws ServiceException;
    boolean addRoleToUser(Long userId, Long roleId) throws ServiceException;
    boolean deleteRoleFromUser(Long userId, Long roleId) throws ServiceException;
}