package io.okkio.mapper.version2;

import io.okkio.domain.version2.ProductDetailV2;
import io.okkio.domain.version2.ProductV2;
import io.okkio.dto.request.version2.RequestProductDto;
import io.okkio.dto.response.version2.ResponseProductDto;
import io.okkio.dto.response.version2.ResponseProductSlugDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapperV2 {
    public abstract ProductV2 toEntity(RequestProductDto dto);
    public abstract ResponseProductDto toDto(ProductV2 dto);
//    @Mapping(target = "productImages", expression = "java(String.join(\",\", productImages()))")
//    public abstract ProductDetailV2 toProductDetail(ProductV2 productV2);
//    @Mapping(target = "productDetailV2s.productImages", expression = "java(String.join(\",\", productDetailV2s.productImages()))")
    public abstract ResponseProductSlugDto toProductDTOs(ProductV2 productV2);
    public abstract List<ResponseProductDto> toDTOs(List<ProductV2> dto);
    public abstract List<ResponseProductSlugDto> toDTOResponseProductSlugDTOs(List<ProductV2> dto);

}
