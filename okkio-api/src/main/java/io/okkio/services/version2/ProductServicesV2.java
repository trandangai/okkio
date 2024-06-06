package io.okkio.services.version2;

import io.okkio.domain.version2.ProductV2;
import io.okkio.dto.request.version2.RequestProductDto;
import io.okkio.dto.response.version2.ResponseProductDto;

import java.util.List;

public interface ProductServicesV2 {
    List<ResponseProductDto> getAllProduct();
    ProductV2 addProduct(RequestProductDto dto);
    ProductV2 getProductById(Long id);
    ProductV2 getProductBySlug(String slug);
    void deleteProductById(Long id);
    boolean isExistedProduct(String name);
    boolean update(RequestProductDto dto);
    List<ProductV2> getProductByCategoryId(Long id);
}
