package io.okkio.services;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {
    List<T> findAll();

    Optional<T> findOne(ID var1);

    T save(T var1);
}
