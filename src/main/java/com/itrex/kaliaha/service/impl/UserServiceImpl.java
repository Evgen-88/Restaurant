package com.itrex.kaliaha.service.impl;

import com.itrex.kaliaha.converters.UserConverter;
import com.itrex.kaliaha.dto.*;
import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.entity.User;
import com.itrex.kaliaha.exception.ServiceException;
import com.itrex.kaliaha.repository.OrderRepository;
import com.itrex.kaliaha.repository.UserRepository;
import com.itrex.kaliaha.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public UserDTO findById(Long id) throws ServiceException {
        return userRepository.findById(id).map(UserConverter::toDTO)
                .orElseThrow(() -> new ServiceException("User is not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserListDTO> findAll() throws ServiceException {
        return UserConverter.toUserListDTO(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserListDTO> findAll(Pageable pageable) {
        Page<User> pageUsers = userRepository.findAll(pageable);
        List<UserListDTO> users = pageUsers.stream().map(UserConverter::toListDTO).collect(Collectors.toList());
        return new PageImpl<>(users);
    }

    @Transactional
    @Override
    public UserSaveDTO add(UserSaveDTO userSaveDTO) throws ServiceException {
        User user = UserConverter.fromDTO(userSaveDTO);
        return UserConverter.toSaveDTO(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserUpdateDTO update(UserUpdateDTO userUpdateDTO) throws ServiceException {
        Optional<User> userToUpdate = userRepository.findById(userUpdateDTO.getId());
        if (userToUpdate.isPresent()) {
            User user = userToUpdate.get();
            if (userUpdateDTO.getFirstName() != null) {
                user.setFirstName(userUpdateDTO.getFirstName());
            }
            if (userUpdateDTO.getLastName() != null) {
                user.setLastName(userUpdateDTO.getLastName());
            }
            if (userUpdateDTO.getLogin() != null) {
                user.setLogin(userUpdateDTO.getLogin());
            }
            if (userUpdateDTO.getPassword() != null) {
                user.setPassword(userUpdateDTO.getPassword());
            }
            if (userUpdateDTO.getAddress() != null) {
                user.setAddress(userUpdateDTO.getAddress());
            }
            userRepository.flush();
            return UserConverter.toUpdateDTO(user);
        } else {
            throw new ServiceException("User is not updated");
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) throws ServiceException {
        Optional<User> userToDelete = userRepository.findById(id);
        if (userToDelete.isPresent()) {
            User user = userToDelete.get();
            orderRepository.deleteAll(user.getOrders());
            userRepository.delete(user);
            return true;
        } else {
            throw new ServiceException("User is not deleted");
        }
    }

    @Transactional
    @Override
    public boolean addRoleToUser(Long userId, Long roleId) throws ServiceException {
        Optional<User> userToAddRole = userRepository.findById(userId);
        if (userToAddRole.isPresent()) {
            User user = userToAddRole.get();
            user.getRoles().add(Role.builder().id(roleId).build());
            userRepository.flush();
            return true;
        } else {
            throw new ServiceException("Role is not added");
        }
    }

    @Transactional
    @Override
    public boolean deleteRoleFromUser(Long userId, Long roleId) throws ServiceException {
        Optional<User> userToRemoveRole = userRepository.findById(userId);
        if (userToRemoveRole.isPresent()) {
            User user = userToRemoveRole.get();
            user.setRoles(user.getRoles().stream()
                    .filter(role -> !Objects.equals(role.getId(), roleId))
                    .collect(Collectors.toSet()));
            userRepository.flush();
            return true;
        } else {
            throw new ServiceException("Role is not deleted");
        }
    }

    @Override
    public UserDetailsDTO findByLogin(String login) {
        return UserConverter.toUserDetailsDTO(
                userRepository.findByLogin(login)
                        .orElseThrow(() -> new UsernameNotFoundException(format("User: %s, not found", login)))
        );
    }
}