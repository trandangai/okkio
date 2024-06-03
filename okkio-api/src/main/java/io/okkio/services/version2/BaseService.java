package io.okkio.services.version2;


import io.okkio.common.AbstractAuditingEntity;
import io.okkio.services.GenericService;

public interface BaseService<T extends AbstractAuditingEntity, ID> extends GenericService<T, ID> {
}
