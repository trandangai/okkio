package io.okkio.mapper.version2;

import io.okkio.domain.version2.ProductDetailV2;
import io.okkio.dto.request.version2.RequestProductDetailDto;
import io.okkio.dto.version2.ProductDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductDetailMapperV2 {
    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "productV2.id", source = "productId")
    public abstract ProductDetailV2 toEntity(RequestProductDetailDto dto);
    @Mapping(target = "productImages", ignore = true)
    @Mapping(target = "suggestion", ignore = true)
    public abstract ProductDetailDto toDto(ProductDetailV2 entity);
//    @Mapping(target = "headerImages", ignore = true)
//    public abstract PDShoppingCartDto toDtoSCart(ProductDetail entity);
//    @Mapping(target = "headerImages", ignore = true)
//    public abstract ResponseProductDetailCategoryDto toCategoryDto(ProductDetail entity);
}
