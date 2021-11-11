package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.BaseEntity;

import java.util.List;

public interface BaseRepository<E extends BaseEntity<Long>> {
    E findById(Long id);
    List<E> findAll();
    E add(E e);
    E update(E e);
    boolean delete(Long id);
}