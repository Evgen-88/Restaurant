package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.RoleConverter;
import com.itrex.kaliaha.converters.UserConverter;
import com.itrex.kaliaha.dto.UserDTO;
import com.itrex.kaliaha.dto.UserListDTO;
import com.itrex.kaliaha.dto.UserSaveDTO;
import com.itrex.kaliaha.dto.UserUpdateDTO;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO findById(Long id) throws ServiceException {
        try {
            return UserConverter.toDTO(userRepository.findById(id));
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public List<UserListDTO> findAll() throws ServiceException {
        try {
            List<User> users = userRepository.findAll();
            return users.stream().map(UserConverter::toListDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public UserSaveDTO add(UserSaveDTO userSaveDTO) throws ServiceException {
        try {
            User user = UserConverter.fromDTO(userSaveDTO);
            user = userRepository.add(user, RoleConverter.fromRoleListIdDTO(userSaveDTO.getRolesId()));
            return UserConverter.toSaveDTO(user);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public UserUpdateDTO update(UserUpdateDTO userUpdateDTO) throws ServiceException {
        try {
            User user = UserConverter.fromDTO(userUpdateDTO);
            return UserConverter.toUpdateDTO(userRepository.update(user));
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return userRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public boolean addRoleToUser(Long userId, Long roleId) throws ServiceException {
        try {
            return userRepository.addRoleToUser(userId, roleId);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }

    }

    @Override
    public boolean deleteRoleFromUser(Long userId, Long roleId) throws ServiceException {
        try {
            return userRepository.deleteRoleFromUser(userId, roleId);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage(), ex.getCause());
        }
    }
}