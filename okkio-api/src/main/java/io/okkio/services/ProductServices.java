package io.okkio.services;

import io.okkio.domain.Product;
import io.okkio.dto.ProductDto;
import io.okkio.dto.request.RequestProductDto;

import java.util.List;

public interface ProductServices {
    List<Product> getAllProduct();
    Product addProduct(RequestProductDto dto);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    boolean isExistedProduct(String name);
    boolean update(RequestProductDto dto);
    List<Product> getProductByCategoryId(Long id);
}
