package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.BaseEntity;
import com.itrex.kaliaha.exception.RepositoryException;

import java.util.List;

public interface BaseRepository<E extends BaseEntity<Long>> {
    E findById(Long id) throws RepositoryException;
    List<E> findAll() throws RepositoryException;
    E add(E e) throws RepositoryException;
    E update(E e) throws RepositoryException;
    boolean delete(Long id) throws RepositoryException;
}