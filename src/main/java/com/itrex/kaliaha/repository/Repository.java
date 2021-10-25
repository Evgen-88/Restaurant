package com.itrex.kaliaha.repository;

import java.util.List;

public interface Repository<Entity> {
    Entity findById(Long id);
    List<Entity> findAll();
    void add(Entity entity);
    void addAll(List<Entity> entities);
    boolean update(Entity entity);
    boolean delete(Long id);
}
