package io.okkio.services;


import io.okkio.domain.ProductDetail;
import io.okkio.dto.PDShoppingCartDto;
import io.okkio.dto.ProductDetailDto;
import io.okkio.dto.request.RequestProductDetailDto;
import io.okkio.dto.request.RequestProductDetailUpdateDto;
import io.okkio.dto.response.ResponseProductDetailCategoryDto;

import java.util.List;

public interface ProductDetailServices {
    List<ProductDetailDto> getAllProductDetail();
    ProductDetail addProductDetail(RequestProductDetailDto dto);
    ProductDetailDto getProductDetailById(Long id);
    PDShoppingCartDto getProductDetailShoppingCartById(Long id, Long shoppingCartId);
    void deleteProductDetailById(Long id);
    boolean isExistedProductDetail(String name);
    boolean update(RequestProductDetailUpdateDto dto);
    ResponseProductDetailCategoryDto getProductDetailByCategoryId(Long id);
}
