package com.itrex.kaliaha.repository;

import com.itrex.kaliaha.entity.Composition;

import java.util.List;

public interface CompositionRepository {
    Composition selectById(Long id);
    List<Composition> selectAll();
    void add(Composition composition);
    void addAll(List<Composition> compositions);
    boolean update(Composition composition);
    boolean remove(Long id);
}
