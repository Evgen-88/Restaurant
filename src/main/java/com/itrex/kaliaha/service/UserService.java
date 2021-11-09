package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.UserDTO;
import com.itrex.kaliaha.dto.UserListDTO;
import com.itrex.kaliaha.dto.UserSaveDTO;
import com.itrex.kaliaha.dto.UserUpdateDTO;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface UserService {
    UserDTO findById(Long id);
    List<UserListDTO> findAll();
    void add(UserSaveDTO userSaveDTO);
    void update(UserUpdateDTO userUpdateDTO) throws ServiceException;
    void delete(Long id) throws InvalidIdParameterServiceException;
}