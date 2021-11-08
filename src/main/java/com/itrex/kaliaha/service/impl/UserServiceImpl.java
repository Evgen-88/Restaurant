package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.dto.UserDTO;
import com.itrex.kaliaha.dto.UserListDTO;
import com.itrex.kaliaha.dto.UserSaveDTO;
import com.itrex.kaliaha.entity.Order;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.InvalidIdParameterServiceException;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.service.UserService;
import com.itrex.kaliaha.util.DTOParser;
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
        return DTOParser.toDTO(user, roles, orders);
    }

    public List<UserListDTO> findAll() {
        List<User> users = userRepository.findAll();
        return DTOParser.toUserListDTO(users);
    }

    public void add(UserSaveDTO userSaveDTO) {
        User user = DTOParser.fromDTO(userSaveDTO);
        userRepository.add(user, DTOParser.fromRoleListIdDTO(userSaveDTO.getRolesId()));
        userSaveDTO.setId(user.getId());
    }

    public void update(UserSaveDTO userSaveDTO) throws ServiceException {
        User user = DTOParser.fromDTO(userSaveDTO);
        if(!userRepository.update(user)) {
            throw new ServiceException("User object is not updated in database", userSaveDTO);
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