package io.okkio.mapper;

import io.okkio.domain.Product;
import io.okkio.dto.ProductDto;
import io.okkio.dto.UserDto;
import io.okkio.dto.request.RequestProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public abstract Product toEntity(RequestProductDto dto);
}
