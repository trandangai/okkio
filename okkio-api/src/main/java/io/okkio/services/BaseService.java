package io.okkio.services;


import io.okkio.common.AbstractAuditingEntity;

public interface BaseService<T extends AbstractAuditingEntity, ID> extends GenericService<T, ID> {
}
