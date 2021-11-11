package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.UserDTO;
import com.itrex.kaliaha.dto.UserListDTO;
import com.itrex.kaliaha.dto.UserSaveDTO;
import com.itrex.kaliaha.dto.UserUpdateDTO;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface UserService {
    UserListDTO findById(Long id);
    List<UserListDTO> findAll();
    UserSaveDTO add(UserSaveDTO userSaveDTO);
    UserUpdateDTO update(UserUpdateDTO userUpdateDTO) throws ServiceException;
    void delete(Long id) throws InvalidIdParameterServiceException;
    UserDTO findWithRolesAndOrdersById(Long id);
}