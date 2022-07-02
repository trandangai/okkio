package io.okkio.mapper;

import io.okkio.domain.Util;
import io.okkio.dto.request.RequestUtilDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UtilMapper {
    public abstract Util toEntity(RequestUtilDto dto);
//    public abstract UserDto toDto(User entity);
}
