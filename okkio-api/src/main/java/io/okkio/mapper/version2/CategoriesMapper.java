package io.okkio.mapper.version2;

import io.okkio.domain.version2.CategoriesV2;
import io.okkio.dto.request.version2.RequestCategoryDto;
import io.okkio.dto.version2.CategoriesDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CategoriesMapper {
    public abstract CategoriesDto toCategoriesDto (CategoriesV2 dto);
    public abstract List<CategoriesDto> toListCategoriesDto (List<CategoriesV2> dto);
    public abstract CategoriesV2 toEntity (RequestCategoryDto dto);

}
