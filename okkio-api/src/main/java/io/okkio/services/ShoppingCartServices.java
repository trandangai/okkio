package io.okkio.services;


import io.okkio.domain.ShoppingCart;
import io.okkio.dto.ShoppingCartDto;
import io.okkio.dto.request.RequestShoppingCartDto;

import java.util.List;

public interface ShoppingCartServices {
    ShoppingCart getShoppingCartById(Long id);
    List<ShoppingCart> getAllShoppingCartByUserId(String phone, String status, Long orderId);
    ShoppingCart addShoppingCart(ShoppingCartDto dto);
    boolean update(RequestShoppingCartDto dto, String token);
    boolean deleteShoppingCartById(Long id, String status, String token);
    ShoppingCartDto getShoppingCartByUser(List<ShoppingCart> shoppingCarts);
    boolean updateStatusShoppingCart(Long id, String status);
    List<ShoppingCart> addShoppingCarts(List<RequestShoppingCartDto> DTOs, String phone);
    int updateShoppingCarts(ShoppingCart dto);
}
