package io.okkio.mapper;

import io.okkio.domain.User;
import io.okkio.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract User toEntity(UserDto dto);
    public abstract UserDto toDto(User entity);
}
