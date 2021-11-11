package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.RoleConverter;
import com.itrex.kaliaha.converters.UserConverter;
import com.itrex.kaliaha.dto.UserDTO;
import com.itrex.kaliaha.dto.UserListDTO;
import com.itrex.kaliaha.dto.UserSaveDTO;
import com.itrex.kaliaha.dto.UserUpdateDTO;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserListDTO findById(Long id) {
        return UserConverter.toDTO(userRepository.findById(id));
    }

    @Override
    public UserDTO findWithRolesAndOrdersById(Long id) {
        return UserConverter.toUserDTO(userRepository.findWithRolesAndOrdersById(id));
    }

    @Override
    public List<UserListDTO> findAll() {
        List<User> users = userRepository.findAll();
        return UserConverter.toUserListDTO(users);
    }

    @Override
    public UserSaveDTO add(UserSaveDTO userSaveDTO) {
        User user = UserConverter.fromDTO(userSaveDTO);
        user = userRepository.add(user, RoleConverter.fromRoleListIdDTO(userSaveDTO.getRolesId()));
        return UserConverter.toSaveDTO(user);
    }

    @Override
    public UserUpdateDTO update(UserUpdateDTO userUpdateDTO) throws ServiceException {
        User user = UserConverter.fromDTO(userUpdateDTO);
        return UserConverter.toUpdateDTO(userRepository.update(user));
    }

    @Override
    public void delete(Long id) throws InvalidIdParameterServiceException {
        if(id == null) {
            throw new InvalidIdParameterServiceException("id parameter shouldn't be null");
        }
        if(!userRepository.delete(id)) {
            throw new InvalidIdParameterServiceException("User wasn't deleted", id);
        }
    }
}