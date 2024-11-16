package io.okkio.mapper.version2;

import io.okkio.domain.version2.ProductDetailV2;
import io.okkio.dto.PDShoppingCartDto;
import io.okkio.dto.request.version2.RequestProductDetailDto;
import io.okkio.dto.response.version2.ResponseProductDetailCategoryDto;
import io.okkio.dto.response.version2.ResponseProductDetailDto;
import io.okkio.dto.version2.ProductDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductDetailMapperV2 {
    @Mapping(target = "productV2.id", source = "productId")
    public abstract ProductDetailV2 toEntity(RequestProductDetailDto dto);
    @Mapping(target = "productId", source = "productV2.id")
    public abstract ResponseProductDetailDto toDtoResponseProductDetailDto(ProductDetailV2 entity);
    @Mapping(target = "suggestion", ignore = true)
    @Mapping(target = "productId", source = "productV2.id")
    public abstract ProductDetailDto toDto(ProductDetailV2 entity);
    public abstract PDShoppingCartDto toDtoSCart(ProductDetailV2 entity);
    public abstract ResponseProductDetailCategoryDto toCategoryDto(ProductDetailV2 entity);
}
