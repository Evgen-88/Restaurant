package com.itrex.kaliaha.repository;

import java.util.List;

public interface BaseRepository<E> {
    E findById(Long id);
    List<E> findAll();
    void add(E e);
    void addAll(List<E> entities);
    boolean update(E e);
    boolean delete(Long id);
}