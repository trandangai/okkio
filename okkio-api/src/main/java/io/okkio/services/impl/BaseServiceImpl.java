package io.okkio.services.impl;

import io.okkio.common.AbstractAuditingEntity;
import io.okkio.services.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

public class BaseServiceImpl<T extends AbstractAuditingEntity, ID> extends GenericServiceImpl<T, ID> implements BaseService<T, ID> {
    public BaseServiceImpl(JpaRepository<T, ID> jpaRepository) {
        super(jpaRepository);
    }
}
