package io.okkio.mapper;

import io.okkio.domain.Categories;
import io.okkio.dto.request.RequestCategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {
    public abstract Categories toEntity(RequestCategoryDto dto);
//    public abstract UserDto toDto(User entity);
}
