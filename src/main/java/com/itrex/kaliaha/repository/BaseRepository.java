package com.itrex.kaliaha.repository;

import java.util.List;

public interface BaseRepository<E> {
    E findById(Long id);
    List<E> findAll();
    boolean add(E e);
    boolean update(E e);
    boolean delete(Long id);
}