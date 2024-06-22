package io.okkio.mapper;

import io.okkio.domain.ProductDetail;
import io.okkio.dto.PDShoppingCartDto;
import io.okkio.dto.ProductDetailDto;
import io.okkio.dto.request.RequestProductDetailDto;
import io.okkio.dto.response.ResponseProductDetailCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductDetailMapper {
    @Mapping(target = "headerImages", ignore = true)
    public abstract ProductDetail toEntity(RequestProductDetailDto dto);
    @Mapping(target = "tastingNotes", ignore = true)
    @Mapping(target = "shippingDelivery", ignore = true)
    @Mapping(target = "headerImages", ignore = true)
    @Mapping(target = "suggestion", ignore = true)
    public abstract ProductDetailDto toDto(ProductDetail entity);
    public abstract PDShoppingCartDto toDtoSCart(ProductDetail entity);
    @Mapping(target = "headerImages", ignore = true)
    public abstract ResponseProductDetailCategoryDto toCategoryDto(ProductDetail entity);
}
