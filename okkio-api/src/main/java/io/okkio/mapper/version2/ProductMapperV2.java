package io.okkio.mapper.version2;

import io.okkio.domain.version2.ProductV2;
import io.okkio.dto.request.version2.RequestProductDto;
import io.okkio.dto.response.version2.ResponseProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapperV2 {
    public abstract ProductV2 toEntity(RequestProductDto dto);
    public abstract ResponseProductDto toDto(ProductV2 dto);
    public abstract List<ResponseProductDto> toDTOs(List<ProductV2> dto);
}
