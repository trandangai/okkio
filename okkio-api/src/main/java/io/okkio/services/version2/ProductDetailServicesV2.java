package io.okkio.services.version2;


import io.okkio.domain.version2.ProductDetailV2;
import io.okkio.dto.PDShoppingCartDto;
import io.okkio.dto.request.RequestProductDetailUpdateDto;
import io.okkio.dto.request.version2.RequestProductDetailDto;
import io.okkio.dto.response.version2.ResponseProductDetailDto;
import io.okkio.dto.version2.ProductDetailDto;

import java.util.List;

public interface ProductDetailServicesV2 {
    List<ProductDetailDto> getAllProductDetail();
    ProductDetailV2 addProductDetail(RequestProductDetailDto dto);
    ProductDetailDto getProductDetailById(Long id);
    ResponseProductDetailDto getProductDetailBySlug(String slug);
    void deleteProductDetailById(Long id);
    boolean isExistedProductDetail(String name);
    boolean update(RequestProductDetailUpdateDto dto);
    List<ProductDetailV2> getProductDetailByProductId(Long id);
    PDShoppingCartDto getProductDetailShoppingCartById(Long id, Long shoppingCartId);
}
