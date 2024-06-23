package io.okkio.mapper.version2;

import io.okkio.domain.version2.Album;
import io.okkio.dto.request.version2.RequestAlbumDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AlbumMapper {
    public abstract Album toEntity(RequestAlbumDto dto);
}
