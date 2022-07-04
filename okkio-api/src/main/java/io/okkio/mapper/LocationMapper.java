package io.okkio.mapper;

import io.okkio.domain.Location;
import io.okkio.dto.LocationDto;
import io.okkio.dto.request.RequestLocationDto;
import io.okkio.dto.response.ResponseLocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LocationMapper {
    @Mapping(target = "images", ignore = true)
    public abstract Location toEntity(RequestLocationDto dto);

    @Mapping(target = "images", ignore = true)
    public abstract LocationDto toDto(Location entity);

    public abstract List<ResponseLocationDto> toResponseDto(List<Location> entities);


//    @Mapping(target = "headerImages", ignore = true)
//    public abstract ResponseProductDetailCategoryDto toCategoryDto(ProductDetail entity);
}
