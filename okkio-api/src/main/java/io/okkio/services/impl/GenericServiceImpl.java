package io.okkio.services.impl;

import io.okkio.services.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class GenericServiceImpl<T, ID> implements GenericService<T, ID> {

    private JpaRepository<T, ID> jpaRepository;

    public GenericServiceImpl(JpaRepository<T, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<T> findAll() {
        List<T> entities = this.jpaRepository.findAll();
        return entities;
    }

    public Optional<T> findOne(ID id) {
        Optional<T> entity = this.jpaRepository.findById(id);
        return entity;
    }

    public T save(T entity) {
        T savedEntity = this.jpaRepository.save(entity);
        return savedEntity;
    }
}
