package io.okkio.services;


import io.okkio.domain.ProductDetail;
import io.okkio.domain.ShoppingCart;
import io.okkio.dto.ShoppingCartDto;
import io.okkio.dto.request.RequestProductDetailDto;
import io.okkio.dto.request.RequestProductDetailUpdateDto;
import io.okkio.dto.request.RequestShoppingCartDto;

import java.util.List;

public interface ShoppingCartServices {
    ShoppingCart getShoppingCartById(Long id);
    List<ShoppingCart> getAllShoppingCartByUserId(Long userId, String status);
    ShoppingCart addShoppingCart(RequestShoppingCartDto dto, String token);
    boolean update(RequestShoppingCartDto dto, String token);
    boolean deleteShoppingCartById(Long id, String status, String token);
    ShoppingCartDto getShoppingCartByUser(List<ShoppingCart> shoppingCarts);
}
