package com.itrex.kaliaha.repository.impl;

import com.itrex.kaliaha.entity.Composition;
import com.itrex.kaliaha.repository.BaseRepositoryTest;
import com.itrex.kaliaha.repository.CompositionRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JDBCCompositionRepositoryImplTest extends BaseRepositoryTest {
    private final CompositionRepository compositionRepository;
    private final List<Composition> compositions;

    public JDBCCompositionRepositoryImplTest() {
        super();
        compositionRepository = new JDBCCompositionRepositoryImpl(getConnectionPool());
        compositions = new ArrayList<>() {{
            add(new Composition(1L,1L, 1L, 100));
            add(new Composition(2L,1L, 2L, 450));
            add(new Composition(3L,1L, 3L, 43));
            add(new Composition(4L,2L, 1L, 132));
            add(new Composition(5L,2L, 2L, 12));
            add(new Composition(6L,3L, 4L, 100));
            add(new Composition(7L,3L, 1L, 450));
            add(new Composition(8L,3L, 5L, 43));
            add(new Composition(9L,3L, 2L, 132));
        }};
        
    }

    @Test
    public void selectById_validData_shouldReturnCompositionById() {
        //given
        Composition expected = compositions.get(0);
        //when
        Composition actual = compositionRepository.selectById(expected.getId());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void selectAll_validData_shouldReturnAllCompositions() {
        //given && when
        List<Composition> actual = compositionRepository.selectAll();
        //then
        Assert.assertEquals(compositions, actual);
    }

    @Test
    public void add_validData_shouldAddNewComposition() {
        //given
        Composition expected = new Composition(10L, 2L, 4L, 100);
        //when
        Composition actual = new Composition(2L, 4L, 100);
        compositionRepository.add(actual);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addAll_validData_shouldAddNewCompositions() {
        //given
        List<Composition> expected = new ArrayList<>() {{
            add(new Composition(10L, 2L, 4L, 50));
            add(new Composition(11L, 2L, 5L, 150));
        }};
        //when
        List<Composition> actual = new ArrayList<>() {{
            add(new Composition(2L, 4L, 50));
            add(new Composition(2L, 5L, 150));
        }};
        compositionRepository.addAll(actual);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void update_validData_shouldUpdateAndReturnNewComposition() {
        //given
        Composition expected = new Composition(1L, 1L, 1L, 200);
        //when
        Composition actual = compositions.get(0);
        actual.setQuantity(200);
        compositionRepository.update(actual);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void remove_validData_shouldRemoveComposition() {
        //given && when
        Composition actual = compositions.get(0);
        //then
        Assert.assertTrue(compositionRepository.remove(actual.getId()));
    }
}