package com.itrex.kaliaha.controller;

import com.itrex.kaliaha.dto.RoleDTO;
import com.itrex.kaliaha.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    public List<RoleDTO> getRoles() {
        return roleService.findAll();
    }
}