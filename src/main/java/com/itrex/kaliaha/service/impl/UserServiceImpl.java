package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.RoleConverter;
import com.itrex.kaliaha.converters.UserConverter;
import com.itrex.kaliaha.dto.UserDTO;
import com.itrex.kaliaha.dto.UserListDTO;
import com.itrex.kaliaha.dto.UserSaveDTO;
import com.itrex.kaliaha.dto.UserUpdateDTO;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id);
        List<Role> roles = userRepository.findRolesByUserId(id);
        List<Order> orders = orderRepository.findOrdersByUserId(id);
        return UserConverter.toDTO(user, roles, orders);
    }

    public List<UserListDTO> findAll() {
        List<User> users = userRepository.findAll();
        return UserConverter.toUserListDTO(users);
    }

    public void add(UserSaveDTO userSaveDTO) {
        User user = UserConverter.fromDTO(userSaveDTO);
        userRepository.add(user, RoleConverter.fromRoleListIdDTO(userSaveDTO.getRolesId()));
        userSaveDTO.setId(user.getId());
    }

    public void update(UserUpdateDTO userUpdateDTO) throws ServiceException {
        User user = UserConverter.fromDTO(userUpdateDTO);
        if(!userRepository.update(user)) {
            throw new ServiceException("User object is not updated in database", userUpdateDTO);
        }
    }

    public void delete(Long id) throws InvalidIdParameterServiceException {
        if(id == null) {
            throw new InvalidIdParameterServiceException("id parameter shouldn't be null");
        }
        if(!userRepository.delete(id)) {
            throw new InvalidIdParameterServiceException("User wasn't deleted", id);
        }
    }
}