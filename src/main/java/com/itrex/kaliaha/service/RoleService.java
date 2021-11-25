package com.itrex.kaliaha.service;

import com.itrex.kaliaha.dto.RoleDTO;
import com.itrex.kaliaha.exception.ServiceException;

import java.util.List;

public interface RoleService {
    List<RoleDTO> findAll() throws ServiceException;
}