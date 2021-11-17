package com.itrex.kaliaha;

import com.itrex.kaliaha.config.ApplicationContextConfiguration;

import com.itrex.kaliaha.entity.Role;
import com.itrex.kaliaha.repository.BaseRepository;
import com.itrex.kaliaha.repository.impl.RoleRepositoryImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        BaseRepository<Role> roleRepository = ctx.getBean(RoleRepositoryImpl.class);
        try{
            roleRepository.add(Role.builder().roleName("admin").build());
        } catch (Exception ex) {
            System.out.print("");
        }
        Thread.sleep(100);
        roleRepository.findById(1L);
    }
}