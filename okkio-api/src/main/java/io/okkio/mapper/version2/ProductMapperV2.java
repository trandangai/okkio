package io.okkio.mapper.version2;

import io.okkio.domain.version2.ProductV2;
import io.okkio.dto.request.version2.RequestProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductMapperV2 {
    public abstract ProductV2 toEntity(RequestProductDto dto);
}
